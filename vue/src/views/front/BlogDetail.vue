<template>
  <div style="padding: 10px">
    <el-row :gutter="10">
      <el-col :span="18">
        <el-card style="height: 120px">
          <div class="item1">
            <div>
              <span style="font-size: 25px;font-weight:bold;margin-bottom:10px;color: #E6A23C">{{ blog.name }}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            </div>
            <div style="margin-top: 10px">
              <i class="el-icon-user"></i> <span style="color: #222">{{blog.user }}</span>&nbsp;&nbsp;&nbsp;&nbsp;
              <i class="el-icon-date"></i> <span style="color: #222">{{blog.time }}</span>&nbsp;&nbsp;&nbsp;&nbsp;
              <i class="el-icon-s-flag"></i><span style="color: #222">{{blog.blogType }}</span>&nbsp;&nbsp;&nbsp;&nbsp;
              <i class="el-icon-eye"></i> <span style="color: #222">{{blog.pageviews }}</span>&nbsp;&nbsp;&nbsp;&nbsp;
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="height: 220px">
          <div>
            {{blog.user }}
          </div>
          <div>
            <el-image style="width: 100%; height: 170px" :src="blog.userDB.avatarUrl"></el-image>
          </div>
        </el-card>
      </el-col>
    </el-row>


    <el-row :gutter="10">
      <el-col :span="18">
        <!--    文章内容-->
        <el-card style="margin-top: -90px">
          <div style="padding: 10px 0; border-bottom: 1px solid #ccc; color: #1E90FF; font-size: 20px">
            文章内容
          </div>
          <div style="margin-top: 15px" v-html="blog.content"></div>
        </el-card>
        <!--   收藏点赞-->
        <el-card style="margin-top: 10px">
          <span class="collect-like" v-if="state" >
            <img src="../../assets/喜欢.png" alt="" style="width: 28px; " @click="collect" />
          </span>
          <span class="collect-like" v-if="!state" >
            <img src="../../assets/喜欢 (1).png" alt="" style="width: 28px;" @click="collect" />
          </span>
          <span class="collect-like" v-if="state1" >
           <img src="../../assets/赞.png" alt="" style="width: 28px; " @click="likes" />
          </span>
          <span class="collect-like" v-if="!state1" >
            <img src="../../assets/赞(1).png" alt="" style="width: 28px;" @click="likes" />
          </span>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="height: 500px;margin-top: 10px">
          <div style="padding: 10px 0; border-bottom: 1px solid #ccc; color: orangered; font-size: 20px;font-weight: bold">
            <span style="text-align: center">最新</span>
          </div>
          <div v-for="item in recommend" :key="item.id" style="margin: 10px 0">
            <span style="cursor: pointer;margin-top: 5px" @click="$router.push('/front/blogDetail?id=' + item.id)">
              {{item.name}}
            </span>
          </div>

        </el-card>

        <el-card style="margin-top: 10px">
          <div style="display: flex; grid-gap: 10px; ">
            <div style="flex: 1; line-height: 25px">
              &nbsp;&nbsp;探索未知世界，发现你下一个目的地，走在路上，与你同行。
            </div>
            <img src="../../assets/广告.gif" alt="" style="width: 80px; height: 80px; border-radius: 5px">
          </div>
        </el-card>
      </el-col>
    </el-row>

<!--    评论模块-->
    <el-card style="margin-top: 15px">
      <div style="width: 1400px; margin: 0 auto">
        <div style="padding: 10px 0;  border-bottom: 1px solid #ccc; color: #1E90FF; font-size: 20px;width: 81%">
          评论
        </div>
        <div style="margin: 20px 0">
          <div style="margin: 10px 0">
            <el-input type="textarea" placeholder="请输入留言" v-model="comment.content" style="width: 81%"></el-input>
            <div style="text-align: right; margin: 10px 0">
              <el-button style="margin: 10px 265px 0 0" type="primary" @click="submit2">提交</el-button>
            </div>
          </div>
        </div>

        <div style="margin: 20px 0">
          <div
              style="margin: 10px 0; font-size: 24px; padding: 10px 0; border-bottom: 1px solid #ccc; text-align: left">
            评论列表
          </div>

          <div style="margin: 20px 0;  text-align: left;">
            <div style="padding: 10px 0; " v-for="item in comments" :key="item.id">
              <div style="display: flex">
                <div style="width: 80px">
                  <el-avatar :size="50" :src="item.img"></el-avatar>
                </div>
                <div style="flex: 1">
                  <div style="display: flex">
                    {{ item.username }} <span style="margin-left: 10px">{{ item.time }}</span>
                  </div>
                  <div style="margin-top: 10px; color: #666">{{ item.content }}</div>
                  <div>
                    <el-button type="text" @click="reply(item.id, item.username)">回复</el-button>
                  </div>

                  <!-- 回复列表 -->
                  <div v-if="item.children.length"
                       style="margin-left: 50px; width: 72%;background-color: aliceblue; padding: 10px; border-radius: 10px">
                    <div v-for="sub in item.children" :key="sub.id">
                      <div style="padding:5px 0">
                        <div style="width: 100px;display: flex;align-items: center">
                          <el-avatar :size="40" :src="sub.img"></el-avatar>&nbsp;
                          <span style="cursor: pointer;font-weight:bold" @click="reply(sub.pid, sub.userId)">{{
                              sub.username
                            }}</span>
                        </div>
                        <br>
                        <span style="margin-left: 47px">
                        回复 <span style="color: cornflowerblue">@{{ sub.parentName }}</span>
                      <span style="color: #666;width: 100%; margin-left: 10px">{{ sub.content }}</span>
                    </span>
                        <span style="float: right; font-size: 13px; color: #666; margin-top: 3px">{{ sub.time }}</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

          </div>
        </div>

        <el-dialog title="回复" :visible.sync="dialogFormVisible3" width="50%">
          <el-form :model="replyComment">
            <el-form-item label="内容" >
              <el-input type="textarea" v-model="replyComment.content" style="width: 100%"></el-input>

            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible3 = false">取 消</el-button>
            <el-button type="primary" @click="saveReply">确 定</el-button>
          </div>
        </el-dialog>

      </div>
    </el-card>


  </div>
