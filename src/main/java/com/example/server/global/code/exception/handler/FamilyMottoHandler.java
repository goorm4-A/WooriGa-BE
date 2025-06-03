package com.example.server.global.code.exception.handler;

import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;

public class FamilyMottoHandler extends CustomException {
    public FamilyMottoHandler(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
