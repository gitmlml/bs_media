package com.example.springboot.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelWriter;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import java.net.URLEncoder;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springboot.common.annotation.AutoLog;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.springboot.common.Result;
import org.springframework.web.multipart.MultipartFile;
import com.example.springboot.entity.User;
import com.example.springboot.utils.TokenUtils;
import com.example.springboot.service.IUserService;

import com.example.springboot.service.IBlogService;
import com.example.springboot.entity.Blog;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author
 * @since
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Resource
    private IBlogService blogService;

    @Resource
    IUserService userService;

    private final String now = DateUtil.now();

    /**
     * 文章排行榜
     * @return
     */
//  返回的是按页面浏览量排序的文章列表
    @GetMapping("/top")
    public Result top() {
        List<Blog> blogs = blogService.list(new LambdaQueryWrapper<Blog>().eq(Blog::getState,"审核通过").orderByDesc(Blog::getPageviews));
        return Result.success(blogs);
    }

    /**
     * 推荐文章
     * @return
     */
//    返回的是按时间戳排序的前8个文章
//    这是获取了所有文章，没有筛选出是否审核
//    @GetMapping("/recommend")
//    public Result recommend() {
//        List<Blog> collect = blogService.list(new LambdaQueryWrapper<Blog>().orderByDesc(Blog::getTime)).stream().limit(8).collect(Collectors.toList());
//        return Result.success(collect);
//    }
    //筛选出审核过的
    @GetMapping("/recommend")
    public Result recommend() {
        List<Blog> blogs = blogService.list(new LambdaQueryWrapper<Blog>().eq(Blog::getState,"审核通过").orderByDesc(Blog::getTime));
        return Result.success(blogs);
    }

    @PostMapping("/label")
    public Result label(@RequestBody List<Integer> ids) {
        for (Integer id : ids) {
            System.out.println(id);
        }
        return Result.success();
    }

    @GetMapping("/pageviews/{id}")
    public Result pageviews(@PathVariable Integer id) {
        if (id != null){
            Blog blog = blogService.getById(id);
            blog.setPageviews(blog.getPageviews() + 1);
            blogService.updateById(blog);
            return Result.success(blog);
        }
        return Result.success();
    }

    // 新增或者更新
    @PostMapping
    public Result save(@RequestBody Blog blog) {
        if (blog.getId() == null) {
            User user = TokenUtils.getCurrentUser();
            if (user.getState().equals("封禁")){
                return Result.error("201","该账号已封禁");
            }

            blog.setPageviews(0);
            blog.setTime(DateUtil.now());
            blog.setUser(user.getUsername());
            if (Objects.isNull(blog.getBlogType())){
                return Result.error("201","请选择文章类别");
            }
        }
        blogService.saveOrUpdate(blog);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        blogService.removeById(id);
        return Result.success();
    }

    @PostMapping("/del/batch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        blogService.removeByIds(ids);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        return Result.success(blogService.list());
    }


    @GetMapping("/{id}")
    public Result findOne(@PathVariable Integer id) {
        Blog blog = blogService.getById(id);
        blog.setUserDB(userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername,blog.getUser())));
        return Result.success(blog);
    }

    @GetMapping("/page/type")
    public Result findPageType(@RequestParam(defaultValue = "") String name,
                               @RequestParam(defaultValue = "") String type,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("state","审核通过");
        if (!"".equals(name)) {
//            queryWrapper.like("name", name);
            queryWrapper.and(wrapper -> wrapper.or(wrapper1 -> wrapper1.like("name",name))
                    .or(wrapper2 -> wrapper2.like("blog_type",name)));
        }

        if (!"".equals(type) && !"0".equals(type)) {
            queryWrapper.like("blog_type", type);
        }
        return Result.success(blogService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping("/page/front")
    public Result findPageFront(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
//            queryWrapper.like("name", name);
            queryWrapper.and(wrapper -> wrapper.or(wrapper1 -> wrapper1.like("name",name))
                    .or(wrapper2 -> wrapper2.like("blog_type",name)));
        }
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getRole().equals("ROLE_USER")) {
            queryWrapper.eq("user", currentUser.getUsername());
        }
        return Result.success(blogService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (!"".equals(name)) {
//            queryWrapper.like("name", name);
            queryWrapper.and(wrapper -> wrapper.or(wrapper1 -> wrapper1.like("name",name))
                    .or(wrapper2 -> wrapper2.like("blog_type",name)));
        }
        User currentUser = TokenUtils.getCurrentUser();
        if (currentUser.getRole().equals("ROLE_USER")) {
            queryWrapper.eq("user", currentUser.getNickname());
        }
        return Result.success(blogService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }

    @GetMapping("/page/search")
    public Result findPageSearch(@RequestParam(defaultValue = "") String name,
                           @RequestParam Integer pageNum,
                           @RequestParam Integer pageSize) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        queryWrapper.eq("state","审核通过");
        if (!"".equals(name)) {
//            queryWrapper.like("name", name);
            queryWrapper.and(wrapper -> wrapper.or(wrapper1 -> wrapper1.like("name",name))
                    .or(wrapper2 -> wrapper2.like("blog_type",name)));
        }
        return Result.success(blogService.page(new Page<>(pageNum, pageSize), queryWrapper));
    }


    /**
    * 导出接口
    */
    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 从数据库查询出所有的数据
        List<Blog> list = blogService.list();
        // 在内存操作，写出到浏览器
        ExcelWriter writer = ExcelUtil.getWriter(true);

        // 一次性写出list内的对象到excel，使用默认样式，强制输出标题
        writer.write(list, true);

        // 设置浏览器响应的格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("Blog信息表", "UTF-8");
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
        List<Blog> list = reader.readAll(Blog.class);

        blogService.saveBatch(list);
        return Result.success();
    }

    private User getUser() {
        return TokenUtils.getCurrentUser();
    }

}

