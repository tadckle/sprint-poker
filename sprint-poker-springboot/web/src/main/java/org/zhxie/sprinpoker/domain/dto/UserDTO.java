package org.zhxie.sprinpoker.domain.dto;

public class UserDTO {

   private String userName;
   private String password;
   private String matchPassword;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMatchPassword() {
    return matchPassword;
  }

  public void setMatchPassword(String matchPassword) {
    this.matchPassword = matchPassword;
  }
}
