<template>
  <div style="width:1000px;margin: 0px auto">
<!--    轮播图   -->
    <el-col :span="24" style="margin: 0 0 10px 0">
      <el-carousel height="450px" :interval="10000">
        <el-carousel-item v-for="(item,index) in files" v-if="index < 5" :key="item.id">
          <el-image
              style="width: 100%; height: 100%"
              :src="item.url"
              :fit="fit"></el-image>
        </el-carousel-item>
      </el-carousel>
    </el-col>

    <div style="margin: 10px 0">
<!--      分类    -->
      <div>
        <el-button class="fenlei" :class="{'active': active === ''}" @click="loadCategory('')">全部</el-button>
        <el-button class="fenlei" v-for="item in types" :key="item.id" :class="{'active': active === item.name}" @click="loadCategory(item.name)">{{ item.name }}</el-button>
      </div>
<!--  文章  -->
      <div style="">
        <el-row>
          <el-col :span="18">
            <div v-for="item in tableData" :key="item.id" style="margin: 10px 0">
              <el-card  shadow="hover">
                <div style="display: flex">
                  <div style="flex: 1">
                    <div style="font-size: 23px; font-weight: bold;cursor: pointer" @click="$router.push('/front/blogDetail?id=' + item.id)" class="name">{{ item.name }}</div>
                    <div style="color: #666; font-size: 20px; margin-top: 30px">{{ item.descr }}</div>

                    <div style="margin: 10px 0; color: #666"></div>
                    <div style="margin-top: 17px; color: #666">
                      <span><i class="el-icon-user"></i> {{ item.user }}</span>
                      <span style="margin-left: 10px"><i class="el-icon-time"></i> {{ item.time }}</span>
                      <span style="margin-left: 10px"><i class="el-icon-s-promotion"></i> <el-tag type="success" style="font-size: 15px">{{ item.blogType }}</el-tag></span>
                      <span style="margin-left: 10px"><i class="el-icon-view"></i> 浏览量&nbsp;{{ item.pageviews }}</span>
                    </div>
                  </div>
                  <div style="width: 200px">
                    <img :src="item.img" alt="" style="width: 200px; height: 110px">
                  </div>
                </div>
              </el-card>
            </div>
          </el-col>

          <el-col :span="6">
            <el-card style="margin: 10px 0 0 10px">
              <div class="card" style="margin-bottom: 4px">
                <a href="/front/person"><div style="color: #666; text-shadow: 2px 2px 4px #48ff00;">&nbsp;&nbsp;让我们一起发现、记录和分享旅途中的美好瞬间吧。</div></a>
                <!--          <div style="font-size: 20px; font-weight: bold; margin-bottom: 10px; text-align: center">欢迎您！</div>-->
                <!--          <img src="../../assets/imgs/welcome.jpg" alt="欢迎图片" style="display:block; margin: 0 auto;/* 使容器居中 */ margin-top:10px; margin-bottom: 10px;">-->
                <img src="../../assets/welcome.gif" alt="欢迎动图" style="display:block; margin: 0 auto;/* 使容器居中 */ width: 200px; height: 200px; margin-bottom: 10px;">
              </div>
            </el-card>
<!--  文章推荐  -->
            <el-card style="margin: 10px 0 0 10px">
              <div style="padding: 10px 0; border-bottom: 1px solid #ccc; color: orangered; font-size: 20px;font-weight: bold">
                <span style="text-align: center">文章推荐</span>
              </div>
<!--              根据文章浏览量排序-->
              <div v-for="item in tops" :key="item.id" style="margin: 10px 0">
                <span @click="$router.push('/front/blogDetail?id=' + item.id)">
                  {{item.name}}
                </span>
              </div>
            </el-card>
            <el-card style="margin: 10px 0 0 10px">
              <img src="../../assets/maliao1.gif" alt="" style="margin-left:20px; width: 130px; height: 130px; border-radius: 5px; margin-bottom: 8px">
              <div class="card-text">联系我们</div>
              <div class="card-text">工作时间：8:30-22:00</div>
              <div class="card-text">电话：400-600-1109</div>
              <div class="card-text">邮箱：blog@qq.com</div>
            </el-card>
          </el-col>

        </el-row>

      </div>
      <div style="padding: 10px 0" v-if="tableData.length">
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[2, 5, 10, 20]"
            :page-size="pageSize"
            layout="total, prev, pager, next"
            :total="total">
        </el-pagination>
      </div>
    </div>

  </div>


</template>

<script>
export default {
  name: "FrontHome",
  data() {
    return {
      pageNum_all: 1,
      pageSize_all: 5,
      total_all: 0,
      files: [],
      pageNum: 1,
      pageSize: 5,
      total: 0,
      tableData: [],
      name: this.$route.query.name,
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      type: '',
      types: [],
      name2: '',
      active: '',
      tops: []
    }
  },
  created() {
    this.request.get("/file/pageHome", {
      params: {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
      }
    }).then(res => {
      this.files = res.data.records
      this.total_all = res.data.total
    })

    this.load()
  },
  methods: {
    loadCategory(name) {
      this.active = name
      this.pageNum = 1
      this.load()
    },
    load() {
      // 标签分类
      this.request.get("/blog/page/type", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          type: this.active
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
      this.request.get("/blogType").then(res => {
        this.types = res.data
      })
      this.request.get("/blog/top").then(res => {
        this.tops = res.data
      })

      //

    },
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

  }
}
</script>

<style scoped>
.fontCss {
  font-size: 13px;
  color: #494949;
}
.card {
  padding: 20px;
  background-color: #b3d8ff;
  border-radius: 5px;
  box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.1);
}
/*.container {*/
/*  margin-left: 0px;*/
/*  width: 100% !important;*/
/*  !*background: linear-gradient(to bottom, #003366, #f8f8ff);*!*/
/*  background: linear-gradient(to bottom, #d2dff1, #f8f8ff);*/
/*  min-height: 100vh;*/
/*}*/
.fenlei{
  width: 100px;
  height: 50px;
  background-image: url("../../assets/resortq.jpg");
  font-size: 15px;
  color: white;
}
.fenlei:hover{
  background-image: url("../../assets/resorth.jpg");
  color: black;
}
.fenlei:focus{
  color: black;
  background-image: url("../../assets/resorth.jpg");
}

</style>
