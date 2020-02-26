package com.example.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

@Configuration
public class RestTemplateConfig {
	
	@Bean
	public RestTemplate restTemplate() {
		HttpsURLConnection.setDefaultHostnameVerifier(new DummyHostnameVerifier());
	    return new RestTemplate();
	}
	
	
	public static class DummyHostnameVerifier implements HostnameVerifier {
	    @Override
	    public boolean verify(String hostname, SSLSession session) {
	    	return hostname.equalsIgnoreCase(session.getPeerHost()); 
	    }
	}
}
