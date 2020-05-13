package com.soa.api.controller.response;

public class ApiResponse {
	
	private Object object;
    private Boolean success;
    private String message;

    public ApiResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
    public ApiResponse(Object object, Boolean success, String message) {
		super();
		this.object = object;
		this.success = success;
		this.message = message;
    }

	public ApiResponse() {
		super();
	}

	public ApiResponse(Boolean success ) {
		super();
		this.success = success;
	}

	public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
    
}