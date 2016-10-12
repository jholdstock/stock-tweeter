package com.jamieholdstock.stocktweeter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class RetryingHttpClient {

	public RetryingHttpClient() {}
	
	public String getHttpBody(String url)	throws ClientProtocolException, IOException {
	    String responseBody = null;
    	
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	HttpGet httpget = new HttpGet(url);
    	CloseableHttpResponse response = httpclient.execute(httpget);
    	try {
    	    HttpEntity entity = response.getEntity();
    	    if (entity != null) {
    	        InputStream inputStream = entity.getContent();
    	        try {
    	        	ByteArrayOutputStream result = new ByteArrayOutputStream();
    	        	byte[] buffer = new byte[1024];
    	        	int length;
    	        	while ((length = inputStream.read(buffer)) != -1) {
    	        	    result.write(buffer, 0, length);
    	        	}
    	        	responseBody = result.toString("UTF-8");
    	        } finally {
    	        	inputStream.close();
    	        }
    	    }
    	} finally {
    	    response.close();
    	}
    	
    	return responseBody;
	}
}
