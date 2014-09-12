package com.thomson.idol.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.thomson.idol.Util.APIRequest;
import com.thomson.idol.Util.APIRequestType;
import com.thomson.idol.json.request.fields.IndexFields;

public class FindSimilarRequest {

	
	public static final String ALL = "all";
	public static final String FILTER = "fields";
	
	public List<String> fieldList = new ArrayList<String>();
	public List<String> searchIndexes = new ArrayList<String>();
	public Boolean higlight = false;
	public String fieldFilter = "";
	public String searchTypeFilter = SimilarFilters.NONE;
	
	public FindSimilarRequest(List<String> fieldList, List<String> searchIndexes, boolean higlight, String fieldFilter) {
		this.fieldList = fieldList;
		this.searchIndexes = searchIndexes;
		this.higlight = higlight;
		this.fieldFilter = fieldFilter;
	}
	
	public FindSimilarRequest() {

	}

	public String makeTextRequest(String text) throws Exception
	{
		if(searchIndexes == null || (searchIndexes != null && searchIndexes.size() == 0))
		{
			return null;
		}
		
		if(StringUtils.isBlank(text))
		{
			return null;
		}
		
		APIRequest api = new APIRequest();
		
		Map<String, String> params = new HashMap<String, String>(){{
	        put("indexes",getListAsString(searchIndexes));
	        put("print_fields",getListAsString(fieldList));
	        put("print",fieldFilter);
	        if(searchTypeFilter != SimilarFilters.NONE)
	        {
	        	put("field_text", searchTypeFilter);
	        }
	        put("highlight",higlight.toString().replace("false", "off").replace("true", "on"));
	    }};
	    params.put("text",text);
		
		return api.makeRequest(params, APIRequestType.FIND_SIMILAR);
	}
	
	public String getListAsString(List<String> list)
	{
		String build = "";
		for(int i = 0; i < list.size(); i++)
		{
			if(i == 0)
			{
				build = list.get(i);
			}
			else
			{
				build = build + "," + list.get(i);
			}
		}
		return build;
	}
}
