package com.services.direct.exception;

public class CustomException extends BusinessException {

	  private static final long serialVersionUID = 1L;

	  public CustomException(String message) {
	    super(message);
	  }
	  
	   /**
	    * constructor for FileNotFoundException
	    *
	    * @param message to set
	    * @param code to set
	    */
	  public CustomException(String message, String code) {
		  super(message, code);
	  }
}
