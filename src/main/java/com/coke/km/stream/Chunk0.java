package com.coke.km.stream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.coke.km.util.NumberUitl;

public class Chunk0  extends Chunk1{
	

	public Chunk0(ChunkHeader chunkHeader) {
		super(chunkHeader);
	}
	
	public static Chunk0 createChunk0(Integer chunkId) {
		return new Chunk0(ChunkHeader.create((byte)0,chunkId ));
	}
	
	private byte classId;
	
	private byte messageType;
	
	private long time;
	
	private long messageId;
	
	private long messageLength;
	

	public byte getClassId() {
		return classId;
	}

	public void setClassId(byte classId) {
		this.classId = classId;
	}

	public byte getMessageType() {
		return messageType;
	}

	public void setMessageType(byte messageType) {
		this.messageType = messageType;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public long getMessageLength() {
		return messageLength;
	}

	public void setMessageLength(long messageLength) {
		this.messageLength = messageLength;
	}
	@Override
	public byte[] toBytes() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			baos.write(this.getChunkHeader().getData());
			baos.write(this.classId);
			baos.write(this.messageType);
			baos.write(NumberUitl.U32BE(time));
			baos.write(NumberUitl.U32BE(messageId));
			baos.write(Chunk.lengthToBytes(this.messageLength));
			baos.write(Byte.toUnsignedInt(this.getKey()));
			baos.write(Chunk.lengthToBytes(this.getDataLen()));
			baos.write(this.getData());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
}
