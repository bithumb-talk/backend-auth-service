package com.bithumb.auth.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "user")
@Entity
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

//    @OneToMany(mappedBy="user")
//    private List<Board> boards;

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    /*
        @Builder
        public User(String userId, String password, Authority authority) {
            this.userId = userId;
            this.password = password;
            this.authority = authority;
        }
    */
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

    /*public User of(String userId, String password, Authority authority, PasswordEncoder passwordEncoder) {
        return User.builder()
            .userId(userId)
            .password(passwordEncoder.encode(password))
            .authority(Authority.ROLE_USER)
            .build();
    }*/

}
