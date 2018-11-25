package com.services.direct.exception;

/**
 * duplication exception class
 * 
 * Created by htaghbalout on 30/05/2016.
 */
public class DuplicateEntityException extends BusinessException {

    private static final long serialVersionUID = 1L;

    /**
     * constructor for DuplicateEntityException
     *
     * @param message to set
     */
    public DuplicateEntityException(String message) {
        super(message);
    }

    /**
     * constructor for DuplicateEntityException
     *
     * @param message to set
     * @param cause to set
     */
    public DuplicateEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
    * constructor for DuplicateEntityException
    *
    * @param message to set
    * @param code to set
    */
   public DuplicateEntityException(String message, String code) {
       super(message, code);
   }
    /**
     * constructor for DuplicateEntityException
     *
     * @param cause to set
     */
    public DuplicateEntityException(Throwable cause) {
        super(cause);
    }
}
