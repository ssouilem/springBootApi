package com.services.direct.exception;

import javax.persistence.EntityNotFoundException;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

//@ControllerAdvice
@Slf4j
public class MyExceptionHandler extends ResponseEntityExceptionHandler {
	
	private static Logger log = LoggerFactory.getLogger(MyExceptionHandler.class);

	/**
	 * Handle MissingServletRequestParameterException. Triggered when a 'required'
	 * request parameter is missing.
	 *
	 * @param ex
	 *            MissingServletRequestParameterException
	 * @param headers
	 *            HttpHeaders
	 * @param status
	 *            HttpStatus
	 * @param request
	 *            WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.debug("handleMissingServletRequestParameter -> Message Erreur :  " + ex.getParameterName());
		String error = ex.getParameterName() + " parameter is missing";
		ErrorMessage errorObj = new ErrorMessage(error, request.getDescription(false));
		return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.CONFLICT);
	}

	/**
	 * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is
	 * invalid as well.
	 *
	 * @param ex
	 *            HttpMediaTypeNotSupportedException
	 * @param headers
	 *            HttpHeaders
	 * @param status
	 *            HttpStatus
	 * @param request
	 *            WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.debug("handleHttpMediaTypeNotSupported -> Message Erreur :  " + ex.getContentType());
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
		ErrorMessage errorObj = new ErrorMessage(builder.substring(0, builder.length() - 2),
				request.getDescription(false));
		return new ResponseEntity<>(errorObj, headers, HttpStatus.UNSUPPORTED_MEDIA_TYPE);

	}

	/**
	 * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid
	 * validation.
	 *
	 * @param ex
	 *            the MethodArgumentNotValidException that is thrown when @Valid
	 *            validation fails
	 * @param headers
	 *            HttpHeaders
	 * @param status
	 *            HttpStatus
	 * @param request
	 *            WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.debug("handleMethodArgumentNotValid -> Message Erreur :  " + ex.getMessage());
		ErrorMessage errorObj = new ErrorMessage("Validation error" + ex.getBindingResult().getFieldErrors(),
				request.getDescription(false));
		return new ResponseEntity<>(errorObj, headers, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles javax.validation.ConstraintViolationException. Thrown when @Validated
	 * fails.
	 *
	 * @param ex
	 *            the ConstraintViolationException
	 * @return the ApiError object
	 */
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex) {
		log.debug("handleConstraintViolation -> Message Erreur :  " + ex.getMessage());
		ErrorMessage errorObj = new ErrorMessage("Validation error" + ex.getConstraintViolations());
		return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles EntityNotFoundException. Created to encapsulate errors with more
	 * detail than javax.persistence.EntityNotFoundException.
	 *
	 * @param ex
	 *            the EntityNotFoundException
	 * @return the ApiError object
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		log.debug("handleEntityNotFound -> Message Erreur :  " + ex.getMessage());
		ErrorMessage errorObj = new ErrorMessage(ex.getMessage());
		return new ResponseEntity<>(errorObj, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}
	 
	/**
	 * Handle HttpMessageNotReadableException. Happens when request JSON is
	 * malformed.
	 *
	 * @param ex
	 *            HttpMessageNotReadableException
	 * @param headers
	 *            HttpHeaders
	 * @param status
	 *            HttpStatus
	 * @param request
	 *            WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.info("handleHttpMessageNotReadable -> Message Erreur :  " + ex.getMessage());
		ServletWebRequest servletWebRequest = (ServletWebRequest) request;
		log.info("{} to {}", servletWebRequest.getHttpMethod());
		// servletWebRequest.getRequest().getServletPath());

		String error = " Malformed JSON request";
		ErrorMessage errorObj = new ErrorMessage(error, request.getDescription(false));
		return new ResponseEntity<>(errorObj, headers, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle HttpMessageNotWritableException.
	 *
	 * @param ex
	 *            HttpMessageNotWritableException
	 * @param headers
	 *            HttpHeaders
	 * @param status
	 *            HttpStatus
	 * @param request
	 *            WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.debug("handleHttpMessageNotWritable -> Message Erreur :  " + ex.getMessage());
		String error = "Error writing JSON output";
		return new ResponseEntity<>(error, headers, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handle NoHandlerFoundException.
	 *
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		log.debug("handleNoHandlerFoundException -> Message Erreur :  " + ex.getMessage());
		ErrorMessage errorObj = new ErrorMessage(
				String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()),
				request.getDescription(false));
		return new ResponseEntity<>(errorObj, headers, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle DataIntegrityViolationException, inspects the cause for different DB
	 * causes.
	 *
	 * @param ex
	 *            the DataIntegrityViolationException
	 * @return the ApiError object
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
			WebRequest request) {
		log.debug("handleDataIntegrityViolation -> Message Erreur :  " + ex.getMessage());
		if (ex.getCause() instanceof ConstraintViolationException) {
			return new ResponseEntity<>(new ErrorMessage("Database error" + ex.getCause()), new HttpHeaders(),
					HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(new ErrorMessage("Database error" + ex.getMessage()), new HttpHeaders(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
