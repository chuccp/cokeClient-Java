package com.coke.km.net;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class KmUri {
	
	private Map<String,String> map  = new HashMap<>();
	
	
	private String host;
	
	private int port;
	
	private String query;
	
	public String getParam(String key) {
		return map.get(key);
		
	}
	public KmUri(String link) throws Exception {
		URL	parse = new URL(link);
		String[] params = parse.getQuery().split("&");
		for(String param:params) {
			if(param.length()>0) {
				String[] kv = param.split("=");
				if(kv.length==2) {
					map.put(kv[0], kv[1]);
				}
			}
		}
		this.host = parse.getHost();
		this.port = parse.getPort();
	}
	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getQuery() {
		return query;
	}
	
}
