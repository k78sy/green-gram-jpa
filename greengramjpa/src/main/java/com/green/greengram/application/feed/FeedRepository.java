package com.green.greengram.application.feed;

import com.green.greengram.entity.Feed;
import com.green.greengram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    // 안에 더 메소드를 쓰지 않더라도 기본적인 CRUD 가능
    // 하이버네이트가 메소드를 구현해 빈등록까지 해줌

    // 쿼리 메소드로 id, user_id를 where조건으로 해서 1개의 row를 가져오고 싶다
    Optional<Feed> findByIdAndWriterUser(Long id, User signedUser);
}
