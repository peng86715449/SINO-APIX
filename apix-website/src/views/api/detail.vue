<template>
  <div class="main">
    <div class="list_top">
      <div class="list_title">{{apiInfo.name}}</div>
      <div class="list_search">
        <div class="but-left">
          <el-dropdown @command="handleCommand">
            <el-button type="primary" size="small" style="width:85px">
              操作<i class="el-icon-arrow-down el-icon--conten-color"></i>
            </el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="editAPI">编辑API</el-dropdown-item>
              <el-dropdown-item command="delAPI">删除API</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
        <el-button type="primary" size="small" style="width:85px" @click="docsEdit()">编辑文档</el-button>
      </div>
    </div>
    <div class="secondTitle">
      {{apiInfo.description || '-'}}
    </div>
    <div class="status">
      <div class="left-span">
        <span>被调用次数 : </span>
        <div class="tag info">{{numbers.totalNum}}次</div>
      </div>
      <div class="left-span">
        <span>调用失败次数 : </span>
        <div class="tag info">{{numbers.failNum}}次</div>
      </div>
      <div class="time">
        <div>
          <span>创建时间 : </span>
          <span>{{apiInfo.creationDate}}</span>
        </div>
        <div>
          <span>更新时间 : </span>
          <span>{{apiInfo.lastUpdateDate}}</span>
        </div>
      </div>
    </div>
    <div class="apiMessage">
      <div class="api-info">
        <span class="label-color">域名 : </span>
        <span class="conten-color">{{apiInfo.domain}}</span>
          <i class="el-icon-copy-document icon-color"
            v-clipboard:copy="apiInfo.domain"
            v-clipboard:success="onCopy"
            v-clipboard:error="onError"/>
      </div>
      <div class="api-info">
        <span class="label-color">路径 : </span>
        <span class="conten-color">{{apiInfo.url}}</span>
        <i class="el-icon-copy-document icon-color"
            v-clipboard:copy="apiInfo.url"
            v-clipboard:success="onCopy"
            v-clipboard:error="onError"/>
      </div>
      <div class="api-info">
        <span class="label-color">请求方式 : </span>
        <span class="conten-color">{{apiInfo.requestMethod}}</span>
      </div>
      <!-- <div class="api-info">
        <span class="label-color">返回类型 : </span>
        <span class="conten-color">{{apiInfo.requestMethod}}</span>
      </div> -->
      <div class="api-info">
        <span class="label-color">请求参数 : </span>
        <div class="table_box table_top">
          <el-table
            :data="requestParamsTable"
            empty-text="暂无数据"
            :row-style="{ height: '50px' }"
            highlight-current-row
            :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
          >
            <el-table-column prop="parame" label="名称" />
            <el-table-column prop="type" label="类型" />
            <el-table-column prop="isHaveto" label="是否必选" />
            <el-table-column prop="describe" label="描述" />
            <el-table-column prop="default" label="默认值" />
          </el-table>
        </div>
      </div>
      <div class="api-info">
        <span class="label-color">请求示例 : </span>
        <div class="table_top">
          <prism-editor
            readonly
            v-model="requestExample"
            class="my-editor height-300"
            :highlight="highlighter"
            :line-numbers="lineNumbers"
          />
        </div>
      </div>
      <div class="api-info">
        <span class="label-color">返回参数 : </span>
        <div class="table_box table_top">
          <el-table
            :data="responseParamsTable"
            empty-text="暂无数据"
            :row-style="{ height: '50px' }"
            highlight-current-row
            :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
          >
            <el-table-column prop="parame" label="名称" />
            <el-table-column prop="type" label="类型" />
            <el-table-column prop="isHaveto" label="是否必选" />
            <el-table-column prop="describe" label="描述" />
            <el-table-column prop="default" label="默认值" />
          </el-table>
        </div>
      </div>
      <div class="api-info">
        <span class="label-color">返回示例 : </span>
        <div class="table_top">
          <prism-editor
            readonly
            v-model="responseExample"
            class="my-editor height-300"
            :highlight="highlighter"
            :line-numbers="lineNumbers"
          />
        </div>
      </div>
      <div class="api-info">
        <span class="label-color">状态码 : </span>
        <div class="table_box table_top">
          <el-table
            :data="statusTable"
            empty-text="暂无数据"
            :row-style="{ height: '50px' }"
            highlight-current-row
            :header-cell-style="{ 'font-weight': 400, color: '#494E6A' }"
          >
            <el-table-column prop="code" label="状态码" width="200"/>
            <el-table-column prop="desc" label="描述" />
          </el-table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { detail, detailNum, deleteApi } from "@/api/AboutApi";
import { PrismEditor } from 'vue-prism-editor'
import 'vue-prism-editor/dist/prismeditor.min.css' // import the styles somewhere
import { highlight, languages } from 'prismjs/components/prism-core'
import 'prismjs/components/prism-clike'
import 'prismjs/components/prism-javascript'
import 'prismjs/themes/prism-tomorrow.css' // import syntax highlighting styles
import "./../mainCss/index.scss";
export default {
  components: {
    PrismEditor
  },
  data () {
    return {
      apiId: '',
      apiInfo: {},
      numbers: {},
      lineNumbers: true,
      requestParamsTable: [],
      responseParamsTable: [],
      requestExample: '',
      responseExample: '',
      statusTable: [
        { code: '200', desc: '操作成功' }
      ]
    };
  },
  created () {
    this.apiId = this.$route.params.id
    this.getDetail()
    this.getDetailNum()
  },
  methods: {
    highlighter (code) {
      return highlight(code, languages.js)
    },
    // 编辑API文档
    docsEdit () {
      this.$router.push('/docsEdit/' + 'api?id=' + this.apiId + '&name=' + this.apiInfo.name)
    },
    // 获取api详情
    getDetail () {
      detail(this.apiId).then((res) => {
        if (res.code === 200) {
          this.apiInfo = res.data
          this.requestParamsTable = JSON.parse(res.data.requestParams)
          this.responseParamsTable = JSON.parse(res.data.responseParams)
          this.requestExample = JSON.parse(res.data.requestExample)
          this.responseExample = JSON.parse(res.data.responseExample)
        }
      });
    },
    // 获取API数据统计
    getDetailNum () {
      detailNum(this.apiId).then((res) => {
        if (res.code === 200) {
          this.numbers = res.data
        }
      });
    },
    handleCommand (command) {
      if (command === 'delAPI') {
        this.delAPI()
      } else {
        this.$router.push({ path: '/api/edit/' + this.apiId })
      }
    },
    // 删除API数据信息
    delAPI () {
      this.$confirm('确认删除API：' + this.apiInfo.name + ', 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteApi(this.apiId).then(res => {
          if (res.code === 200) {
            this.$message.success('删除成功！')
            this.$router.push({ path: '/api/list/' })
          }
        })
      })
    },
    onCopy () {
      this.$message('复制成功')
    },
    onError () {
      this.$message('复制失败');
    },
  }
};
</script>

<style lang='scss' scoped>
.my-editor {
  background: #f4f6ff;
  color: #373753;
  border: 0px;
  font-family: Fira code, Fira Mono, Consolas, Menlo, Courier, monospace;
  font-size: 14px;
  line-height: 1.5;
  padding: 5px;
}
/* optional */
.prism-editor__textarea:focus {
  outline: none;
}
/* not required: */
.height-300 {
  height: 150px;
}
</style>