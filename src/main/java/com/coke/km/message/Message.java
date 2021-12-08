package com.coke.km.message;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class Message {
	
	public Message(byte classId,byte messageType) {
		this.classId = classId;
		this.messageType = messageType;
		
	}
	public Message(int classId,int messageType) {
		this.classId = (byte) classId;
		this.messageType = (byte) messageType;
		
	}
	
	private HashMap<Byte,byte[]> valueMap = new LinkedHashMap<>();
	
	public List<Byte> keys(){
		return new ArrayList<Byte>(valueMap.keySet());
	}
	

	private long messageId;
	
	private long timeStamp;
	
	private long messageLength;
	
	private byte classId;
	
	private byte messageType;
	
	public void addValue(byte key,byte[] value) {
		valueMap.put(key, value);
	}
	
	public byte[] getValue(byte key) {
		return valueMap.get(key);
	}
	public long getMessageId() {
		return messageId;
	}

	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public long getMessageLength() {
		if(messageLength==0) {
			Iterator<byte[]> iterator = valueMap.values().iterator();
			while(iterator.hasNext()) {
				messageLength = messageLength+iterator.next().length;
			}
		}
		return messageLength;
	}

	public void setMessageLength(long messageLength) {
		this.messageLength = messageLength;
	}

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


	
	
	

}
