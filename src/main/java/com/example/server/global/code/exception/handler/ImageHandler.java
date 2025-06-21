package com.example.server.global.code.exception.handler;

import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;

public class ImageHandler extends CustomException {
  public ImageHandler(ErrorStatus errorStatus) {
    super(errorStatus);
  }
}
