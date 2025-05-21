package com.example.server.global.code.exception.handler;

import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;

public class FamilyMemberHandler extends CustomException {
    public FamilyMemberHandler(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
