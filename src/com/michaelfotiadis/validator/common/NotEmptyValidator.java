package com.michaelfotiadis.validator.common;

/**
 *
 */
public class NotEmptyValidator implements StringValidator {

    @Override
    public boolean validate(final String input) {
        if (input != null && !input.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
