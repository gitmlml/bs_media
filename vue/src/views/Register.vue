<template>
  <div class="wrapper">
<!--    <div style="height: 60px; line-height: 60px; font-size: 20px; padding-left: 50px; color: mediumseagreen;-->
<!--      background-color: rgba(0,0,0,0.2)">自媒体创意素材网站</div>-->
    <div style="margin: 50px auto; background-color: rgba(243,233,233,0.8); width: 480px; height: 520px; padding: 20px; border-radius: 10px">
      <div style="margin: 20px 0; text-align: center; font-size: 24px;color:#6d9d88"><b>欢迎注册</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item class="flex-form-item" label="账号" prop="username">
          <el-input style="width: 330px" size="medium" prefix-icon="el-icon-user" placeholder="请输入账号" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item class="flex-form-item" label="密码" prop="password">
          <el-input style="width: 330px" size="medium" prefix-icon="el-icon-lock" placeholder="请输入密码" show-password  v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item class="flex-form-item" label="确认密码" style="margin-left: -8px" prop="confirmPass">
          <el-input style="width: 330px" size="medium" prefix-icon="el-icon-lock" placeholder="请确认密码" show-password  v-model="user.confirmPass"></el-input>
        </el-form-item>
        <el-form-item class="flex-form-item" label="邮箱" prop="email">
          <el-input style="width: 330px" size="medium" prefix-icon="el-icon-message" placeholder="请输入邮箱" v-model="user.email"></el-input>
        </el-form-item>
        <el-form-item class="flex-form-item" label="手机" prop="phone">
          <el-input style="width: 330px" size="medium" prefix-icon="el-icon-phone" placeholder="请输入手机号" v-model="user.phone"></el-input>
        </el-form-item>

        <el-form-item class="flex-form-item" prop="sex" label="性别">
          <template>
            <el-radio v-model="user.sex" label="男">男</el-radio>
            <el-radio v-model="user.sex" label="女">女</el-radio>
          </template>
        </el-form-item>
        <template>
          <div>
            <el-form-item style="flex: 1; display: flex; justify-content: center;">
              <el-button type="success" style="width: 190px; font-size: 16px; height: 40px; margin: 0 auto;background-color: #6d9d88;border-color: #6d9d88" autocomplete="off" @click="login">注册</el-button>
            </el-form-item>
            <el-form-item style="margin-left: 290px">
              已有账号？请 <a href="/login" style="text-decoration: none; color: blue; cursor: pointer;">登录</a>
            </el-form-item>
          </div>
        </template>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Login",
  data() {
    // 密码的验证码校验
    const validatePassword = (rule, confirmPass, callback) => {
      // 其他验证逻辑保持不变
      // 添加新的验证逻辑来检查密码是否包含至少一个字母和一个数字
      const hasLetter = /[a-zA-Z]/.test(confirmPass);
      const hasNumber = /\d/.test(confirmPass);

      if (!hasLetter || !hasNumber) {
        callback(new Error('密码必须包含至少一个字母和一个数字'));
      } else {
        callback();
      }
    };
    // 邮箱验证
    const validateEmail = (rule, email, callback) => {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!emailRegex.test(email)) {
        callback(new Error('请输入正确的邮箱地址'));
      } else {
        callback();
      }
    };
    // 手机号码验证
    const validatePhone = (rule, phone, callback) => {
      const phoneRegex = /^\d{11}$/; // 假设手机号码是11位数字
      if (!phoneRegex.test(phone)) {
        callback(new Error('请输入正确的手机号码'));
      } else {
        callback();
      }
    };
    return {
      user: {},
      rules: {
        username: [
          { required: true, message: '请输入账号', trigger: 'blur' },
          { min: 3, max: 6, message: '账号长度应在3-8个字符之间', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 8, message: '密码长度应在8-15个字符之间', trigger: 'blur' },
        ],
        confirmPass: [
          { required: true, message: '请再次输入密码', trigger: 'blur' },
          { validator: validatePassword, trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱', trigger: 'blur' },
          { validator: validateEmail, trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号码', trigger: 'blur' },
          { validator: validatePhone, trigger: 'blur' } // 添加手机号码验证规则
        ],
        birth: [
          { required: true, message: '请选择出生日期', trigger: 'blur' },
        ],
        sex: [
          { required: true, message: '请选择性别', trigger: 'blur' },
        ],
      }
    }
  },
  methods: {
    login() {
      this.$refs['userForm'].validate((valid) => {
        if (valid) {  // 表单校验合法
          if (this.user.password !== this.user.confirmPass) {
            this.$message.error("两次输入的密码不一致")
            return false
          }
          this.request.post("/user/register", this.user).then(res => {
            if(res.code === '200') {
              this.$message.success("注册成功")
              this.$router.push("/login")
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      });
    }
  }
}
</script>

<style>
.wrapper {
  height: 100vh;
  background-image: url("../assets/bg3.jpg");
  background-size: cover;
  overflow: hidden;
}
.card {
  padding: 20px;
  background-color: #fff;
  border-radius: 5px;
  box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.1);
}
.flex-form-item {
  display: flex;
  align-items: center; /* 垂直居中 */
  margin-left: 20px;
}
</style>
