package com.thomson.idol.Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import com.west.tugboat.Tugboat;

public class APIRequest {

	
	
	
	public String makeRequest(Map<String,String> parameters, String requestType) throws Exception
	{
		if(parameters == null && requestType != null && !requestType.equals(APIRequestType.LIST_INDEX))
		{
			return null;
		}
		else if(parameters == null && requestType != null && requestType.equals(APIRequestType.LIST_INDEX))
		{
			parameters = new HashMap<String, String>();
		}
		ResourceBundle bundle = Tugboat.getBundle("properties.api");
		String urlString = bundle.getString("baseurl");
		String APIKEY =  bundle.getString("apiToken");
		parameters.put("apiKey", APIKEY);
		urlString = addAPIAction(urlString, requestType);
		urlString = makeURLParams(parameters, urlString);
		
		Response response = getRequest(urlString);
		
		return response.response;
	}
	
	public Response getRequest(String URL) throws Exception
	{
		Response responseHolder = new Response();
		try {
			HttpClient client = HttpClients.createDefault();
			HttpGet request = new HttpGet(URL);
			HttpResponse response = client.execute(request);

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			
			responseHolder.responseCode = response.getStatusLine().getStatusCode();
			responseHolder.response = IOUtils.toString(rd);
			return responseHolder;
		} 
		catch (Exception e) {
			throw new Exception("Unknown Error occurred:" + e.getMessage());
		}
	}
	
	public String addAPIAction(String URL, String action)
	{
		return URL + action + "/v1";
	}
	
	public String makeURLParams(Map<String,String> parameters, String URL)
	{
		int count = 0;
		for (Map.Entry<String, String> entry : parameters.entrySet()) 
		{
		    String key = entry.getKey();
		    String value = entry.getValue();
		    
		    if(count == 0)
		    {
		    	URL = URL + "?" + getParameter(key, value);
		    	count++;
		    }
		    else
		    {
		    	URL = URL + "&" + getParameter(key, value);
		    }
		}
		return URL;
	}
	
	private String getParameter(String name, String value)
	{
		return name + "=" + URLEncoder.encode(value);
	}
}

