package com.coke.km.stream;

public class ChunkHeader {
	
	public static ChunkHeader create(Byte chunkType,Integer chunkId) {
		return new ChunkHeader(chunkType, chunkId);
	}
	
	private Byte chunkType;
	
	private Integer chunkId;
	
	private byte[] data;

	public ChunkHeader(byte[] data) {
		this.data = data;
	}

	public Byte getChunkType() {
		if(chunkType!=null) {
			return chunkType;
		}else {
			chunkType = (byte)(Byte.toUnsignedInt( this.data[0])>>6);
		}
		return chunkType;
	}
	public Integer getChunkId() {
		
		if(chunkId!=null) {
			return chunkId;
		}else {
			chunkId = 0;
			chunkId =  (chunkId|(Byte.toUnsignedInt(this.data[0])&63)<<8)|data[1];
		}
		return chunkId;
	}


	public ChunkHeader(Byte chunkType, Integer chunkId) {
		this.chunkType = chunkType;
		this.chunkId = chunkId;
	}


	public byte[] getData() {
		
		if(data!=null&&data.length>0) {
			return data;
		}
		data =  new byte[2];
		data[0] = (byte) (Byte.toUnsignedInt(this.chunkType.byteValue())<<6|Byte.toUnsignedInt(this.chunkId.byteValue())>>8);
		data[1] = this.chunkId.byteValue();
		return data;
	}


	
}
