(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-5a8240d6"],{"0075":function(e,t,l){"use strict";l("fd01")},f836:function(e,t,l){"use strict";l.r(t);var a=function(){var e=this,t=e._self._c;return t("div",[t("div",{staticStyle:{margin:"10px 0"}},[t("el-input",{staticStyle:{width:"200px"},attrs:{placeholder:"请输入名称","suffix-icon":"el-icon-search"},model:{value:e.name,callback:function(t){e.name=t},expression:"name"}}),t("el-button",{staticClass:"ml-5",attrs:{type:"primary"},on:{click:e.load}},[e._v("搜索")]),t("el-button",{attrs:{type:"warning"},on:{click:e.reset}},[e._v("重置")])],1),t("div",{staticStyle:{margin:"10px 0"}},[t("el-popconfirm",{staticClass:"ml-5",attrs:{"confirm-button-text":"确定","cancel-button-text":"我再想想",icon:"el-icon-info","icon-color":"red",title:"您确定批量删除这些数据吗？"},on:{confirm:e.delBatch}},[t("el-button",{attrs:{slot:"reference",type:"danger"},slot:"reference"},[e._v("批量删除 "),t("i",{staticClass:"el-icon-remove-outline"})])],1)],1),t("el-table",{attrs:{data:e.tableData,border:"",stripe:"","header-cell-class-name":"headerBg"},on:{"selection-change":e.handleSelectionChange}},[t("el-table-column",{attrs:{type:"selection",width:"55"}}),t("el-table-column",{attrs:{prop:"id",label:"ID",width:"80",sortable:""}}),t("el-table-column",{attrs:{prop:"name",label:"操作"}}),t("el-table-column",{attrs:{prop:"params",label:"参数","show-overflow-tooltip":!0}}),t("el-table-column",{attrs:{prop:"url",label:"url"}}),t("el-table-column",{attrs:{prop:"duration",label:"执行时间"}}),t("el-table-column",{attrs:{prop:"ip",label:"IP"}}),t("el-table-column",{attrs:{prop:"username",label:"操作人"}}),t("el-table-column",{attrs:{prop:"createTime",label:"创建时间"}}),t("el-table-column",{attrs:{label:"操作",width:"180",align:"center"},scopedSlots:e._u([{key:"default",fn:function(l){return[t("el-popconfirm",{staticClass:"ml-5",attrs:{"confirm-button-text":"确定","cancel-button-text":"我再想想",icon:"el-icon-info","icon-color":"red",title:"您确定删除吗？"},on:{confirm:function(t){return e.del(l.row.id)}}},[t("el-button",{attrs:{slot:"reference",type:"danger"},slot:"reference"},[e._v("删除 "),t("i",{staticClass:"el-icon-remove-outline"})])],1)]}}])})],1),t("div",{staticStyle:{padding:"10px 0"}},[t("el-pagination",{attrs:{"current-page":e.pageNum,"page-sizes":[2,5,10,20],"page-size":e.pageSize,layout:"total, sizes, prev, pager, next, jumper",total:e.total},on:{"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}})],1),t("el-dialog",{attrs:{title:"信息",visible:e.dialogFormVisible,width:"40%","close-on-click-modal":!1},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[t("el-form",{ref:"ruleForm",staticStyle:{width:"85%"},attrs:{"label-width":"140px",size:"small",model:e.form,rules:e.rules}},[t("el-form-item",{attrs:{prop:"name",label:"操作"}},[t("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.name,callback:function(t){e.$set(e.form,"name",t)},expression:"form.name"}})],1),t("el-form-item",{attrs:{prop:"params",label:"入参"}},[t("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.params,callback:function(t){e.$set(e.form,"params",t)},expression:"form.params"}})],1),t("el-form-item",{attrs:{prop:"output",label:"出参"}},[t("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.output,callback:function(t){e.$set(e.form,"output",t)},expression:"form.output"}})],1),t("el-form-item",{attrs:{prop:"url",label:"url"}},[t("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.url,callback:function(t){e.$set(e.form,"url",t)},expression:"form.url"}})],1),t("el-form-item",{attrs:{prop:"duration",label:"执行时间"}},[t("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.duration,callback:function(t){e.$set(e.form,"duration",t)},expression:"form.duration"}})],1),t("el-form-item",{attrs:{prop:"ip",label:"IP"}},[t("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.ip,callback:function(t){e.$set(e.form,"ip",t)},expression:"form.ip"}})],1),t("el-form-item",{attrs:{prop:"address",label:"地址"}},[t("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.address,callback:function(t){e.$set(e.form,"address",t)},expression:"form.address"}})],1),t("el-form-item",{attrs:{prop:"username",label:"操作人"}},[t("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.username,callback:function(t){e.$set(e.form,"username",t)},expression:"form.username"}})],1),t("el-form-item",{attrs:{prop:"createTime",label:"创建时间"}},[t("el-date-picker",{attrs:{type:"datetime","value-format":"yyyy-MM-dd HH:mm:ss",placeholder:"选择日期时间"},model:{value:e.form.createTime,callback:function(t){e.$set(e.form,"createTime",t)},expression:"form.createTime"}})],1),t("el-form-item",{attrs:{prop:"deleted",label:"软删除"}},[t("el-input",{attrs:{autocomplete:"off"},model:{value:e.form.deleted,callback:function(t){e.$set(e.form,"deleted",t)},expression:"form.deleted"}})],1)],1),t("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[t("el-button",{on:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取 消")]),t("el-button",{attrs:{type:"primary"},on:{click:e.save}},[e._v("确 定")])],1)],1)],1)},o=[],r={name:"Log",data(){return{tableData:[],total:0,pageNum:1,pageSize:10,name:"",form:{},dialogFormVisible:!1,multipleSelection:[],user:localStorage.getItem("user")?JSON.parse(localStorage.getItem("user")):{},rules:{name:[{required:!0,message:"请输入名称",trigger:"blur"}]}}},created(){this.load()},methods:{load(){this.request.get("/log/page",{params:{pageNum:this.pageNum,pageSize:this.pageSize,name:this.name}}).then(e=>{this.tableData=e.data.records,this.total=e.data.total})},save(){this.$refs["ruleForm"].validate(e=>{e&&this.request.post("/log",this.form).then(e=>{"200"===e.code?(this.$message.success("保存成功"),this.dialogFormVisible=!1,this.load()):this.$message.error(e.msg)})})},handleAdd(){this.dialogFormVisible=!0,this.form={},this.$nextTick(()=>{this.$refs.img&&this.$refs.img.clearFiles(),this.$refs.file&&this.$refs.file.clearFiles()})},handleEdit(e){this.form=JSON.parse(JSON.stringify(e)),this.dialogFormVisible=!0,this.$nextTick(()=>{this.$refs.img&&this.$refs.img.clearFiles(),this.$refs.file&&this.$refs.file.clearFiles()})},del(e){this.request.delete("/log/"+e).then(e=>{"200"===e.code?(this.$message.success("删除成功"),this.load()):this.$message.error("删除失败")})},handleSelectionChange(e){console.log(e),this.multipleSelection=e},delBatch(){if(!this.multipleSelection.length)return void this.$message.error("请选择需要删除的数据");let e=this.multipleSelection.map(e=>e.id);this.request.post("/log/del/batch",e).then(e=>{"200"===e.code?(this.$message.success("批量删除成功"),this.load()):this.$message.error("批量删除失败")})},reset(){this.name="",this.load()},handleSizeChange(e){console.log(e),this.pageSize=e,this.load()},handleCurrentChange(e){console.log(e),this.pageNum=e,this.load()},handleFileUploadSuccess(e){this.form.file=e},handleImgUploadSuccess(e){this.form.img=e},download(e){window.open(e)},exp(){window.open("http://localhost:9090/log/export")},handleExcelImportSuccess(){this.$message.success("导入成功"),this.load()}}},s=r,i=(l("0075"),l("2877")),n=Object(i["a"])(s,a,o,!1,null,null,null);t["default"]=n.exports},fd01:function(e,t,l){}}]);
//# sourceMappingURL=chunk-5a8240d6.76a07880.js.map