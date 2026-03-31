package com.green.greengram.application.feed;

import com.green.greengram.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    // 안에 더 메소드를 쓰지 않더라도 기본적인 CRUD 가능
    // 하이버네이트가 메소드를 구현해 빈등록까지 해줌
}
