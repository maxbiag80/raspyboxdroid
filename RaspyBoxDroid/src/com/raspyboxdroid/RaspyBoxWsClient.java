package com.raspyboxdroid;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;


public class RaspyBoxWsClient {
	
	private static final String URL_POWERON = "poweron";
	private static final String URL_POWEROFF = "poweroff";	
	
	private String address;
	private int port;
	
	private AsyncHttpClient client;
	
	public RaspyBoxWsClient() {
		this.client = new AsyncHttpClient();
	}
		
	public void powerOn(int channel, AsyncHttpResponseHandler responseHandler) {
		client.get(this.getEndpoint(URL_POWERON, channel), responseHandler);
	}

	public void powerOff(int channel, AsyncHttpResponseHandler responseHandler) {
		client.get(this.getEndpoint(URL_POWEROFF, channel), responseHandler);
	}

	private String getEndpoint(String call, int channel) {
		StringBuilder sb = new StringBuilder();
		sb.append("http://")
			.append(this.address)
			.append(String.valueOf(this.port))
			.append("/")
			.append(call)
			.append("/")
			.append(String.valueOf(channel));
		
		return sb.toString();
		
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
}
