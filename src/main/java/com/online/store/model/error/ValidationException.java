package com.online.store.model.error;

import java.util.List;

public class ValidationException extends RuntimeException {

    private final List<ErrorField> errors;

    public ValidationException(List<ErrorField> errors) {
        this.errors = errors;
    }

    public List<ErrorField> getErrors() {
        return errors;
    }
}
