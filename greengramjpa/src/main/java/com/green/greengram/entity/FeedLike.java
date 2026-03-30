package com.green.greengram.entity;

import jakarta.persistence.*;
import lombok.*;

// 복합키: 컬럼 두개가 묶여 키가 되니 이미 유니크 속성
// 대리키 따로 생성 > 컬럼 두개가 일반 속성이 되는데, 그럼 중복이 들어가는 문제가 생기니 > 둘을 묶어 복합 유니크로

@Entity // 테이블을 만든다.
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자가 public이 아니라 protected가 되도록 설정
@Table(
        indexes = {
//                @Index(name="idx_feed_user", columnList = "feed_id, user_id"), // 복합 인덱스 설정
//                @Index(name="idx_feed", columnList = "feed_id") // 인덱스
        },
        uniqueConstraints = {
                @UniqueConstraint(name="uk_feed_user", columnNames = {"feed_id", "user_id"})
                // 유니크 속성 걸면 자동으로 인덱스 생성
        }
)
public class FeedLike extends CreatedAt {
    @Id // 대리키 PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long id;

    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public FeedLike(Feed feed, User user) {
        this.feed = feed;
        this.user = user;
    }
}