package com.sweet.api.exception;


import com.sweet.api.constants.MessageConst;

public class OrderException extends RuntimeException {
  private int code;

  public OrderException(int code, String message) {
    super(message);
    this.code = code;
  }

  public OrderException(String message){
    super(message);
    this.code= MessageConst.CODE_ERROR;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }
}
