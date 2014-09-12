package com.thomson.idol.request;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.thomson.idol.Util.APIRequest;
import com.thomson.idol.Util.APIRequestType;

public class FindParametricRequest {

	public String field;
	public String index;
	public Boolean documentCount = false;
	public Boolean sort = false;
	
	public String makeParametricRequest() throws Exception
	{
		if(StringUtils.isBlank(index) || StringUtils.isBlank(field))
		{
			return null;
		}
		
		APIRequest api = new APIRequest();
		
		Map<String, String> params = new HashMap<String, String>(){{
	        put("field_name",field);
	        put("indexes",index);
	        put("document_count",documentCount.toString());
	    }};
		
		return api.makeRequest(params, APIRequestType.FIND_PARAMETRIC);
	}
}
