<template>
  <div>
<!--    用户信息-->
    <div>
      <el-container>
        <el-header style="height: 130px; display: flex; align-items: center;background-color: rgb(244,208,212) ">
          <img :src="user.avatarUrl" alt="" style="height: 100px; width:100px;margin-right: 10px;border-radius: 50%"> <!-- 添加了margin-right来调整间距 -->
          <div style="flex: 1;margin-left: 15px">
            <div style="color: #666; font-size: 18px; margin-top: 12px">{{ user.nickname }}</div>
            <div style="color: #666; font-size: 18px; margin-top: 33px" class="line2">{{ user.info }}</div>
          </div>
          <el-button @click="goToPersonPage" size="medium" >编辑资料</el-button>

        </el-header>
      </el-container>
    </div>

    <div>
      <!-- 导航栏 -->
      <el-menu default-active="1" mode="horizontal" @select="handleSelect">
        <el-menu-item index="1">我的发布</el-menu-item>
        <el-menu-item index="2">我的收藏</el-menu-item>
        <el-menu-item index="3">我的点赞</el-menu-item>
      </el-menu>

      <!-- 我的收藏 -->
      <div v-if="active1 === '2'">
        <div style="margin-top: 15px">
          <el-card>
            <el-table :data="tableData">
              <el-table-column prop="blog.name" label="文章">
                <template slot-scope="scope">
                  <span @click="$router.push('/front/blogDetail?id=' + scope.row.blog.id)">{{scope.row.blog.name}}</span>
                </template>
              </el-table-column>
              <el-table-column prop="blog.blogType" label="文章类别"></el-table-column>
              <el-table-column label="封面"><template slot-scope="scope"><el-image style="width: 100px; height: 100px" :src="scope.row.blog.img" :preview-src-list="[scope.row.blog.img]"></el-image></template></el-table-column>
              <el-table-column prop="blog.user" label="发布者"></el-table-column>
              <el-table-column prop="time" label="收藏时间"></el-table-column>
              <el-table-column label="操作"  width="280" align="center">
                <template slot-scope="scope">
                  <el-popconfirm
                      id="pagination_blog"
                      class="ml-5"
                      confirm-button-text='确定'
                      cancel-button-text='我再想想'
                      icon="el-icon-info"
                      icon-color="red"
                      title="您确定取消收藏吗？"
                      @confirm="del1(scope.row.id)"
                  >
                    <el-button type="danger" slot="reference">取消收藏 <i class="el-icon-remove-outline"></i></el-button>
                  </el-popconfirm>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </div>
      </div>

      <!-- 我发布的文章 -->
      <div v-if="active1 === '1'">
        <div>
          <el-card>
            <div style="margin: 10px 0">
              <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            </div>

            <el-table :data="tableData_blog">
