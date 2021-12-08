package com.coke.km.stream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Chunk1 extends Chunk2 {

	public Chunk1(ChunkHeader chunkHeader) {
		super(chunkHeader);
	}
	private byte key;
	
	private int dataLen;
	

	public byte getKey() {
		return key;
	}

	public void setKey(byte key) {
		this.key = key;
	}

	public int getDataLen() {
		return dataLen;
	}

	public void setDataLen(int dataLen) {
		this.dataLen = dataLen;
	}

	public static Chunk1 createChunk1(Integer chunkId) {
		return new Chunk1(ChunkHeader.create((byte)1,chunkId ));
	}
	
	@Override
	public byte[] toBytes() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			baos.write(this.getChunkHeader().getData());
			baos.write(Byte.toUnsignedInt(this.getKey()));
			baos.write(Chunk.lengthToBytes(dataLen));
			baos.write(this.getData());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
	
}
