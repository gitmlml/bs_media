package com.example.springboot.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * <p>
 * 收藏表
 * </p>
 *
 * @author
 * @since
 */
@Getter
@Setter
@ApiModel(value = "likes对象", description = "点赞表")
public class Likes implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @Alias("ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("文章")
    @Alias("文章")
    private Integer blogId;

    @ApiModelProperty("用户")
    @Alias("用户")
    private Integer userId;

    @ApiModelProperty("点赞时间")
    @Alias("点赞时间")
    private String time;

    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String blogName;

    @TableField(exist = false)
    private Blog blog;

}
