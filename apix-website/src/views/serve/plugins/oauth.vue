<template>
  <div class="formBox">
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-position="top"
    >
      <el-form-item label="Provision Key" prop="ProvisionKey">
        <el-input
          v-model="ruleForm.ProvisionKey"
          placeholder=""
          class="inputWidth"
          clearable
        >
        </el-input>
      </el-form-item>
      <el-form-item label="Token Expiration" prop="TokenExpiration">
        <el-input-number
          v-model="ruleForm.TokenExpiration"
          @change="handleChange"
          :min="1"
        ></el-input-number>
      </el-form-item>
      <el-form-item prop="timeout">
        <el-checkbox disabled v-model="ruleForm.EnableClientCredentials"
          >客户端认证</el-checkbox
        >
      </el-form-item>
      <el-form-item
        label="Refresh Token Expiration"
        prop="RefreshTokenExpiration"
      >
        <el-input-number
          v-model="ruleForm.RefreshTokenExpiration"
          @change="handleChange"
          :min="1"
        ></el-input-number>
      </el-form-item>
      <div class="bottom_button_a">
        <el-button
          size="small"
          type="primary"
          @click="submitForm('ruleForm')"
          >{{ buttonFont }}</el-button
        >
        <el-button size="small" @click="resetForm('ruleForm')">取消</el-button>
      </div>
    </el-form>
  </div>
</template>

<script>
import { postPlugin, getPlugin, putPlugin } from "@/api/AboutServe.js";

export default {
  data() {
    return {
      id: "",
      appCode: "",
      appId: "",
      buttonFont: "添加",
      enabled: 0,
      ruleForm: {
        ProvisionKey: "",
        EnableClientCredentials: true,
        TokenExpiration: "7200",
        RefreshTokenExpiration: "1209600",
      },
      rules: {
        ProvisionKey: [
          { required: true, message: "请输入ProvisionKey", trigger: "blur" },
        ],
        TokenExpiration: [
          { required: true, message: "请输入TokenExpiration", trigger: "blur" },
        ],
        RefreshTokenExpiration: [
          {
            required: true,
            message: "请输入RefreshTokenExpiration",
            trigger: "blur",
          },
        ],
      },
    };
  },
  created() {
    this.appCode = this.$route.query.appcode;
    this.appId = this.$route.query.appid;
    if (this.$route.query.pluginParams) {
      this.id = this.$route.query.id;
      //查询当前插件详情
      getPlugin(this.id, this.appCode).then((res) => {
        if (res.code === 200) {
          this.enabled = res.data.enabled;
          const data = JSON.parse(res.data.pluginParams);
          this.ruleForm = data;
          // this.ruleForm = {
          //   header: data.HeaderNames,
          //   key: data.keyClaimName,
          //   maximum: data.TokenExpiration,
          // };
        }
      });
      this.buttonFont = "应用";
    } else {
      this.buttonFont = "添加";
    }
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.buttonFont === "添加") {
            const query = {
              pluginType: "oauth2",
              appCode: this.appCode,
              appId: this.appId,
              pluginParams: JSON.stringify(this.ruleForm),
            };
            postPlugin(query).then((res) => {
              if (res.code === 200) {
                this.$router.push({
                  path: "/serve/serveDetail/" + this.appCode,
                });
              }
            });
          } else {
            const query = {
              pluginType: "oauth2",
              appCode: this.appCode,
              appId: this.appId,
              id: this.id,
              enabled: this.enabled,
              pluginParams: JSON.stringify(this.ruleForm),
            };
            putPlugin(query).then((res) => {
              if (res.code === 200) {
                this.$router.push({
                  path: "/serve/serveDetail/" + this.appCode,
                });
              }
            });
          }
        } else {
          return false;
        }
      });
    },
    resetForm() {
      this.$router.push({
        path:
          "/serve/serveDetail/plug-in?" +
          "appcode=" +
          this.appCode +
          "&appid=" +
          this.appId,
      });
    },
    handleChange(value) {
      console.log(value);
    },
  },
};
</script>

<style>
</style>