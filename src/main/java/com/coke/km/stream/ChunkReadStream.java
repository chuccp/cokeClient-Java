package com.coke.km.stream;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.coke.km.message.Message;
import com.coke.km.util.NumberUitl;

public class ChunkReadStream {

	private InputStream ips;

	private int maxBodySize = 512;

	private Map<Integer, chunkRecord> chunkRecordMap = new HashMap<>();

	public ChunkReadStream(InputStream ips) {
		this.ips = ips;
	}
	
	
	
	public Message readMessage() throws IOException {
		while(true) {
			chunkRecord chunkRecord = this.readChunk();
			
			if(chunkRecord==null) {
				return null;
			}
			
			if(chunkRecord.isError()) {
				break;
			}
			
			if(chunkRecord.isFinish()) {
				return chunkRecord.msg;
			}
		}
		return null;
	}

	public chunkRecord readChunk() throws IOException {
		byte[] data = this.read(2);

		ChunkHeader ch = new ChunkHeader(data);
		if (ch.getChunkType() == 0) {
			Chunk0 chunk0 = new Chunk0(ch);
			byte[] chunk0_data = this.read(10);
			chunk0.setClassId(chunk0_data[0]);
			chunk0.setMessageType(chunk0_data[1]);
			chunk0.setTime(NumberUitl.U32BE(chunk0_data, 2));
			chunk0.setMessageId(NumberUitl.U32BE(chunk0_data, 6));
			chunk0.setMessageLength(this.readMessageLength());
			chunk0.setKey((byte) this.read());
			int dataLenght = this.readMessageLength();
			chunk0.setDataLen(dataLenght);
			if (dataLenght > this.maxBodySize) {
				chunk0.setData(this.read(this.maxBodySize));
			} else {
				chunk0.setData(this.read(dataLenght));
			}
			return this.initChunkRecord(chunk0);
		} else if (ch.getChunkType() == 1) {
			Chunk1 chunk1 = new Chunk1(ch);
			chunk1.setKey((byte) this.read());
			int dataLenght = this.readMessageLength();
			chunk1.setDataLen(dataLenght);
			if (dataLenght > this.maxBodySize) {
				chunk1.setData(this.read(this.maxBodySize));
			} else {
				chunk1.setData(this.read(chunk1.getDataLen()));
			}
			
			chunkRecord chunkRecord = this.chunkRecordMap.get(chunk1.getChunkId());
			chunkRecord.putchunk1(chunk1);
			return chunkRecord;
		} else if (ch.getChunkType() == 2) {
			Chunk2 chunk2 = new Chunk2(ch);
			chunkRecord chunkRecord = this.chunkRecordMap.get(chunk2.getChunkId());
			int rlen = chunkRecord.dataLength - chunkRecord.rDataLength;
			if (rlen > this.maxBodySize) {
				chunk2.setData(this.read(this.maxBodySize));
			} else {
				chunk2.setData(this.read(rlen));
			}
			chunkRecord.putchunk2(chunk2);
			return chunkRecord;
		}
		return null;
	}

	private int readMessageLength() throws IOException {
		int num = 0;
		byte[] data = this.read(2);

		int pre = data[0] & 64;
		if (pre == 0) {
			num = num | Byte.toUnsignedInt(data[0]);
			num = (num << 8) | Byte.toUnsignedInt(data[1]);
		} else {
			num = (num & 64) | Byte.toUnsignedInt( data[0]);
			num = (num << 8) | Byte.toUnsignedInt( data[1]);
			num = (num << 8) | Byte.toUnsignedInt(data[2]);
			num = (num << 8) | Byte.toUnsignedInt(data[3]);
		}
		return num;
	}

	private byte[] read(int num) throws IOException {
		byte[] data = new byte[num];
		int offset = 0;
		while (true) {
			int size = this.ips.read(data, offset, num);
			
			if(size<0) {
				throw new IOException("Read Over");
			}
			
			offset = offset + size;
			if (offset == num) {
				break;
			}
		}
		return data;
	}

	private int read() throws IOException {
		int size = this.ips.read();
		if(size<0) {
			throw new IOException("Read Over");
		}
		return size;
	}

	private chunkRecord initChunkRecord(Chunk0 chunk0) {
		chunkRecord chunkRecord = new chunkRecord(chunk0);
		this.chunkRecordMap.put(chunk0.getChunkId(), chunkRecord);
		return chunkRecord;
	}

	class chunkRecord {
		private long messageLength;
		private int rMessageLength;
		private byte key;
		private byte[] data;
		private int dataLength;
		private int rDataLength;
		private Message msg;
		
		private boolean error = false;
		
		
		private boolean isError() {
			return error;
		}

		private chunkRecord(Chunk0 chunk0) {
			this.msg = new Message(chunk0.getClassId(),chunk0.getMessageType());
			this.messageLength = chunk0.getMessageLength();
			this.msg.setTimeStamp(chunk0.getTime());
			this.msg.setMessageLength(messageLength);
			this.msg.setMessageId(chunk0.getMessageId());
			this.key = chunk0.getKey();
			this.dataLength = chunk0.getDataLen();
			this.data = new byte[this.dataLength];
			System.arraycopy(chunk0.getData(), 0, this.data, this.rDataLength, chunk0.getData().length);
			this.rDataLength = this.rDataLength + chunk0.getData().length;
			
			if(this.rDataLength==this.dataLength) {
				this.msg.addValue(this.key,this.data);
			}
			
			this.rMessageLength = this.rMessageLength+chunk0.getData().length;
		}

		
		private void putchunk1(Chunk1 chunk1) {
			if(this.dataLength!=this.rDataLength) {
				this.error = true;
				return;
			}
			this.dataLength = 0 ;
			this.rDataLength = 0;
			this.key = chunk1.getKey();
			this.dataLength = chunk1.getDataLen();
			this.data = new byte[this.dataLength];
			System.arraycopy(chunk1.getData(), 0, this.data, this.rDataLength, chunk1.getData().length);
			this.rDataLength = this.rDataLength + chunk1.getData().length;
			
			if(this.rDataLength==this.dataLength) {
				this.msg.addValue(this.key,this.data);
			}
			
			this.rMessageLength = this.rMessageLength+chunk1.getData().length;
		}
		
		private void putchunk2(Chunk2 chunk2) {
			System.arraycopy(chunk2.getData(), 0, this.data, this.rDataLength, chunk2.getData().length);
			this.rDataLength = this.rDataLength + chunk2.getData().length;
			if(this.rDataLength==this.dataLength) {
				this.msg.addValue(this.key,this.data);
			}
			this.rMessageLength = this.rMessageLength+chunk2.getData().length;
		}
		
		private boolean isFinish() {
			return this.rMessageLength == this.messageLength;
		}
		
	}
}
