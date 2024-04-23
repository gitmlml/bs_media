package com.example.springboot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.entity.Blog;
import com.example.springboot.entity.Collect;
import com.example.springboot.entity.Comment;
import com.example.springboot.entity.User;
import com.example.springboot.mapper.BlogMapper;
import com.example.springboot.service.IBlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.service.ICollectService;
import com.example.springboot.service.ICommentService;
import com.example.springboot.service.IUserService;
import com.example.springboot.utils.TokenUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 
 * @since 
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {


    @Resource
    private IUserService userService;

    @Resource
    private ICommentService commentService;

    @Resource
    private ICollectService collectService;


}
