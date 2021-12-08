package com.coke.km.stream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Chunk2 extends Chunk{

	public Chunk2(ChunkHeader chunkHeader) {
		super(chunkHeader);
	}
	private byte[] data;
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public static Chunk2 createChunk2(Integer chunkId) {
		return new Chunk2(ChunkHeader.create((byte)2,chunkId ));
	}
	@Override
	public byte[] toBytes() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			baos.write(this.getChunkHeader().getData());
			baos.write(this.getData());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toByteArray();
	}
}
