<template>
  <el-card style="margin-top: 15px">
    <el-form label-width="100px" size="small" style="width: 90%">
      <el-form-item prop="blogType" label="文章类别">
        <template>
          <el-select v-model="form.blogType" placeholder="请选择">
            <el-option v-for="item in types" :key="item.name" :label="item.name" :value="item.name"></el-option>
          </el-select>
        </template>
      </el-form-item>
      <el-form-item label="文章名称">
        <el-input v-model="form.name" placeholder="请输入文章名称" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item prop="descr" label="简介">
        <el-input v-model="form.descr" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="封面">
        <el-upload

            class="avatar-uploader"
            :action="'http://localhost:9090/file/upload'"
            :show-file-list="false"
            :on-success="handleImgUploadSuccess"
        >
          <img  v-if="form.img" :src="form.img" class="avatar">
          <i style="border: 1px dashed #d9d9d9" v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>

      <el-form-item label="文章内容">
        <div id="richText"></div>
      </el-form-item>

    </el-form>
    <div style="margin-top: 10px;margin-left: 988px">
      <el-button size="small" type="success" @click="submit">提交</el-button>
    </div>
  </el-card>
</template>

<script>
import E from "wangeditor"

let editor;

export default {
  name: "AddBlog",
  data() {
    return {
      form: {
        img: ""
      },
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      types: []
    }
  },
  created() {
    // 初始化富文本编辑器
    this.handleAdd()

    this.request.get("/blogType").then(res => {
      this.types = res.data
    })
  },
  methods: {
    submit() {
      if (this.user.username == null){
        this.$message.warning("请登录")
        return;
      }
      let data = {
        name: this.form.name,
        content: editor.txt.html(),
        userId: this.user.id,
        img: this.form.img,
        descr: this.form.descr,
        blogType: this.form.blogType
      }
      console.log(data);
      if (data.blogType === null || data.blogType === '' || data.blogType === 'undefined'){
        this.$message.error("请选择文章类别")
        return;
      }
      this.request.post("/blog", data).then(res => {
        if (res.code === '200') {
          this.$message.success("发表成功")
          this.form = {}
          editor.txt.html('')
          this.load()
        } else if (res.code === '201'){
          this.$message.error(res.msg)
        } else {
          this.$message.error("发表失败")
        }
      })
    },
    handleAdd() {
      editor = ''
      this.$nextTick(() => {
        if (!editor) {
          editor = new E("#richText")
          // 配置 server 接口地址
          editor.config.uploadImgServer = 'http://localhost:9090/file/uploadImg'
          editor.config.uploadFileName = 'file'
          editor.create()
        }
        editor.txt.html('')
      })
    },
    handleImgUploadSuccess(res) {
      this.form.img = res
    },
  }
}
</script>

<style scoped>
.avatar-uploader {
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
}
.avatar {
  width: 138px;
  height: 138px;
  display: block;
}
</style>

