package com.nebula.common.exception;


/**
 * 用户未存在
 *
 * @author feifeixia
 */
public class UserNotExistException extends BusinessException {

    private static final long serialVersionUID = -8542578013805117153L;

    private static final String MESSAGE = "用户不存在";

    public UserNotExistException() {
        super(MESSAGE);
    }

    public UserNotExistException(String message) {
        super(message);
    }

}
