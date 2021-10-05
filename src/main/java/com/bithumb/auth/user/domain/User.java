package com.bithumb.auth.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@Table(name = "user")
@Entity
@ToString
@AllArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "user_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "user_password", nullable = false)
    private String password;

    @Column(name = "user_nickname", nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_authority", nullable = false)
    private Authority authority;

    @Column(name = "device_token", nullable = true)
    private String deviceToken;

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changeDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }


    @Builder
    public User(Long id, String userId, String password, String nickname,
        Authority authority) {
        this.id = id;
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.authority = authority;
    }

    @Builder
    public User(String userId, String password, String nickname,
        Authority authority) {
        this.userId = userId;
        this.password = password;
        this.nickname = nickname;
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", userId='" + userId + '\'' +
            ", password='" + password + '\'' +
            ", nickname='" + nickname + '\'' +
            ", authority=" + authority +
            '}';
    }



}
