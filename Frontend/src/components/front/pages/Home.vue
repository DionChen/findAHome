<template>
  <div class="banner">
    <el-container class="main_container">
      <el-header>
        <navbar></navbar>
      </el-header>
      <el-main>
        <el-row>
          <el-col :span="24">
            <el-image
              style="width: 100%; height: 300px"
              :src="bannerImg"
              fit="cover"
            ></el-image>
          </el-col>
          <el-col :span="12" :offset="6">
            <div class="title">
              <p>{{ title }}</p>
            </div>
          </el-col>
          <el-col :span="16" :offset="4">
            <el-card shadow="always" style="border-radius: 12px" class="card">
              <form>
                <el-input
                  v-model="search"
                  prefix-icon="el-icon-search"
                  placeholder="Enter a destination or property"
                ></el-input>
                <!-- <el-row :gutter="20"> -->
                <el-col :span="6">
                  <el-date-picker
                    v-model="date"
                    type="datetimerange"
                    start-placeholder="Start date"
                    end-placeholder="End date"
                    style="margin-top: 15px"
                  >
                  </el-date-picker>
                </el-col>
                <el-col :span="6" :offset="9">
                  <el-input-number
                    v-model="num"
                    :min="1"
                    :max="100"
                    style="margin-top: 15px"
                  ></el-input-number>
                </el-col>
                <el-col :span="3">
                  <div style="margin-top: 15px">
                    <el-select
                      v-model="type"
                      slot="prepend"
                      placeholder="Select"
                    >
                      <el-option label="Dog" value="dog"></el-option>
                      <el-option label="Cat" value="cat"></el-option>
                      <el-option label="Other" value="other"></el-option>
                    </el-select>
                  </div>
                </el-col>
                <el-button
                  style="margin-top: 15px; width: 100%"
                  type="warning"
                  icon="el-icon-search"
                  >Search</el-button
                >
              </form>
            </el-card>
          </el-col>
        </el-row>
        <el-row class="space-content">
        </el-row>

        <el-row>
          <el-col :span="12" :offset="6">
            <div class="news-title">
              <p>{{ newsTitle }}</p>
            </div>
          </el-col>

          <el-col :span="22" :offset="1">
            <el-carousel
              :interval="4000"
              type="card"
              height="250px"
              class="custom-carousel"
            >
              <el-carousel-item v-for="news in 6" :key="news">
                <h3 class="medium">{{ news }}</h3>
              </el-carousel-item>
            </el-carousel>
          </el-col>
        </el-row>

        <el-row class="sug-title">
          <el-col :span="12" :offset="6">
              <p>{{ suggestedTitle }}</p>
          </el-col>
        </el-row>

        <el-row class="sug-card">
          <el-col
            :span="5"
            v-for="(suggestion, index) in 4"
            :key="index"
            :offset="index > 0 ? 1 : 0"
          >
            <el-card :body-style="{ padding: '0px' }">
              <img :src="suggestion" class="image" />
              <div style="padding: 14px">
                <span>Yummy hamburger{{index}}</span>
                <div class="bottom clearfix"></div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-main>
      <el-footer>
        <footer></footer>
      </el-footer>
    </el-container>
  </div>
</template>


<script lang="ts">
import navbar from "../shared/Navbar.vue";
import { Component, Vue } from "vue-property-decorator";
import axios from "axios";

@Component({
  components: {
    navbar,
  },
})
export default class Home extends Vue {
  //data
  //search data
  search = "";
  date = "";
  num = 1;
  type = "";
  //front-data
  title = "";
  bannerImg = "";
  newsTitle = "";
  news = "";
  suggestedTitle = "";
  suggestion = "";
  //lifecyle hook
  created() {
        axios.get('/api/public/front-data/landing').then((res) => {
            this.title = res.data.title
            this.bannerImg = res.data.bannerImg
            this.newsTitle = res.data.newsTitle
            this.news = res.data.news
            this.suggestedTitle = res.data.suggestedTitle
            this.suggestion = res.data.suggestion
        })
  }
}
</script>
<style scoped>
.el-container {
  height: 100%;
  padding: 0;
  margin: 0;
  widows: 100%;
}
.banner {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  justify-content: center;
  align-items: center;
}
.banner .card {
  position: absolute;
  top: 200px;
  left: 10%;
  justify-content: center;
}
  .space-content {
    min-height: 120px;
  }
.el-button--warning {
  background: #d14c2e !important;
}
.el-button--warning:hover {
  background: #de816c !important;
}
.banner .title p {
  font-family: "微软雅黑";
  color: #303030;
  font-size: 50px;
  width: 100%;
  position: absolute;
  display: flex;
  justify-content: center;
  top: 80px;
  left: 0%;
}
.news-title p {
  font-family: "微软雅黑";
  color: #303030;
  font-size: 50px;
  width: 100%;
  display: flex;
  justify-content: center;
}
.sug-title p {
  font-family: "微软雅黑";
  color: #303030;
  font-size: 50px;
  width: 100%;
  display: flex;
  justify-content: center;
}
.main_container {
  height: 100%;
}
.el-carousel__item h3 {
  color: #475669;
  font-size: 14px;
  opacity: 0.75;
  margin: 0;
  border-radius: 30px 30px 30px 30px;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
  border-radius: 30px 30px 30px 30px;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
  border-radius: 30px 30px 30px 30px;
}

.el-footer {
  /* position: fixed;
  bottom: 0px;
  left: 0px; */
  width: 100%;
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  line-height: 60px;
}
</style>