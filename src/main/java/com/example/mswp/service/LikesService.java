package com.example.mswp.service;

import com.example.mswp.dto.LikesDto;
import com.example.mswp.dto.UserDto;
import com.example.mswp.entity.Likes;
import com.example.mswp.repository.JpaLikesRepository;

import com.example.mswp.repository.JpaUserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final JpaLikesRepository jpalikesRepository;
    private final JpaUserRepository jpaUserRepository;
    private final RedisTemplate<String, String> redisTemplate;

    //상대방 좋아요 누르기
    @Transactional
    public Optional<Likes> click(LikesDto likesDto) {

        //좋아요 상태 변화를 위한 데이터
        Optional<Likes> likes = jpalikesRepository.findByIdToAndIdFrom(likesDto.getIdTo(), likesDto.getIdFrom());

        //likes 가져오고 expired_at이 Null인 경우
        if (likes.isPresent() && likes.get().getExpiredAt() == null) {
            likes.get().setExpiredAt(LocalDateTime.now());
        }
        //likes를 가져오고 expired_at이 Null이 아닌 경우
        else if (likes.isPresent()) {
            likes.get().setExpiredAt(null);
        } else {
            Likes newLikes = likesDto.toEntity();
            newLikes.setIdFrom(likesDto.getIdFrom());
            newLikes.setIdTo(likesDto.getIdTo());
            jpalikesRepository.save(newLikes);
            likes = jpalikesRepository.findByIdToAndIdFrom(likesDto.getIdTo(), likesDto.getIdFrom());
        }

        return likes;
    }

    //나를 좋아요 하는 사람 수
    public Map<String, List<Likes>> count(String id) {
        //반환 데이터 res
        Map<String, List<Likes>> res = new HashMap<>();

        //like table의 idTo 값으로 idFrom 값 전부 가져오기
        List<Likes> list = jpalikesRepository.findByIdFromAndExpiredAtIsNull(id);

        res.put("list", list);

        return res;
    }

    //나를 좋아요 하는 사람 배열로 반환
    public Set<String> me(UserDto userDto) {

        //redis 저장을 위한 Set
        SetOperations<String, String> redisSet = redisTemplate.opsForSet();
        //return하기 위한 Set (key로 Value 전체 가져옴)
        Set<String> members;
        //좋아요 list
        List<Likes> likes = jpalikesRepository.findByIdToAndExpiredAtIsNull(userDto.getId());

        //List가 비어있는 경우
        if (likes.isEmpty()) {
            return null;
        } else {
            String key = likes.get(0).getIdTo();

            members = redisSet.members(key);

            //redis에 해당 키 값이 있는 경우
            if (redisSet.getOperations().hasKey(key)) {
                redisSet.remove(key, members.toArray());
                members.clear();
            }
            for (int i = 0; i < likes.size(); i++) {
                redisSet.add(key, likes.get(i).getIdFrom());
            }

            //redis 저장된 Set의 Key 기준 expire timeout 10m 설정
            redisTemplate.expire(key, 600, TimeUnit.SECONDS);

            members = redisSet.members(key);
        }

        return members;
    }

    //내가 좋아요 누른 사람 list
    public Map<Object, Object> list(String id) {
        //반환 데이터 res
        Map<Object, Object> res = new HashMap<>();
        //ID 기준 내가 좋아요 누른 사람 조회
        List<Likes> user = jpalikesRepository.findByIdFromAndExpiredAtIsNull(id);

        if(user.isEmpty()) {
            res.put("sc", 400);
            res.put("message", "No Data");
        } else {
            for(int i = 0; i < user.size(); i++) {
                res.put(i, jpaUserRepository.findById(user.get(i).getIdTo()));
            }
        }

        return res;
    }

}