<!--              <el-table-column prop="name" label="标题"></el-table-column>-->
              <el-table-column prop="name" label="文章">
                <template slot-scope="scope">
                  <span @click="$router.push('/front/blogDetail?id=' + scope.row.id)">{{scope.row.name}}</span>
                </template>
              </el-table-column>
              <el-table-column prop="descr" label="简介"></el-table-column>
              <el-table-column prop="content" label="内容">
                <template v-slot="scope">
                  <el-button type="primary" @click="view(scope.row.content)">查看内容</el-button>
                </template>
              </el-table-column>
              <el-table-column prop="user" label="发布人"></el-table-column>
              <el-table-column prop="time" label="发布时间" width="150"></el-table-column>
              <el-table-column label="图片" width="140"><template slot-scope="scope"><el-image style="width: 100px; height: 100px" :src="scope.row.img" :preview-src-list="[scope.row.img]"></el-image></template></el-table-column>
              <el-table-column prop="blogType" label="文章类别"></el-table-column>
              <el-table-column prop="reviewer" label="审核人"></el-table-column>
              <el-table-column prop="state" label="审核状态"></el-table-column>
              <el-table-column prop="reason" label="审核描述"></el-table-column>
              <el-table-column label="操作"  width="280" align="center">
                <template slot-scope="scope">
                  <el-button type="success" @click="handleEdit(scope.row)" v-if="scope.row.state === '待审核'">编辑 <i class="el-icon-edit"></i></el-button>
                  <el-popconfirm
                      id="pagination_blog"
                      class="ml-5"
                      confirm-button-text='确定'
                      cancel-button-text='我再想想'
                      icon="el-icon-info"
                      icon-color="red"
                      title="您确定删除吗？"
                      @confirm="del(scope.row.id)"
                  >
                    <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
                  </el-popconfirm>
                </template>
              </el-table-column>
            </el-table>
          </el-card>

          <el-dialog title="信息" :visible.sync="dialogFormVisible" width="60%" :close-on-click-modal="false">
            <el-form label-width="140px" size="small" style="width: 85%;" :model="form" :rules="rules" ref="ruleForm">
              <el-form-item prop="blogType" label="文章类别">
                <template>
                  <el-select v-model="form.blogType" placeholder="请选择">
                    <el-option v-for="item in types" :key="item.name" :label="item.name" :value="item.name"></el-option>
                  </el-select>
                </template>
              </el-form-item>
              <el-form-item prop="name" label="标题">
                <el-input v-model="form.name" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item prop="descr" label="简介">
                <el-input v-model="form.descr" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="内容">
                <div id="richText"></div>
              </el-form-item>
              <el-form-item prop="img" label="封面">
                <el-upload action="http://localhost:9090/file/upload" ref="img" :on-success="handleImgUploadSuccess">
                  <el-button size="small" type="primary">点击上传</el-button>
                </el-upload>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible = false">取 消</el-button>
              <el-button type="primary" @click="save">确 定</el-button>
            </div>
          </el-dialog>

          <el-dialog title="内容" :visible.sync="dialogFormVisible1" width="60%" :close-on-click-modal="false">
            <el-card>
              <div v-html="content"></div>
            </el-card>
          </el-dialog>

          <el-dialog title="信息" :visible.sync="dialogFormVisible2" width="40%" :close-on-click-modal="false">
            <el-form label-width="140px" size="small" style="width: 85%;" :model="form" :rules="rules" ref="ruleForm">
              <el-form-item label="审核人姓名">
                <el-input v-model="form.reviewer" autocomplete="off"></el-input>
              </el-form-item>
              <el-form-item label="审核状态">
                <template>
                  <el-radio v-model="form.state" label="审核通过">审核通过</el-radio>
                  <el-radio v-model="form.state" label="审核不通过">审核不通过</el-radio>
                </template>
              </el-form-item>
              <el-form-item label="审核描述">
                <el-input type="textarea" v-model="form.reason" autocomplete="off" :rows="7"></el-input>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogFormVisible2 = false">取 消</el-button>
              <el-button type="primary" @click="save2">确 定</el-button>
            </div>
          </el-dialog>
        </div>
      </div>

      <!-- 我的点赞 -->
      <div v-if="active1 === '3'">
        <div style="margin-top: 15px">
          <el-card>
            <el-table :data="tableData_likes">
              <el-table-column prop="blog.name" label="文章">
                <template slot-scope="scope">
                  <span @click="$router.push('/front/blogDetail?id=' + scope.row.blog.id)">{{scope.row.blog.name}}</span>
                </template>
              </el-table-column>
              <el-table-column prop="blog.blogType" label="文章类别"></el-table-column>
              <el-table-column label="封面"><template slot-scope="scope"><el-image style="width: 100px; height: 100px" :src="scope.row.blog.img" :preview-src-list="[scope.row.blog.img]"></el-image></template></el-table-column>
              <el-table-column prop="blog.user" label="发布者"></el-table-column>
              <el-table-column prop="time" label="点赞时间"></el-table-column>
              <el-table-column label="操作"  width="280" align="center">
                <template slot-scope="scope">
                  <el-popconfirm
                      id="pagination_blog"
                      class="ml-5"
                      confirm-button-text='确定'
                      cancel-button-text='我再想想'
                      icon="el-icon-info"
                      icon-color="red"
                      title="您确定取消点赞吗？"
                      @confirm="del2(scope.row.id)"
                  >
                    <el-button type="danger" slot="reference">取消点赞 <i class="el-icon-remove-outline"></i></el-button>
                  </el-popconfirm>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </div>
      </div>



    </div>
  </div>

</template>

<script>
import router from "../../router";
import E from "wangeditor"
let editor;

