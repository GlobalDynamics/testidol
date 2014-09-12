package com.thomson.idol.customrequest;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.thomson.idol.json.request.fields.IndexFields;
import com.thomson.idol.json.request.fields.PatentFields;
import com.thomson.idol.request.FindSimilarRequest;
import com.thomson.idol.request.SimilarFilters;
import com.thomson.idol.search.CompanyResult;

public class CompanyRequest {
	
	public CompanyResult makeCompanyRequest(String query) throws Exception
	{
		CompanyResult cr = new CompanyResult();
		String bio = getCompanyBio(query);
		if(StringUtils.isNotBlank(bio))
		{
			cr.companyBio = bio;
		}
		return cr;
	}
	
	public String getResult(String query)
	{
		List<String> index = new ArrayList<String>() {{
			add(IndexFields.WIKI_ENG);
		}};
		FindSimilarRequest f = new FindSimilarRequest();
		f.fieldFilter = FindSimilarRequest.ALL;
		f.higlight = false;
		f.searchIndexes = index;
		f.searchTypeFilter = SimilarFilters.COMPANY_SEARCH;
		return f.makeTextRequest(query);
	}
	
	public String getCompanyBio(String query) throws Exception
	{
		String bio = "";
		List<String> index = new ArrayList<String>() {{
			add(IndexFields.WIKI_ENG);
		}};
		FindSimilarRequest f = new FindSimilarRequest();
		f.fieldFilter = FindSimilarRequest.ALL;
		f.higlight = false;
		f.searchIndexes = index;
		f.searchTypeFilter = SimilarFilters.COMPANY_SEARCH;
		String result = f.makeTextRequest(query);
		JSONObject obj = new JSONObject(result);
		JSONArray arr = obj.getJSONArray("documents");
		
		bio = arr.getJSONObject(0).getString("content");
		return bio;
	}
	

}
