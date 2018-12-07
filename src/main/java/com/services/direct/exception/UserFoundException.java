package com.services.direct.exception;

/**
 * file not found exception class
 * 
 * 
 * Created by htaghbalout on 19/04/2016.
 */
public class UserFoundException extends BusinessException {

    private static final long serialVersionUID = 1L;

    /**
     * constructor for FileNotFoundException
     *
     * @param message to set
     */
    public UserFoundException(String message) {
        super(message);
    }

    /**
     * constructor for FileNotFoundException
     *
     * @param message to set
     * @param code to set
     */
    public UserFoundException(String message, String code) {
        super(message, code);
    }
}
