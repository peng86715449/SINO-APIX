<template>
  <div class="main">
    <div class="he_height">
      <div class="list_top">
        <div class="list_title">{{ serveAllMeaasge.appName }}
          <el-button type="text" @click="goDocx">详细文档</el-button>
        </div>
        <div class="list_search">
          <el-button
            type="primary"
            size="small"
            class="td-but"
            @click="unSubscribe"
            >退订</el-button
          >
        </div>
      </div>
      <div class="secondTitle">{{ serveAllMeaasge.appDescription }}</div>
      <div class="status">
        <div class="left-span">
          <span>发布者 : </span> <span> 博冀信息</span>
        </div>
        <div class="time">
          <div>
            <span>发布时间 : </span>
            <span>{{ serveAllMeaasge.appPublishDate }}</span>
          </div>
          <div>
            <span>订阅时间 : </span>
            <span>{{ serveAllMeaasge.subscribeDate }}</span>
          </div>
        </div>
      </div>
      <div class="tabsMessage">
        <el-tabs
          v-loading="loading"
          v-model="activeName"
          @tab-click="handleClick"
        >
          <el-tab-pane label="API详情" name="first">
            <api-detail
              :list="versionList"
              :defaultApiList="apiList"
              :gatewayDomain="serveAllMeaasge.gatewayDomain"
              @changeVersion="changeVersion"
            ></api-detail>
          </el-tab-pane>
          <el-tab-pane label="插件详情" name="second">
            <plug-in></plug-in>
          </el-tab-pane>
        </el-tabs>
      </div>
    </div>
  </div>
</template>

<script>
import "./../mainCss/index.scss";
import apiDetail from "./component/apiDetail.vue";
import plugIn from "./component/plug-in.vue";
import { subscribed, gounSubscribe } from "@/api/AboutServe.js";

export default {
  data () {
    return {
      activeName: "first",
      appCode: "",
      serveAllMeaasge: {},
      loading: false,
      versionList: [],
      apiList: [],
    };
  },
  created () {
    this.appCode = this.$route.params.appCode;
    this.getSubscribed();
  },
  components: {
    apiDetail,
    plugIn,
  },
  methods: {
    goDocx() {
      this.$router.push({
        name: 'openServedocxDetail',
        query: {
          name: this.appName,
          id: this.appCode
        },
        params: {
          type: 'serve'
        }
      })
    },
    // 切换tab
    handleClick () { },
    // 查询订阅详情
    getSubscribed () {
      this.loading = true;
      subscribed(this.appCode).then((res) => {
        if (res.code === 200) {
          this.loading = false;
          this.serveAllMeaasge = res.data;
          this.versionList = [];
          for (let index = 0; index < res.data.appVersion.length; index++) {
            this.versionList.push({
              label: res.data.appVersion[index].version,
              value: res.data.appVersion[index].id,
            });
          }
          this.apiList = res.data.appVersion[0].apiList;
        }
      });
    },
    // 选择服务版本
    changeVersion (e) {
      this.apiList = e;
    },
    // 退订
    unSubscribe () {
      // console.log(e)
      this.$confirm(
        "确认退订服务" + this.serveAllMeaasge.appName + "?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          gounSubscribe(this.appCode).then((res) => {
            if (res.code === 200) {
              this.$router.push({ path: "/serve/subscribe" });
            }
          });
        })
        .catch(() => { });
    },
  },
};
</script>

<style scoped lang='scss'>
.td-but {
  width: 124px;
}
.he-main {
  min-height: inherit;
}
.he_height {
  // height: 100%;
  min-height: inherit;
  overflow: hidden;
  position: relative;
}
.tabsMessage {
  margin-top: 20px;
  position: absolute;
  width: 100%;
  height: 100%;
  // min-height: inherit;
  overflow: hidden;
  ::v-deep {
    .el-tabs__header {
      margin: 0px;
    }
    .el-tabs {
      height: 100%;
      .el-tabs__content {
        height: 100%;
        .el-tab-pane {
          height: 100%;
        }
      }
    }
  }
}
</style>