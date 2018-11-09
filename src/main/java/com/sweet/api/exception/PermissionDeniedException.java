package com.sweet.api.exception;

public class PermissionDeniedException extends RuntimeException {
  public PermissionDeniedException(){

  }
  public PermissionDeniedException(String s){
    super(s);
  }
}
