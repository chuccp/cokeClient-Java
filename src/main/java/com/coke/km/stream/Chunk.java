package com.coke.km.stream;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Chunk {

	private ChunkHeader chunkHeader;

	public Chunk(ChunkHeader chunkHeader) {
		super();
		this.chunkHeader = chunkHeader;
	}
	public Byte getChunkType() {
		return chunkHeader.getChunkType();
	}

	public Integer getChunkId() {
		return chunkHeader.getChunkId();
	}
	

	public ChunkHeader getChunkHeader() {
		return chunkHeader;
	}


	private static  Lock lock = new ReentrantLock();
	
	private static char  countChunkId  = 0;
	public static Integer chunkId()  {
		lock.lock();
		countChunkId = (char) (countChunkId + 4);
		lock.unlock();
		return countChunkId>>2;
	}
	
	
	public static long msgId() {
		int num =(int) (Math.random()*1025);
		return System.currentTimeMillis()<<10 | (num);
	}
	
	
	public static byte[] lengthToBytes(long length) {
		if(length<=32_767) {
			byte[] data = new byte[2];
			data[0] = (byte) ((byte) (length>>8));
			data[1] = (byte) length;
			return data;
		}else {
			
			byte[] data = new byte[4];
			data[0] = (byte) ((byte) (length>>24)|128);
			data[1] = (byte) ((byte) (length>>16));
			data[2] = (byte) ((byte) (length>>8));
			data[3] = (byte) length;
			return data;
		}
		
		
	}
	
	
	public  abstract byte[] toBytes() ;
}