export default {
  name: "PersonCentre.vue",
  data() {
    return {
      active1: '1', // 默认激活选项为“我的收藏”

      //发布的文章
      tableData_blog: [],
      total_blog: 0,
      pageNum_blog: 1,
      pageSize_blog: 200,

      //点赞的文章
      tableData_likes: [],
      total_likes: 0,
      pageNum_likes: 1,
      pageSize_likes: 200,

      //报名的活动
      tableData_act: [],
      total_act: 0,
      pageNum_act: 1,
      pageSize_act: 200,

      //收藏的文章
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 200,

      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      name: "",
      form: {},
      dialogFormVisible: false,
      dialogFormVisible1: false,
      dialogFormVisible2: false,
      content: '',
      multipleSelection: [],
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur'}
        ],
      },
      types: [],

    }
  },
  created() {
    editor = ''
    if (this.user.username == null){
      this.$message.warning("请登录")
      return;
    }
    this.load()
  },

  methods: {
    handleSelect(key, index) {
      if (key === '1') { // 当点击“”时，更新 active1 为 '1'。
        this.active1 = '1';
      } else if (key === '2') { // 当点击“”时，更新 active1 为 '2'
        this.active1 = '2';
      } else if (key === '3') { // 当点击“”时，更新 active1 为 '3'
        this.active1 = '3';
      }else if (key === '4') { // 当点击“”时，更新 active1 为 '4'
        this.active1 = '4';
      }else { // 其他情况，你可以根据需要添加更多的逻辑。
        this.active1 = '0'; // 重置 active1 到默认值或任何其他值。
      }
    },

    cancel(o){
      o.state = '已取消'
      this.request.post("/activityApply", o).then(res => {
        if (res.code === '200') {
          this.$message.success("取消成功")
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },

    view(data){
      this.content = data
      this.dialogFormVisible1 = true;
    },

    load() {
      // 报名的活动
      this.request.get("/activityApply/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData_act = res.data.records
        this.total_act = res.data.total
      }),

      // 收藏的文章
      this.request.get("/collect/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      }),

       // 点赞的文章
       this.request.get("/likes/page", {
         params: {
           pageNum: this.pageNum,
           pageSize: this.pageSize,
         }
       }).then(res => {
         this.tableData_likes = res.data.records
         this.total_likes = res.data.total
       }),

      // 发布的文章
      this.request.get("/blog/page/front", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData_blog = res.data.records
        this.total_blog = res.data.total
      })
      this.request.get("/blogType").then(res => {
        this.types = res.data
      })
    },

    save_act() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          this.request.post("/activityApply", this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("保存成功")
              this.dialogFormVisible = false
              this.load()
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    save() {
      this.form.content = editor.txt.html()
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          this.request.post("/blog", this.form).then(res => {
            if (res.code === '200') {
              this.$message.success("保存成功")
              this.dialogFormVisible = false
              this.dialogFormVisible2 = false
              this.load()
            } else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
    save2() {
      this.request.post("/blog", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("审核成功")
          this.dialogFormVisible2 = false
          this.load()
        } else {
          this.$message.error(res.msg)
        }
      })
    },
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
      this.$nextTick(() => {
        if (!editor){
          editor  = new E("#richText")
          // 配置 server 接口地址
          editor.config.uploadImgServer = 'http://localhost:9090/file/uploadImg'
          editor.config.uploadFileName = 'file'
          editor.create()
        }
        editor.txt.html('')

        if(this.$refs.img) {
          this.$refs.img.clearFiles();
        }
        if(this.$refs.file) {
          this.$refs.file.clearFiles();
        }
      })
    },
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
      this.$nextTick(() => {
        if (!editor){
          editor = new E("#richText")
          // 配置 server 接口地址
          editor.config.uploadImgServer = 'http://localhost:9090/file/uploadImg'
          editor.config.uploadFileName = 'file'
          editor.create()
        }
        editor.txt.html(this.form.content)

        if(this.$refs.img) {
          this.$refs.img.clearFiles();
        }
        if(this.$refs.file) {
          this.$refs.file.clearFiles();
        }
      })
    },
    handleEdit2(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible2 = true
    },
    del(id) {
      this.request.delete("/blog/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    del1(id) {
      this.request.delete("/collect/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    del2(id) {
      this.request.delete("/likes/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    delBatch() {
      if (!this.multipleSelection.length) {
        this.$message.error("请选择需要删除的数据")
        return
      }
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/blog/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    reset() {
      this.name = ""
      this.load()
    },
// 收藏
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },

    // 点赞
    handleSizeChange_likes(pageSize) {
      console.log(pageSize)
      this.pageSize_likes = pageSize
      this.load()
    },
    handleCurrentChange_likes(pageNum) {
      console.log(pageNum)
      this.pageNum_likes = pageNum
      this.load()
    },

    // 活动
    handleSizeChange_act(pageSize) {
      console.log(pageSize)
      this.pageSize_act = pageSize
      this.load()
    },
    handleCurrentChange_act(pageNum) {
      console.log(pageNum)
      this.pageNum_act = pageNum
      this.load()
    },

    // 发布
    handleSizeChange_blog(pageSize) {
      console.log(pageSize)
      this.pageSize_blog = pageSize
      this.load()
    },
    handleCurrentChange_blog(pageNum) {
      console.log(pageNum)
      this.pageNum_blog = pageNum
      this.load()
    },

    handleFileUploadSuccess(res) {
      this.form.file = res
    },
    handleImgUploadSuccess(res) {
      this.form.img = res
    },
    download(url) {
      window.open(url)
    },
    exp() {
      window.open("http://localhost:9090/blog/export")
    },
    handleExcelImportSuccess() {
      this.$message.success("导入成功")
      this.load()
    },


    goToPersonPage() {
      router.push({ name: 'Person' }); // 触发路由跳转
    },

  }
}
</script>

<style scoped>
.active {
  /* 在这里添加你想要的样式 */
  color: red; /* 示例：将活动链接的文字颜色设置为红色 */
}
.line2 {
  word-break: break-all;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2; /* 超出几行省略 */
  overflow: hidden;
}
</style>



