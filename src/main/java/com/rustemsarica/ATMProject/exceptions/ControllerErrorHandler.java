package com.rustemsarica.ATMProject.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rustemsarica.ATMProject.exceptions.responses.CustomFieldError;
import com.rustemsarica.ATMProject.exceptions.responses.FieldErrorResponse;

@RestControllerAdvice
public class ControllerErrorHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	protected ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
		FieldErrorResponse fieldErrorResponse = new FieldErrorResponse();

		List<CustomFieldError> fieldErrors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			CustomFieldError fieldError = new CustomFieldError();
			fieldError.setField(((FieldError) error).getField());
			fieldError.setMessage(error.getDefaultMessage());
			fieldErrors.add(fieldError);
		});

		fieldErrorResponse.setFieldErrors(fieldErrors);
		return ResponseEntity.badRequest().body(fieldErrorResponse);
	}
}
