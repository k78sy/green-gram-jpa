package com.green.greengram.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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

    // 참조 당하는 쪽은 항상 one, mappedBy는 상대방쪽에서 나를 참조하는 필드명
    // cascade All은 Feed가 삭제되면 참조하는 쪽도 삭제처리
    // orphanRemoval은 자식이 고아가 되면 해당 자식은 삭제처리, 즉, pics에서 특정 객체 삭제하면 그 객체와 맵핀되어있는 row가 삭제
    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedPic> pics = new ArrayList<>();

    // 댓글
    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedComment> comments = new ArrayList<>();

    // 좋아요
    @OneToMany(mappedBy = "feed", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeedLike> likes = new ArrayList<>();
}
