package com.sweet.api.commons;

public class ResponseMessage<T> {

  private int code;
  private String message;
  private T data;

  public ResponseMessage(){
    this(0);
  }

  public ResponseMessage(int code){
    this.code = code;
    if(code==0 && this.message==null){
      this.message="success";
    }
  }

  public ResponseMessage(int code, String message){
    this(code);
    this.message = message;
  }

  public ResponseMessage(int code, String message, T data){
    this(code,message);
    this.data = data;
  }

  public ResponseMessage(int code, T data){
    this(code);
    this.data = data;
  }

  public ResponseMessage(T data){
    this(0);
    this.data=data;
  }

  public ResponseMessage(String message){
    this(0,message);
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  @Override
  public String toString() {
    return "ResponseMessage{" +
        "code=" + code +
        ", message='" + message + '\'' +
        '}';
  }
}
