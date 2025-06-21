package com.example.server.global.code.exception.handler;

import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;

public class FamilyRecipeHandler extends CustomException {
    public FamilyRecipeHandler(ErrorStatus errorStatus) {
        super(errorStatus);
    }
}
