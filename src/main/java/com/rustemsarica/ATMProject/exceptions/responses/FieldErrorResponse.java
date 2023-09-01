package com.rustemsarica.ATMProject.exceptions.responses;

import java.util.List;

public class FieldErrorResponse {
    private List<CustomFieldError> errors;

	public FieldErrorResponse() {
	}

	public List<CustomFieldError> getFieldErrors() {
		return errors;
	}

	public void setFieldErrors(List<CustomFieldError> fieldErrors) {
		this.errors = fieldErrors;
	}
}
