(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d21e794"],{d65a:function(e,l,a){"use strict";a.r(l);var t=function(){var e=this,l=e._self._c;return l("div",{staticStyle:{"margin-bottom":"100px"}},[l("el-select",{attrs:{multiple:"",placeholder:"请选择"},model:{value:e.value1,callback:function(l){e.value1=l},expression:"value1"}},e._l(e.options,(function(e){return l("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1),l("el-button",{attrs:{type:"primary"},on:{click:e.submit}},[e._v("主要按钮")])],1)},s=[],u={name:"FrontHome",data(){return{options:[{value:1,label:"黄金糕"},{value:2,label:"xxx"},{value:3,label:"yyy"},{value:4,label:"666"},{value:5,label:"黄金糕5"}],value1:[1,2,4],value2:[]}},created(){},methods:{submit(){console.log(this.value1),this.request.post("/blog/label",this.value1).then(e=>{"200"===e.code?this.$message.success("保存成功"):this.$message.error(e.msg)})}}},o=u,n=a("2877"),i=Object(n["a"])(o,t,s,!1,null,null,null);l["default"]=i.exports}}]);
//# sourceMappingURL=chunk-2d21e794.6ee93b0e.js.map