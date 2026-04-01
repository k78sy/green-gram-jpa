package com.green.greengram.application.feedcomment;


import com.green.greengram.application.feedcomment.model.FeedCommentGetRes;
import com.green.greengram.entity.FeedComment;
import com.green.greengram.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface FeedCommentRepository extends JpaRepository<FeedComment, Long> {
    // JPQL
    @Query("Select new com.green.greengram.application.feedcomment.model.FeedCommentGetRes(fc.id, fc.feed.id, fc.comment, u.id, u.nm, u.pic, fc.createdAt) " +
            "FROM FeedComment fc JOIN fc.user u " +
            "WHERE fc.feed.id = :feedId")
    List<FeedCommentGetRes> getFeedCommentList(@Param("feedId") Long feedId,
                                               Pageable pageable);

    Optional<FeedComment> findByIdAndUser(Long id, User user);
}
