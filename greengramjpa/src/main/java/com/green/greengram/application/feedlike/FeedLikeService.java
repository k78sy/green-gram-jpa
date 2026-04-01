package com.green.greengram.application.feedlike;

import com.green.greengram.application.feedlike.model.FeedLikeReq;
import com.green.greengram.entity.Feed;
import com.green.greengram.entity.FeedLike;
import com.green.greengram.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedLikeService {
    private final FeedLikeRepository feedLikeRepository;
    private final FeedLikeMapper feedLikeMapper;

    // 좋아요 처리 true
    // 좋아요 취소 false

    // 1경우. select. row가 있다? delete 처리하고 return false
    // 2경우. select. row가 없다? insert 처리하고 return true
    public boolean toggleFeedLike(FeedLikeReq req){

        User user = new User();
        user.setId( req.getSignedUserId() );

        Feed feed = new Feed();
        feed.setId( req.getFeedId() );

        FeedLike feedLike = feedLikeRepository.findByFeedAndUser( feed, user );

        // 좋아요 기록이 없으면 좋아요
        if(feedLike == null){
            FeedLike newFeedLike = new FeedLike( feed, user );
            feedLikeRepository.save( newFeedLike );
            return true;
        }
        // 이미 좋아요를 했으면 좋아요 취소
        feedLikeRepository.delete( feedLike );
        return false;

    }
}
