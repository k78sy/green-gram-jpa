package com.green.greengram.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity // 테이블을 만든다.
@Getter
@Setter
@ToString
public class FeedComment extends CreatedUpdatedAt{
    @Id // 대리키 PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // AUTO_INCREMENT
    private Long id;

    @ManyToOne // 다대하나 // 앞에 있는게 나, to 상대방
    @JoinColumn(name = "feed_id", nullable = false)
    private Feed feed;

    @ManyToOne // 다대하나 // 앞에 있는게 나, to 상대방
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 1000)
    private String comment;
}
