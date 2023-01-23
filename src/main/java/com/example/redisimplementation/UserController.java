package com.example.redisimplementation;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {


    @Autowired
    RedisTemplate<String,User> redisTemplate;


    @Autowired
    ObjectMapper objectMapper;


    @PostMapping("/add_value")
    public void addValue(@RequestParam("id")String id,@RequestBody User user){

        redisTemplate.opsForValue().set(id,user);
    }

    @GetMapping("/get_value")
    public User getValue(@RequestParam("id")String id){

       User user = redisTemplate.opsForValue().get(id);

       return user;
    }

    @PostMapping("/lpush")
    public void lpush(@RequestParam("key")String key,@RequestBody User user){

        redisTemplate.opsForList().leftPush(key,user);
    }

    @GetMapping("/lpop")
    public List<User> lpop(@RequestParam("key")String key,@RequestParam("count")int count){

        List<User> users = redisTemplate.opsForList().leftPop(key,2);

        return users;
    }

    @PostMapping("/hmset")
    public void hmset(@RequestParam("key")String key,@RequestBody User user){

        Map map = objectMapper.convertValue(user,Map.class);

        redisTemplate.opsForHash().putAll(key,map);
    }

    @GetMapping("/hgetattribute")
    public String getAttribute(@RequestParam("key")String key,@RequestParam("attribute")String attribute){

        Map map = redisTemplate.opsForHash().entries(key);

        return (String) map.get(attribute);
    }


}