</template>

<script>
export default {
  name: "BlogDetail",
  data() {
    return {
      id: this.$route.query.id,
      blog: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      form: {},
      comments: [],
      comment: {
        content: ''
      },
      replyComment: {},
      dialogFormVisible3: false,
      dialogFormVisible4: false,
      state: true,
      state1: true,
      pageviews: 0,
      recommend:[]
    }
  },
  created() {
    this.request.get("/blog/pageviews/" + this.id).then(res => {
      if (res.code === '200') {
        this.pageviews = res.data.pageviews
      }
    })

    this.load()
    this.load2()
  },
  methods: {
    collect(){
      if (this.user.username == null){
        this.$message.warning("请登录")
        return;
      }
      let data = {blogId:this.id,userId:this.user.id}
      this.request.post("/collect", data).then(res => {
        if (res.code === '200') {
          this.state = false;
          this.$message.success("收藏成功")
        } else {
          this.state = true;
          this.$message.error(res.msg)
        }
      })
    },
    likes(){
      if (this.user.username == null){
        this.$message.warning("请登录")
        return;
      }
      let data = {blogId:this.id,userId:this.user.id}
      this.request.post("/likes", data).then(res => {
        if (res.code === '200') {
          this.state1 = false;
          this.$message.success("点赞成功")
        } else {
          this.state1 = true;
          this.$message.error(res.msg)
        }
      })
    },

    load() {
      this.request.get("/blog/" + this.id).then(res => {
        this.blog = res.data
      })
      this.request.get("/blog/recommend").then(res => {
        this.recommend = res.data
      })

      this.request.get("/collect/repeat/" + this.id + '/' + this.user.id).then(res => {
        if (res.code === '200') {
          this.state = true;
        } else {
          this.state = false
        }
      })

      this.request.get("/likes/repeat/" + this.id + '/' + this.user.id).then(res => {
        if (res.code === '200') {
          this.state1 = true;
        } else {
          this.state1 = false
        }
      })
    },

    reply(pid, target) {
      if (this.user.username == null){
        this.$message.warning("请登录")
        return;
      }
      this.replyComment = {pid: pid, userId: this.user.id, foreignId: this.id}
      this.dialogFormVisible3 = true
    },
    load2() {
      this.request.get("/comment/tree", {
        params: {
          foreignId: this.id,
        }
      }).then(res => {
        this.comments = res.data
      })
    },
    saveReply() {
      if (this.user.username == null){
        this.$message.warning("请登录")
        return;
      }
      this.replyComment.blogId = this.id;
      this.request.post("/comment", this.replyComment).then(res => {
        if (res.code === '200') {
          this.$message.success("评论成功")
          this.replyComment = {}
          this.dialogFormVisible3 = false
          this.load2()
        } else {
          this.$message.error("保存失败")
        }
      })
    },
    submit2() {
      if (this.user.username == null){
        this.$message.warning("请登录")
        return;
      }
      this.comment.userId = this.user.id
      this.comment.blogId = this.id
      this.request.post("/comment", this.comment).then(res => {
        if (res.code === '200') {
          this.$message.success("评论成功")
          this.load2()
          this.comment = {}
        } else {
          this.$message.error("评论失败")
        }
      })
    },
  }
}
</script>

<style scoped>
.item1 {
  padding: 10px;
  color: #666;
}
.collect-like {
  display: inline-block; /* 或者 flex */
  margin-left: 250px; /* 根据需要调整 */
}
</style>

