package com.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    ObjectMapper objectMapper;//can also use Gson in place of ObjectMapper

    private static final String REDIS_PREFIX_KEY="user::";
    private static final String CREATE_WALLET_TOPIC="wallet_create";
    private static final int REDIS_EXPIRY=12;

    public void createUser(UserRequest userRequest) throws JsonProcessingException {
        //create user
        User user=User.builder()
                .age(userRequest.getAge())
                .userId(userRequest.getUserId())
                .email(userRequest.getEmail())
                .name(userRequest.getName())
                .build();
        userRepository.save(user);

        //TODO: Insert data is Redis with some expiry
        saveUserInCache(user);


        //TODO: Publish a kafka event to trigger wallet creation
        JSONObject walletRequest=new JSONObject();
        walletRequest.put("userId",userRequest.getUserId());

        kafkaTemplate.send(CREATE_WALLET_TOPIC,//topic name
                userRequest.getUserId(),//for letting it go to same partition for a particular key
                objectMapper.writeValueAsString(walletRequest));//actual message


    }
    private void saveUserInCache(User user)
    {
        String redisKey=REDIS_PREFIX_KEY+user.getUserId();
        Map userMap = objectMapper.convertValue(user, Map.class);
        redisTemplate.opsForHash().putAll(redisKey,userMap);
        redisTemplate.expire(redisKey, Duration.ofHours(REDIS_EXPIRY));

    }
    public User getUserByUserId(String userId) throws Exception
    {

        try {
            //TODO: Query data from redis before calling from db
            Map userMap=redisTemplate.opsForHash().entries(REDIS_PREFIX_KEY+userId);
            User user ;
            if(userMap==null || userMap.size()==0)
            {
                user=userRepository.findByUserId(userId);
                if(user==null)
                {
                    throw new UserNotFoundException();
                }
             saveUserInCache(user);
            }
            else
            {
                user=objectMapper.convertValue(userMap,User.class);
            }
            return user;


        }
        catch(Exception exception)
        {
           throw new UserException();
        }
    }
}
