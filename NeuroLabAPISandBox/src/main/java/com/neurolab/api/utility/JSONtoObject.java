package com.neurolab.api.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JSONtoObject {

	public static <T> T  jsonToObject(String json, Class<?> classType ) throws Exception{

		Object map = null;
		try {
			ObjectMapper mapper = new ObjectMapper();

			map = mapper.readValue(json, classType);
			
		} catch (Exception e) {

			throw e;
		} 

		return (T) map;
	
	}
	
	public static String ObjecttoJson(Object obj) throws Exception {

		String output = null;
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			output = mapper.writeValueAsString(obj);
			
		} catch (Exception e) {

			throw e;
		}


		return output;
		
	}
	
}
