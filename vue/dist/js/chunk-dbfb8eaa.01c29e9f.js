(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-dbfb8eaa"],{"173e":function(e,t,s){},"7e06":function(e,t,s){"use strict";s.r(t);var n=function(){var e=this,t=e._self._c;return t("div",{staticStyle:{padding:"10px","margin-bottom":"50px"}},[t("el-row",[t("el-col",{attrs:{span:6}},[t("el-card",{staticStyle:{width:"300px",height:"300px",color:"#333"}},[t("div",{staticStyle:{"padding-bottom":"10px","border-bottom":"1px solid #ccc"}},[e._v("在线用户"),t("span",{staticStyle:{"font-size":"12px"}},[e._v("（点击聊天气泡开始聊天）")])]),e._l(e.users,(function(s){return t("div",{key:s.username,staticStyle:{padding:"10px 0"}},[t("span",[e._v(e._s(s.username))]),t("i",{staticClass:"el-icon-chat-dot-round",staticStyle:{"margin-left":"10px","font-size":"16px",cursor:"pointer"},on:{click:function(t){e.chatUser=s.username}}}),s.username===e.chatUser?t("span",{staticStyle:{"font-size":"12px",color:"limegreen","margin-left":"5px"}},[e._v("chatting...")]):e._e()])}))],2)],1),t("el-col",{attrs:{span:18}},[t("div",{staticStyle:{width:"800px",margin:"0 auto","background-color":"white","border-radius":"5px","box-shadow":"0 0 10px #ccc"}},[t("div",{staticStyle:{"text-align":"center","line-height":"50px"}},[e._v(" 在线聊天（"+e._s(e.chatUser)+"） ")]),t("div",{staticStyle:{height:"350px",overflow:"auto","border-top":"1px solid #ccc"},domProps:{innerHTML:e._s(e.content)}}),t("div",{staticStyle:{height:"200px"}},[t("textarea",{directives:[{name:"model",rawName:"v-model",value:e.text,expression:"text"}],staticStyle:{height:"160px",width:"100%",padding:"20px",border:"none","border-top":"1px solid #ccc","border-bottom":"1px solid #ccc",outline:"none"},domProps:{value:e.text},on:{input:function(t){t.target.composing||(e.text=t.target.value)}}}),t("div",{staticStyle:{"text-align":"right","padding-right":"10px"}},[t("el-button",{attrs:{type:"primary",size:"mini"},on:{click:e.send}},[e._v("发送")])],1)])])])],1)],1)},i=[];s("14d9"),s("b775");let o;var l={name:"Im",data(){return{circleUrl:"https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",user:{},isCollapse:!1,users:[],chatUser:"",text:"",messages:[],content:""}},created(){this.init()},methods:{send(){if(this.chatUser)if(this.text)if("undefined"==typeof WebSocket)console.log("您的浏览器不支持WebSocket");else{console.log("您的浏览器支持WebSocket");let e={from:this.user.username,to:this.chatUser,text:this.text};o.send(JSON.stringify(e)),this.messages.push({user:this.user.username,text:this.text}),this.createContent(null,this.user.username,this.text),this.text=""}else this.$message({type:"warning",message:"请输入内容"});else this.$message({type:"warning",message:"请选择聊天对象"})},createContent(e,t,s){let n;t?n='<div class="el-row" style="padding: 5px 0">\n  <div class="el-col el-col-22" style="text-align: right; padding-right: 10px">\n    <div class="tip left">'+s+'</div>\n  </div>\n  <div class="el-col el-col-2">\n  <span class="el-avatar el-avatar--circle" style="height: 40px; width: 40px; line-height: 40px;">\n    <img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" style="object-fit: cover;">\n  </span>\n  </div>\n</div>':e&&(n='<div class="el-row" style="padding: 5px 0">\n  <div class="el-col el-col-2" style="text-align: right">\n  <span class="el-avatar el-avatar--circle" style="height: 40px; width: 40px; line-height: 40px;">\n    <img src="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png" style="object-fit: cover;">\n  </span>\n  </div>\n  <div class="el-col el-col-22" style="text-align: left; padding-left: 10px">\n    <div class="tip right">'+s+"</div>\n  </div>\n</div>"),console.log(n),this.content+=n},init(){this.user=localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{};let e=this.user.username,t=this;if("undefined"==typeof WebSocket)console.log("您的浏览器不支持WebSocket");else{console.log("您的浏览器支持WebSocket");let s="ws://localhost:9090/imserver/"+e;null!=o&&(o.close(),o=null),o=new WebSocket(s),o.onopen=function(){console.log("websocket已打开")},o.onmessage=function(s){console.log("收到数据===="+s.data);let n=JSON.parse(s.data);n.users?t.users=n.users.filter(t=>t.username!==e):n.from===t.chatUser&&(t.messages.push(n),t.createContent(n.from,null,n.text))},o.onclose=function(){console.log("websocket已关闭")},o.onerror=function(){console.log("websocket发生了错误")}}}}},c=l,a=(s("e3b6"),s("2877")),r=Object(a["a"])(c,n,i,!1,null,null,null);t["default"]=r.exports},e3b6:function(e,t,s){"use strict";s("173e")}}]);
//# sourceMappingURL=chunk-dbfb8eaa.01c29e9f.js.map