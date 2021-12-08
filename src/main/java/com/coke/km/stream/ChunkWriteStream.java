package com.coke.km.stream;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import com.coke.km.message.Message;

public class ChunkWriteStream {

	private OutputStream ops;
	
	private int maxBodySize = 512;
	
	public ChunkWriteStream(OutputStream ops) {
		this.ops = ops;
	}
	
	public void write(Message msg) throws IOException {
		messageTochunk messageTochunk = new messageTochunk(msg, maxBodySize);
		while(!messageTochunk.isFinish()) {
			Chunk chunk = messageTochunk.readChunk();
			byte[] data = chunk.toBytes();
			this.ops.write(data);
			this.ops.flush();
		}
	}
	
	
	

	
	class messageTochunk{
		
		private Message msg;
		
		private Integer chunkId;
		private int maxBodySize;
		
		private messageTochunk(Message msg,int maxBodySize) {
			this.msg = msg;
			this.keys =this.msg.keys(); 
			this.chunkId = Chunk.chunkId();
			this.maxBodySize = maxBodySize;
			
		}
		
		private List<Byte> keys;
		
		private byte[] data;
		
		private int end;
		private int rdata;
		
		private int process = 0; 
		
	    private int index = 0;
	    
	    private long rMessageLength = 0;
	    
	    private long messageLength  = 0;

		private Chunk readChunk() {
			if(process==0) {
				Chunk0 chunk0 =Chunk0.createChunk0(this.chunkId);
				chunk0.setMessageId(Chunk.msgId());
				chunk0.setTime(System.currentTimeMillis());
				this.messageLength = msg.getMessageLength();
				chunk0.setMessageLength(this.messageLength);
				chunk0.setMessageType(msg.getMessageType());
				chunk0.setClassId(msg.getClassId());
				Byte fkey = keys.get(index);
				this.data = msg.getValue(fkey);
				this.end = this.data.length;
				chunk0.setKey(fkey);
				chunk0.setDataLen(data.length);
				int sLenght = end-rdata;
				if(sLenght<=this.maxBodySize) {
					byte[] data = new byte[sLenght];
					System.arraycopy(this.data, rdata, data, 0, sLenght);
					chunk0.setData(data);
					this.process = 1;
					this.rMessageLength = this.rMessageLength+sLenght;
				}else {
					byte[] data = new byte[this.maxBodySize];
					System.arraycopy(this.data, rdata, data, 0, this.maxBodySize);
					chunk0.setData(data);
					this.process = 2;
					this.rdata = this.rdata+this.maxBodySize;
					this.rMessageLength = this.rMessageLength+this.maxBodySize;
				}
				return chunk0;
			}else if(process==1) {
				index++;
				Chunk1 chunk1 = Chunk1.createChunk1(chunkId);
				Byte fkey = keys.get(index);
				chunk1.setKey(fkey);
				chunk1.setDataLen(data.length);
				this.data = msg.getValue(fkey);
				this.end = this.data.length;
				int sLenght = end-rdata;
				if(sLenght<=this.maxBodySize) {
					byte[] data = new byte[sLenght];
					System.arraycopy(this.data, rdata, data, 0, sLenght);
					chunk1.setData(data);
					this.process = 1;
					this.rMessageLength = this.rMessageLength+sLenght;
				}else {
					byte[] data = new byte[this.maxBodySize];
					System.arraycopy(this.data, rdata, data, 0, this.maxBodySize);
					chunk1.setData(data);
					this.process = 2;
					this.rdata = this.rdata+this.maxBodySize;
					this.rMessageLength = this.rMessageLength+this.maxBodySize;
				}
				return chunk1;
			}else if(process==2) {
				Chunk2 chunk2 =Chunk2.createChunk2(chunkId);
				int sLenght = end-rdata;
				if(sLenght<=this.maxBodySize) {
					byte[] data = new byte[sLenght];
					System.arraycopy(this.data, rdata, data, 0, sLenght);
					chunk2.setData(data);
					this.process = 1;
					this.rMessageLength = this.rMessageLength+sLenght;
				}else {
					byte[] data = new byte[this.maxBodySize];
					System.arraycopy(this.data, rdata, data, 0, this.maxBodySize);
					chunk2.setData(data);
					this.process = 2;
					this.rdata = this.rdata+this.maxBodySize;
					this.rMessageLength = this.rMessageLength+this.maxBodySize;
				}
				return chunk2;
			}
			return null;
		} 
		
		private boolean isFinish() {
			return this.rMessageLength == this.messageLength && this.rMessageLength>0;
		}
		
	}
}
