package com.nebula.common.exception;


/**
 * 短信发送太频繁
 *
 * @author feifeixia
 */
public class SmsTooMuchException extends BusinessException {

    private static final String MESSAGE = "短信发送太频繁";

    private static final long serialVersionUID = -8199473909884306578L;

    public SmsTooMuchException() {
        super(MESSAGE);
    }

    public SmsTooMuchException(String message) {
        super(message);
    }

}
