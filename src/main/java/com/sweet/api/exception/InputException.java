package com.sweet.api.exception;

public class InputException extends Exception {
  private String [] inputParams;

  public InputException(){
    super("input error");
  }
  public InputException(String message){
    super(message);
  }

  public InputException(String message, Throwable cause){
    super(message,cause);
  }
  public InputException(Throwable cause){
    super(cause);
  }

}
