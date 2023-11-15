package com.example.mswp.service;

import com.example.mswp.dto.LikesDto;
import com.example.mswp.dto.UserDto;
import com.example.mswp.entity.Likes;
import com.example.mswp.repository.JpaLikesRepository;
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
    private final RedisTemplate<String, String> redisTemplate;

    @Transactional
    public Optional<Likes> clickLike(LikesDto likesDto) {

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

    public Map<String, List<Object>> countLike(String id) {

        Map<String, List<Object>> res = new HashMap<>();

        //like table의 idTo 값으로 idFrom 값 전부 가져오기
        List<Object> list = jpalikesRepository.findByIdFromAndExpiredAtIsNull(id);

        res.put("list", list);

        return res;

    }

    public Set<String> test(UserDto userDto) {

        //redis 저장을 위한 Set
        SetOperations<String, String> redisSet = redisTemplate.opsForSet();
        //return하기 위한 Set (key로 Value 전체 가져옴)
        Set<String> members;
        List<Likes> likes = jpalikesRepository.findByIdToAndExpiredAtIsNull(userDto.getId());

        //List가 비어있는 경우
        if (likes.isEmpty()) {
            return null;
        } else {
            String key = likes.get(0).getIdTo();

            members = redisSet.members(key);

            if (redisSet.getOperations().hasKey(key)) {
                redisSet.remove(key, members.toArray());
                members.clear();
            }
            for (int i = 0; i < likes.size(); i++) {
                redisSet.add(key, likes.get(i).getIdFrom());
            }

            //redis 저장된 Set의 Key 기준 expire timeout 30minutes 설정
            redisTemplate.expire(key, 600, TimeUnit.SECONDS);

            members = redisSet.members(key);
        }

        return members;
    }

}
