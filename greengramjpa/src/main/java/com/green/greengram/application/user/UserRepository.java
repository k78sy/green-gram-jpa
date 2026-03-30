package com.green.greengram.application.user;

import com.green.greengram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<어떤 엔터티랑 연결하나, 그 엔터티의 PK타입이 무엇이냐 >
public interface UserRepository extends JpaRepository<User, Long> {
    // 메소드쿼리. 메소드 이름으로 쿼리문을 만든다.
    User findByUid( String uid );
}
