package com.coke.km;

public class KmClient {

	public static Builder createClient(ClientConfig config) {
		return new Builder().setClientConfig(config);
	}

	public static Builder createClient(String url) throws Exception {
		return createClient(new ClientConfig(url));
	}

	static class Builder {
		public Builder() {
		}

		private String kmUrl;

		public String getKmUrl() {
			return kmUrl;
		}

		public Builder setKmUrl(String kmUrl) {
			this.kmUrl = kmUrl;
			return this;
		}

		private ClientConfig clientConfig;

		private MessageListener messageListener;

		public ClientConfig getClientConfig() {
			return clientConfig;
		}

		public Builder setClientConfig(ClientConfig clientConfig) {
			this.clientConfig = clientConfig;
			return this;
		}

		public Builder addMessageListener(MessageListener messageListener) {
			this.messageListener = messageListener;
			return this;
		}

		public MessageListener getMessageListener() {
			return messageListener;
		}

		public Chat build() {
			return new Chat(this.clientConfig);
		}

	}

}
