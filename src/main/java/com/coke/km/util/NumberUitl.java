package com.coke.km.util;

public class NumberUitl {
	

	public static long U32BE(byte[] data,int start) {
		long num = 0;
		num = num|Byte.toUnsignedInt(data[start]);
		num = (num<<8)|Byte.toUnsignedInt(data[start+1]);
		num = (num<<8)|Byte.toUnsignedInt(data[start+2]);
		num = (num<<8)|Byte.toUnsignedInt(data[start+3]);
		return num;
	}
	
	
	public static byte[] U32BE(long num) {
		byte[] data = new byte[4];
		data[0] = (byte) ((byte) (num>>24));
		data[1] = (byte) ((byte) (num>>16));
		data[2] = (byte) ((byte) (num>>8));
		data[3] = (byte) num;
		return data;
	}
}
