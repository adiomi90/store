package com.adiomiDev.store.exception;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.ValidationException;

@Getter
@Setter
public class NotFoundException extends ValidationException {
    public NotFoundException(String message) {
        super(message);
    }
}
