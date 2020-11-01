package com.neurolab.api.bean;

import com.neurolab.api.utility.JSONtoObject;

public class RequestBean {
	
	private String request ;



	public <T> T getObject(Class<T> classType) throws Exception {
		Object object = JSONtoObject.jsonToObject(request, classType);
        return classType.cast(object);
    }
	

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}


	
}
