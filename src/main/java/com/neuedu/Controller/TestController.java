package com.neuedu.Controller;

import com.neuedu.Entity.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class TestController {

    @RequestMapping("/userlist")
    public List<User> userList()
    {
        List<User> userList = new ArrayList<>();

        User user1 = new User(1,"first",10);
        User user2=new User(2,"second",20);
        User user3=new User(3,"third",30);
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        return userList;
    }
}
