package com.bithumb.auth.auth.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
public class TokenResponseDto {

  private long id;
  private String grantType;
  private String accessToken;
  private String refreshToken;
  private Long accessTokenExpiresIn;

  @Builder
  public TokenResponseDto(long id, String grantType, String accessToken,
      String refreshToken, Long accessTokenExpiresIn) {
    this.id = id;
    this.grantType = grantType;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
    this.accessTokenExpiresIn = accessTokenExpiresIn;
  }


  public static TokenResponseDto of(long id, TokenDto tokenDto) {
    return new TokenResponseDto(id, tokenDto.getGrantType(), tokenDto.getAccessToken(),
        tokenDto.getRefreshToken(), tokenDto.getAccessTokenExpiresIn());
  }
}
