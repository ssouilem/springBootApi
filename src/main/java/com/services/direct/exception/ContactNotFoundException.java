package com.services.direct.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "contact can not be found!")
public class ContactNotFoundException extends BusinessException {

    private static final long serialVersionUID = 1L;

    /**
     * constructor for SharingNotFoundException
     *
     * @param message to set
     */
    public ContactNotFoundException(String message) {
        super(message);
    }

    /**
     * constructor for SharingNotFoundException
     *
     * @param message to set
     * @param code to set
     */
    public ContactNotFoundException(String message, String code) {
        super(message, code);
    }
}
