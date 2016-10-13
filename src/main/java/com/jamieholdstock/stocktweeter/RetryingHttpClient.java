package com.jamieholdstock.stocktweeter;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.jamieholdstock.stocktweeter.stockchecker.StockException;

public class RetryingHttpClient {

	public RetryingHttpClient() {}
	
	public String getHttpBody(String url) throws StockException {
	    String responseBody = null;
    	
    	CloseableHttpClient httpclient = HttpClients.createDefault();
    	HttpGet httpget = new HttpGet(url);
    	try {
	    	CloseableHttpResponse response = httpclient.execute(httpget);
	    	try {
	    	    HttpEntity entity = response.getEntity();
    	    	responseBody = EntityUtils.toString(entity);
    	    	EntityUtils.consume(entity);
	    	} finally {
	    	    response.close();
	    	}
    	}
    	catch(Exception e) {
    		throw new StockException("Http GET to url " + url + " failed", e);
    	}
    	
    	return responseBody;
	}
}
