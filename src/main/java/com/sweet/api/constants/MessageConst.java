package com.sweet.api.constants;

public interface MessageConst {

  int CODE_INNER_ERROR = 1001;
  String MSG_INNER_ERROR = "内部错误";

  int CODE_INPUT_ERROR = 1002;

  /**
   * 未登录异常编码
   */
  int CODE_PERMISSION_DENIED_ERROR = 1003;

  /**
   * 不允许访问接口异常编码
   */
  int CODE_NOT_ALLOWED_ERROR = 1004;
}
