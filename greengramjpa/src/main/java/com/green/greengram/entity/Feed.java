package com.green.greengram.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Feed extends CreatedUpdatedAt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String location;

    @Column(length = 1000)
    private String contents;

    @ManyToOne // 다대하나// 앞에 있는게 나, to 상대방
    @JoinColumn(name = "user_id", nullable = false)
    private User writerUser;
}
