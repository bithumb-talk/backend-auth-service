package com.bithumb.auth.board.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "comment")
@Entity
@ToString
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @Column(name = "table_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tableNo;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "comment_id", nullable = false)
    private Long commnetId;


}
