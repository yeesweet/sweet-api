package com.sweet.api.entity.res;

import lombok.Data;

@Data
public class SessionUserInfo {
  private String userId;
  private String token;
  private String openId;
  private String sessionKey;
}
