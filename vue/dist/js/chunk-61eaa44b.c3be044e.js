(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-61eaa44b"],{3109:function(t,i,e){"use strict";e("4a2b")},"36a2":function(t,i,e){"use strict";e.r(i);e("14d9");var s=function(){var t=this,i=t._self._c;return i("div",{staticStyle:{padding:"10px"}},[i("el-row",{attrs:{gutter:10}},[i("el-col",{attrs:{span:12}},[i("el-card",[i("div",[i("img",{staticStyle:{width:"100%",height:"450px"},attrs:{src:t.music.img,"preview-src-list":[t.music.img]}})])])],1),i("el-col",{attrs:{span:12}},[i("el-card",{staticStyle:{height:"495px"}},[i("div",{staticClass:"item1",staticStyle:{margin:"30px 0 0 30px"}},[i("h1",{staticStyle:{color:"#E6A23C"}},[t._v(t._s(t.music.name))])]),i("div",{staticClass:"item1",staticStyle:{"font-size":"20px",margin:"20px 0 0 30px"}},[i("span",{staticStyle:{color:"#666666"}},[t._v("歌曲类别")]),t._v("  :  "),i("span",{staticStyle:{color:"#222"}},[t._v(t._s(t.music.typeName))])]),i("div",{staticClass:"item1",staticStyle:{"font-size":"20px",margin:"10px 0 0 30px"},on:{click:function(i){return t.$router.push("/front/musicSingerList?id="+t.music.singerId)}}},[i("span",{staticStyle:{color:"#666666"}},[t._v("歌手")]),t._v("  :  "),i("span",{staticStyle:{color:"orangered"}},[t._v(t._s(t.music.singerName))])])])],1)],1),i("el-card",{staticStyle:{"margin-top":"15px"}},[i("div",{staticStyle:{padding:"10px 0","border-bottom":"1px solid #ccc",color:"#1E90FF","font-size":"20px","font-weight":"bold"}},[t._v(" 歌曲介绍 ")]),i("div",{staticStyle:{"margin-top":"15px"},domProps:{innerHTML:t._s(t.music.content)}})]),i("el-card",{staticStyle:{"margin-top":"15px"}},[i("div",{staticStyle:{padding:"10px 0",color:"#1E90FF","font-size":"20px","font-weight":"bold"}},[t._v(" 歌曲播放 ")]),i("aplayer",{attrs:{music:t.audio,showlrc:!0}})],1),i("el-card",{staticStyle:{"margin-top":"15px"}},[i("div",{staticStyle:{padding:"10px 0","border-bottom":"1px solid #ccc",color:"#1E90FF","font-size":"20px","font-weight":"bold"}},[t._v(" mv ")]),i("div",{staticStyle:{"margin-top":"15px"}},[i("video",{ref:"videoPlayer",attrs:{src:t.music.file,controls:"",width:"100%",height:"460"}})])])],1)},a=[],c=e("0f7d"),r=e.n(c),o={components:{aplayer:r.a},name:"Detail",data(){return{id:this.$route.query.id,music:{},user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},audio:{title:"dome1",artist:"豆腐厂厂长",url:"http://localhost:9090/file/21b8b1b5cff44a8ea4bb44ca1a1ab96d.mp3",pic:"",lrc:"[00:00.00] (,,•́ . •̀,,) 刚刚开始学钢琴弹的hhhh",theme:""},state:!0}},created(){this.load()},methods:{load(){this.request.get("/music/"+this.id).then(t=>{this.music=t.data,this.audio={title:this.music.name,artist:this.music.singerName,url:this.music.url,pic:this.music.img,lrc:"[00:00.00] (,,•́ . •̀,,) 刚刚开始学钢琴弹的hhhh",theme:""}})}}},l=o,n=(e("3109"),e("2877")),d=Object(n["a"])(l,s,a,!1,null,"7a1f0ad8",null);i["default"]=d.exports},"4a2b":function(t,i,e){}}]);
//# sourceMappingURL=chunk-61eaa44b.c3be044e.js.map