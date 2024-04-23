<template>
  <div style="width:1000px;margin: 15px auto">
    <div style="">
      <el-carousel height="450px" :interval="10000">
        <el-carousel-item v-for="(item,index) in files" v-if="index < 5" :key="item.id">
          <el-image
              style="width: 100%; height: 100%"
              :src="item.url"
              :fit="fit"></el-image>
        </el-carousel-item>
      </el-carousel>
    </div>

    <div>
      <div style="margin: 10px 0">
        <el-button :class="{'active': active === ''}" @click="loadCategory('')">全部</el-button>
        <el-button v-for="item in types" :key="item.id" :class="{'active': active === item.name}" @click="loadCategory(item.name)">{{ item.name }}</el-button>
      </div>
      <div style="margin: 10px 0">
        <el-row>
          <el-col :span="18">
            <div v-for="item in tableData" :key="item.id" style="margin: 10px 0">
              <el-card>
                <div style="display: flex">
                  <div style="flex: 1">
                    <div style="font-size: 23px; font-weight: bold;cursor: pointer" @click="$router.push('/front/blogDetail?id=' + item.id)" class="name">{{ item.name }}</div>
                    <div style="margin: 10px 0; color: #666"></div>
                    <div style="margin-top: 57px; color: #666">
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
              <img src="../../assets/bg.jpg" alt="" style="width: 100%;height: 200px">
            </el-card>

            <el-card style="margin: 10px 0 0 10px">
              <div style="padding: 10px 0; border-bottom: 1px solid #ccc; color: orangered; font-size: 20px;font-weight: bold">
                <span style="text-align: center">热榜</span>
              </div>
              <div v-for="item in tops" :key="item.id" style="margin: 10px 0">
                <span @click="$router.push('/front/blogDetail?id=' + item.id)">
                  {{item.name}}
                </span>
              </div>
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
    this.load()
  },
  methods: {
    loadCategory(name) {
      this.active = name
      this.pageNum = 1
      this.load()
    },
    load() {
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

      this.request.get("/file/pageHome", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
        }
      }).then(res => {
        this.files = res.data.records
        this.total = res.data.total
      })
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

<style>
.fontCss {
  font-size: 13px;
  color: #494949;
}
</style>
