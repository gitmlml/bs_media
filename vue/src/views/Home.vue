<template>
  <div>
    <el-row >
      <el-col :span="24">
        <el-card>
          <b>您好！{{ user.nickname }}</b>，欢迎使用本系统
        </el-card>
      </el-col>
    </el-row>

    <el-row style="margin: 10px 0">
      <el-col :span="8">
        <el-card style="color: #409EFF">
          <div><i class="el-icon-user-solid" /> 用户总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            {{map.users}}
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card style="color: #F56C6C">
          <div><i class="el-icon-money" /> 文章分类总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            {{map.comments}}
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card style="color: #67C23A">
          <div><i class="el-icon-bank-card" /> 文章总数</div>
          <div style="padding: 10px 0; text-align: center; font-weight: bold">
            {{map.blogs}}
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row style="margin-top: 10px">
      <el-col :span="24">
        <el-card>
          <div id="pie" style="width: 100%; height: 400px;text-align: center;"></div>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="10" style="margin-top: 10px">
      <el-col :span="24">
        <el-card>
          <div id="main" style="height: 450px"></div>
        </el-card>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import * as echarts from 'echarts'

export default {
  name: "Home",
  data() {
    return {
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      map: {},
      activeNames: ['0'],
      tableData: [],
      total: 0,
      pageNum: 1,
      pageSize: 6,
      name: "",
      noticesData: []
    }
  },
  created() {
    this.request.get("/echarts/counts").then(res => {
      this.map = res.data
    })

    this.request.get("/notice/page", {
      params: {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        name: this.name,
      }
    }).then(res => {
      this.noticesData = res.data.records
      this.total = res.data.total
    })
  },
  mounted() {  // 页面元素渲染之后再触发
    const option = {
      title: {
        text: '各用户文章数统计',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      xAxis: {
        type: 'category',
        data: [],
        axisLabel:{
          interval:0,
          rotate:30
        }
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          data: [],
          type: 'bar'
        },
      ]
    };

    // 饼图

    const pieOption = {
      title: {
        text: '各文章浏览数统计',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
      },
      series: [
        {
          type: 'pie',
          radius: '55%',
          center: ['50%', '50%'],
          data: [],  // 填空
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        },
      ],

    };

    var chartDom = document.getElementById('main');
    var myChart = echarts.init(chartDom);

    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);

    this.request.get("/echarts/user").then(res => {
      // 填空
      option.xAxis.data = res.data.x
      option.series[0].data = res.data.y
      // 数据准备完毕之后再set
      myChart.setOption(option);
    })

    this.request.get("/echarts/likes").then(res => {
      pieOption.series[0].data = res.data
      pieChart.setOption(pieOption)

    })
  }
}
</script>

<style scoped>
.manager-container {
  /*background: linear-gradient(to bottom, #003366, #f8f8ff);*/
  background: linear-gradient(to bottom, #0d2a48, #f8f8ff);
  min-height: 100vh;
}

</style>
