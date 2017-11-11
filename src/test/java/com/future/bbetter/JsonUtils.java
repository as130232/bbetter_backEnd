package com.future.bbetter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	
	public static String asJsonString(final Object value){
		try {
			return new ObjectMapper().writeValueAsString(value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
