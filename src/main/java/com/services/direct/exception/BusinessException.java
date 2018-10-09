package com.services.direct.exception;

/**
 * business exception class
 * 
 * 
 * Created by htaghbalout on 26/05/2016.
 */
public class BusinessException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * code
     */
    private String code;

    /**
     * constructor for BusinessException
     *
     * @param message to set
     */
    public BusinessException(String message) {
        super(message);
    }

    /**
     * constructor for BusinessException
     *
     * @param message to set
     * @param cause to set
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * constructor for BusinessException
     *
     * @param cause to set
     */
    public BusinessException(Throwable cause) {
        super(cause);
    }

    /**
     * constructor for BusinessException
     *
     * @param message to set
     * @param code to set
     */
    public BusinessException(String message, String code) {
        super(message);
        this.code = code;
    }

    /**
     * @return code
     */
    public String getCode() {
        return code;
    }
}
