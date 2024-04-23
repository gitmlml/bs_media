package com.example.springboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.Result;
import com.example.springboot.entity.Likes;
import com.example.springboot.entity.User;
import com.example.springboot.service.IBlogService;
import com.example.springboot.service.ILikesService;
import com.example.springboot.service.IUserService;
import com.example.springboot.utils.TokenUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * <p>
 * 点赞表 前端控制器
 * </p>
 *
 * @author
 * @since
 */
@RestController
@RequestMapping("/likes")
public class LikesController {

    @Resource
    private ILikesService likesService;

    @Resource
    private IBlogService blogService;

    @Resource
    IUserService userService;

    private final String now = DateUtil.now();

    @GetMapping("/repeat/{blogId}/{userId}")
    public Result repeat(@PathVariable Integer blogId,@PathVariable Integer userId) {
        Likes one = likesService.getOne(new LambdaQueryWrapper<Likes>().eq(Likes::getUserId, userId).eq(Likes::getBlogId, blogId));
        if (null != one) {
            return Result.error("202", "false");
        } else {
            return Result.success();
        }
    }

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Likes likes) {
        User user = TokenUtils.getCurrentUser();
        if (user.getState().equals("封禁")){
            return Result.error("201","该账号已封禁");
        }

        Likes one = likesService.getOne(new LambdaQueryWrapper<Likes>().eq(Likes::getUserId, likes.getUserId()).eq(Likes::getBlogId,likes.getBlogId()));
        if (null != one){
            likesService.remove(new LambdaQueryWrapper<Likes>().eq(Likes::getBlogId,likes.getBlogId()).eq(Likes::getUserId,likes.getUserId()));
            return Result.error("202","已取消点赞");
        }

        likes.setTime(DateUtil.now());
        likesService.save(likes);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        likesService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        likesService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(likesService.list());
    }

    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        return Result.success(likesService.getById(id));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
            queryWrapper.like("name", name);
        }
//        User currentUser = TokenUtils.getCurrentUser();
//        if (currentUser.getRole().equals("ROLE_USER")) {
//            queryWrapper.eq("userid", currentUser.getId());
//        }
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getRole().equals("ROLE_USER")) {
            queryWrapper.eq("user_id", currentUser.getId());
        }
        Page<Likes> page = likesService.page(new Page<>(pageNum, pageSize), queryWrapper);
        page.getRecords().stream().forEach(likes -> {
            likes.setBlog(blogService.getById(likes.getBlogId()));
            likes.setBlogName(blogService.getById(likes.getBlogId()).getName());
            likes.setUserName(userService.getById(likes.getUserId()).getNickname());
        });
        return Result.success(page);
    }

    /**
    * 导出接口
    */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Likes> list = likesService.list();
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("Like信息表", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        out.close();
        writer.close();

        }

    /**
     * excel 导入
     * @param file
     * @throws Exception
     */
    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 通过 javabean的方式读取Excel内的对象，但是要求表头必须是英文，跟javabean的属性要对应起来
        List<Likes> list = reader.readAll(Likes.class);

        likesService.saveBatch(list);
        return Result.success();
    }

    private User getUser() {
        return TokenUtils.getCurrentUser();
    }

}

