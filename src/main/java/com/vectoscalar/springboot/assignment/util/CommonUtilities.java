package com.vectoscalar.springboot.assignment.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtilities {
		
	public static String convertObjectToJsonString(Object object)throws Exception {
		if(null == object) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(object);
	}
}
