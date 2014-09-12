package com.thomson.idol.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.thomson.idol.Util.APIRequest;
import com.thomson.idol.Util.APIRequestType;
import com.thomson.idol.customrequest.CompanyRequest;
import com.thomson.idol.json.request.fields.IndexFields;
import com.thomson.idol.json.request.fields.PatentFields;

public class APIRequestTest {

	
	@Test
	public void testSample() throws Exception
	{
		APIRequest tet = new APIRequest();
		String test = tet.makeRequest(null, APIRequestType.LIST_INDEX);
		JSONObject obj = new JSONObject(test);
		JSONArray arr = obj.getJSONArray("public_index");
		for (int i = 0; i < arr.length(); i++)
		{
		    String post_id = arr.getJSONObject(i).getString("index");
		    System.out.println(post_id);
		}
	}
	
	@Test
	public void testcompany() throws Exception
	{
		CompanyRequest c = new CompanyRequest();
		c.makeCompanyRequest("Exxon");
	}
	
	@Test
	public void testPatent() throws Exception
	{
		
		Map<String, String> params = new HashMap<String, String>(){{
	        put("text","microsoft");
	        put("indexes",IndexFields.TR_PATENT);
	        put("print","all");
	        put("highlight","off");
	    }};
		
		APIRequest tet = new APIRequest();
		String test = tet.makeRequest(params, APIRequestType.FIND_SIMILAR);
		JSONObject obj = new JSONObject(test);
		JSONArray arr = obj.getJSONArray("public_index");
		for (int i = 0; i < arr.length(); i++)
		{
		    String post_id = arr.getJSONObject(i).getString("index");
		    System.out.println(post_id);
		}
	}
	
	@Test
	public void testParametric() throws Exception
	{
		FindParametricRequest f = new FindParametricRequest();
		f.field = PatentFields.OWNER;
		f.index = IndexFields.HP_PATENT;
		String test = f.makeParametricRequest();
		
		JSONObject obj = new JSONObject(test);
		JSONObject arr = obj.getJSONObject(PatentFields.OWNER.toUpperCase());
		System.out.println(arr.names().length());
//		for (int i = 0; i < arr.names().length(); i++)
//		{
//			if(arr.names().get(i).toString().contains("MICROSOFT"))
//			{
//				System.out.println(arr.names().get(i).toString().split(",")[0]);
//			}
//			
//		}
	}
	
	@Test
	public void testsim() throws Exception
	{
		List<String> index = new ArrayList<String>() {{
			add(IndexFields.WIKI_ENG);
			//add(IndexFields.HP_PATENT);
		}};
		FindSimilarRequest f = new FindSimilarRequest();
		f.fieldFilter = FindSimilarRequest.ALL;
		f.higlight = false;
		f.searchIndexes = index;
		f.searchTypeFilter = SimilarFilters.COMPANY_SEARCH;
		String test = f.makeTextRequest("Microsoft");
		System.out.println(test);
	}
	
	@Test
	public void testPatentRequest() throws Exception
	{
		List<String> fieldList = new ArrayList<String>() {{
			add(PatentFields.OWNER);
			add(PatentFields.TITLE);
			add(PatentFields.COUNTRY_CODE);
		}};
		List<String> index = new ArrayList<String>() {{
			add(IndexFields.TR_PATENT);
			//add(IndexFields.HP_PATENT);
		}};
		FindSimilarRequest f = new FindSimilarRequest(fieldList, index, false, FindSimilarRequest.FILTER);
		String test = f.makeTextRequest("microsoft corporation");
		
		JSONObject obj = new JSONObject(test);
		JSONArray arr = obj.getJSONArray("documents");
		System.out.println(arr.length());
		for (int i = 0; i < arr.length(); i++)
		{
		    String owner = arr.getJSONObject(i).getJSONArray(PatentFields.OWNER).toString();
		    String code = arr.getJSONObject(i).getJSONArray(PatentFields.COUNTRY_CODE).toString();
		    String title = arr.getJSONObject(i).getString("title");
		    //String owner = arr.getJSONObject(i).getJSONArray(PatentFields.OWNER).toString();
		    
		    System.out.println(title);
		}
	}
}
