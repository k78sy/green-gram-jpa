package com.green.greengram.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // 테이블을 만든다.
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name="uk_feed_pic", columnNames = {"feed_id", "pic"})
        }
)
public class FeedPic extends CreatedAt {
    @Id // 대리키 PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long id;

    @ManyToOne
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    @Column(nullable = false, length = 50)
    private String pic;

    public FeedPic(Feed feed, String pic) {
        this.feed = feed;
        this.pic = pic;
    }
}
