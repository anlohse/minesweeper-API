package com.anlohse.minesweeper.commons.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class MinesweeperException extends RuntimeException {
    public MinesweeperException() {
    }

    public MinesweeperException(String message) {
        super(message);
    }

    public MinesweeperException(String message, Throwable cause) {
        super(message, cause);
    }

    public MinesweeperException(Throwable cause) {
        super(cause);
    }

    public MinesweeperException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
