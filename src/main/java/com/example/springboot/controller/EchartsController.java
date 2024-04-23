package com.example.springboot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Blog;
import com.example.springboot.entity.User;
import com.example.springboot.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IBlogService blogService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IBlogTypeService  blogTypeService;

    @GetMapping("/likes")
    public Result likes() {
        List<Blog> blogs = blogService.list();

        List<Map<String, Object>> maps = new ArrayList<>();
        for (Blog blog : blogs) {
            Map<String, Object> map = new HashMap<>();
            map.put("name",blog.getName());
            map.put("value",blog.getPageviews());
            maps.add(map);
        }
        return Result.success(maps);
    }

    @GetMapping("/counts")
    public Result counts() {
        Map<String, Object> map = new HashMap<>();
        map.put("users",userService.count());
        map.put("blogs",blogService.count());
        map.put("blogs2",blogService.count(new LambdaQueryWrapper<Blog>().eq(Blog::getState,"审核通过")));
        map.put("comments",blogTypeService.count());
        return Result.success(map);
    }

    @GetMapping("/user")
    public Result user() {
        List<User> users = userService.list(new LambdaQueryWrapper<User>().eq(User::getRole, "ROLE_USER"));

        List<String> x = new ArrayList<>();
        List<Object> y = new ArrayList<>();
        for (User user : users) {
            x.add(user.getUsername());
            y.add(blogService.count(new LambdaQueryWrapper<Blog>().eq(Blog::getUser,user.getUsername())));
        }

        Map<String, Object> map = new HashMap<>();
        map.put("x", x);
        map.put("y", y);
        return Result.success(map);
    }
}
