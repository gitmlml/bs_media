<template>
  <div>
<!--    头部-->
    <div style="background-color: black; display: flex; height: 60px; line-height: 60px; border-bottom: 1px solid #eee">
      <div style="width: 400px; display: flex; padding-left: 30px">
        <div style="width: 50px">
          <img src="../../assets/logo.png" alt="" style="width: 40px; position: relative; top: 8px; right: 0">
        </div>
        <div style="flex: 1; color: #eeeeee">欢迎来到自媒体创意素材网站</div>
      </div>
      <div style="flex: 1;background-color: black;">
        <el-menu :default-active="$route.path" class="el-menu-demo" mode="horizontal" router style="border: none; height: 100%;background-color: black; ">
          <el-menu-item style="color: white;background-color: black;" class="custom-menu-item" index="/front/home">首页</el-menu-item>
          <el-menu-item style="color: white;background-color: black;" class="custom-menu-item" index="/front/addBlog">发布文章</el-menu-item>
          <el-menu-item style="color: white;background-color: black;" class="custom-menu-item" index="/front/notice">公告信息</el-menu-item>
          <el-menu-item style="color: white;background-color: black;" class="custom-menu-item" index="/front/personCentre">个人中心</el-menu-item>
      </el-menu>
      </div>

      <div style="text-align: center;background-color: black;">
        <el-input size="small" style="width: 300px;" placeholder="请输入文章名称/文章类别" v-model="name" suffix-icon="el-icon-search"></el-input>
        <el-button size="small" class="ml-5 styled-button " type="info"  @click="$router.push('/front/search?name=' + name)">搜索</el-button>
      </div>

      <div style="width: 200px; text-align: right; padding-right: 10px">
        <div v-if="!user.username" style="text-align: right; padding-right: 30px">
          <el-button @click="$router.push('/login')">登录</el-button>
          <el-button @click="$router.push('/register')">注册</el-button>
        </div>
        <div v-else>
          <el-dropdown style="width: 150px; cursor: pointer; text-align: right">
            <div style="display: inline-block">
              <img :src="user.avatarUrl" alt=""
                   style="width: 30px;height: 30px; border-radius: 50%; position: relative; top: 10px; right: 5px">
              <span style="color: white">{{ user.nickname }}</span><i class="el-icon-arrow-down" style="margin-left: 5px"></i>
            </div>
            <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
              <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                <router-link to="/front/password"><div>修改密码</div></router-link>
              </el-dropdown-item>
              <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                <router-link to="/front/person"><div>个人信息</div></router-link>
              </el-dropdown-item>
              <el-dropdown-item style="font-size: 14px; padding: 5px 0">
                <span style="text-decoration: none" @click="logout"><div>退出</div></span>
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </div>

<!--      <el-button @click="goToAddBlog" class="styled-button">发布 <i class="el-icon-circle-plus-outline"></i></el-button>-->

    </div>

    <div style="width: 1200px; margin: 5px auto;">
      <router-view  @refreshUser="getUser" />
    </div>
  </div>
</template>

<script>
import router from "../../router";

export default {
  name: "Front",
  data() {
    return {
      name: '',
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },

  created() {
  },
  methods: {
    goToAddBlog() {
      router.push({ name: 'AddBlog' }); // 触发路由跳转
    },
    logout() {
      this.$store.commit("logout")
      this.$message.success("退出成功")
    },
    getUser() {
      let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username : ""
      if (username) {
        // 从后台获取User数据
        this.request.get("/user/username/" + username).then(res => {
          // 重新赋值后台的最新User数据
          this.user = res.data
        })
      }
    }
  }
}
</script>

<style>
.item{
  display: inline-block;
  width: 100px;

  text-align: center;
  cursor: pointer
}
.item a {
  color: 	yellow;
}
.item:hover{
  background-color: 	LightPink;
}

.el-menu-demo{
  background-color: #6878c4;
}

.custom-menu-item:hover {
  background-color: rgb(244,208,212) !important;
}
.styled-button {
  /* 添加背景色和边框 */
  width: 70px;
  height: 32px;
  margin-top: 10px;
  margin-right: 5px;
  background-color:  rgb(244,208,212); /* 背景 */
  border: none;
  border-radius: 5px; /* 圆角按钮 */
  color: black; /* 白色文字 */
  /*padding: 10px 20px; !* 适当调整内边距 *!*/
  text-align: center; /* 居中文本 */
  text-decoration: none; /* 无下划线 */
  display: inline-block; /* 显示为行内块元素 */
  font-size: 12px; /* 调整字体大小 */
  /*margin: 4px 2px; !* 适当调整外边距 *!*/
  cursor: pointer; /* 鼠标悬停时显示为手型光标 */
}
/*.styled-button :hover{*/
/*  background-color: #6d9d88;*/
/*}*/
</style>
