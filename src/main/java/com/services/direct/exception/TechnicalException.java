package com.services.direct.exception;

/**
 * Technical Exception class
 *
 *
 */
public class TechnicalException extends BusinessException {

    private static final long serialVersionUID = 1L;

    /**
     * constructor for TechnicalException
     *
     * @param message to set
     */
    public TechnicalException(String message) {
        super(message);
    }

    /**
     * constructor for TechnicalException
     *
     * @param message to set
     * @param code to set
     */
    public TechnicalException(String message, String code) {
        super(message, code);
    }

    /**
     * constructor for TechnicalException
     *
     * @param cause to set
     */
    public TechnicalException(Throwable cause) {
        super(cause);
    }

    /**
     * constructor for TechnicalException
     *
     * @param message to set
     * @param cause to set
     */
    public TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
