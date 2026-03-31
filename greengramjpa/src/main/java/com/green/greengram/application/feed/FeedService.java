package com.green.greengram.application.feed;

import com.green.greengram.application.feed.model.*;
import com.green.greengram.application.user.UserRepository;
import com.green.greengram.configuration.util.ImgUploadManager;
import com.green.greengram.configuration.util.MyFileUtil;
import com.green.greengram.entity.Feed;
import com.green.greengram.entity.FeedPic;
import com.green.greengram.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper feedMapper;
    private final FeedRepository feedRepository;
    private final FeedPicRepository feedPicRepository;
    private final ImgUploadManager imgUploadManager;
    private final MyFileUtil myFileUtil;
    private final UserRepository userRepository;

    @Transactional
    public FeedPostRes postFeed(FeedPostReq req, List<MultipartFile> pics){
        Feed newFeed = new Feed();

        // 프론트에서 받아온 데이터 넣기
        newFeed.setContents( req.getContents() );
        newFeed.setLocation( req.getLocation() );

        // 로그인 유저 데이터 넣기
        User signedUser = new User();
        signedUser.setId( req.getSignedUserId() );
        newFeed.setWriterUser( signedUser );

        // 현 시점까지는 newFeed는 영속성이 없음

        feedRepository.save( newFeed ); // 이 시점부터 FeedId 존재
        // save 메소드 호출 후 newFeed 영속성이 생긴다.

        // feedId 그냥 가져오면 됨
        long feedId = newFeed.getId();
        log.info("feedId:{}", feedId);

        // feed
        List<String> picSavedNames = imgUploadManager.saveFeedPics( feedId, pics );

        try {
            for(String pic : picSavedNames){
                FeedPic newFeedPic = new FeedPic( newFeed, pic );
                feedPicRepository.save( newFeedPic );
            }
        } catch (Exception e) {
            //사진을 지운다.
            String directoryPath = String.format("%s/feed/%d", myFileUtil.fileUploadPath, feedId);
            log.info("directoryPath: {}", directoryPath);
            myFileUtil.deleteDirectory(directoryPath);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "SQL Syntax Error 발생");
        }
        return new FeedPostRes(feedId,picSavedNames);
    }

    public List<FeedGetRes> getFeedList(FeedGetReq req){
        List<FeedGetRes> list = feedMapper.findAll(req);
        // 작업! 피드당 사진 정보를 가져오는 작업을 해야한다
        // 실무에서 사용하지 않는 방법. N+1 이슈를 해결하는게 쉽지 않다
        for(FeedGetRes res : list){
            // 사진 가져오는 select
            List<String> pics = feedMapper.findPicsById(res.getId());
            res.setPics(pics);
            
        }
        return list;
    }

    @Transactional
    public int deleteFeed(FeedDeleteReq req){
        // feed_pic, feed_like, feed_comment 먼저 삭제
        feedMapper.deleteRef( req );

        //feed 테이블의 row는 가장 마지막에 삭제 처리
        feedMapper.delete( req );

        // 폴더 째 삭제
        String delDirectoryPath = String.format( "%s/feed/%d", myFileUtil.fileUploadPath, req.getFeedId() );
        myFileUtil.deleteDirectory(delDirectoryPath);
        return 0;
    }
}
