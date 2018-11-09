package com.sweet.api.exception;

public class ShowNotAllowedException extends RuntimeException{
  public ShowNotAllowedException(){
    super("无权访问");
  }
}
