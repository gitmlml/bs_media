package com.example.springboot.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springboot.entity.Likes;
import com.example.springboot.mapper.LikesMapper;
import com.example.springboot.service.ILikesService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收藏表 服务实现类
 * </p>
 *
 * @author
 * @since
 */
@Service
public class LikesServiceImpl extends ServiceImpl<LikesMapper, Likes> implements ILikesService {

}
