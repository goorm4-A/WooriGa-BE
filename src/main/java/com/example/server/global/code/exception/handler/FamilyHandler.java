package com.example.server.global.code.exception.handler;

import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;

public class FamilyHandler extends CustomException {
    public FamilyHandler(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
