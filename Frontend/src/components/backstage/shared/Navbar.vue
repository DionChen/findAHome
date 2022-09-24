<template>
  <div class="">
    <el-menu
      :default-active="activeIndex"
      class="el-menu navbar-box"
      mode="horizontal"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
    >
      <el-menu-item index="1"
        ><router-link to="/console" class="nav-link">
        <i class="el-icon-s-home"></i>Home</router-link
        ></el-menu-item
      >
      <el-menu-item index="2" v-if="showHotelAdminBoard">
        <router-link to="/console/hotel-admin" class="nav-link"
          >Hotel Admin Board</router-link
        ></el-menu-item
      >
      <el-menu-item index="3" v-if="showHotelAdminBoard"
        ><router-link to="/console/hotel-admin" class="nav-link"
          >Hotel Admin Board</router-link
        ></el-menu-item
      >
      <el-menu-item index="4" v-if="currentUser" to="/user">User </el-menu-item>
      <el-menu-item index="5" v-if="!currentUser"
        ><router-link to="/console/register" class="nav-link">
          <font-awesome-icon icon="user-plus" /> Sign Up
        </router-link>
      </el-menu-item>
      <el-menu-item index="6" v-if="!currentUser"
        ><router-link to="/console/login" class="nav-link">
            <font-awesome-icon icon="sign-in-alt" /> Login
          </router-link>
      </el-menu-item>
        <el-menu-item index="7" v-if="currentUser"
        ><router-link to="/console/profile" class="nav-link">
            <font-awesome-icon icon="user" />
            {{ currentUser.username }}
          </router-link>
      </el-menu-item>
    </el-menu>

    <!-- <nav class="navbar navbar-expand navbar-dark bg-dark">
      <a href class="navbar-brand" @click.prevent>FindAHome</a>
      <div class="navbar-nav mr-auto">
        <li v-if="showAdminBoard" class="nav-item">
          <router-link to="/console/admin" class="nav-link"
            >Admin Board</router-link
          >
        </li>
        <li v-if="showHotelAdminBoard" class="nav-item">
          <router-link to="/console/hotel-admin" class="nav-link"
            >Hotel Admin Board</router-link
          >
        </li>
        <li class="nav-item">
          <router-link v-if="currentUser" to="/user" class="nav-link"
            >User</router-link
          >
        </li>
      </div>
      <div v-if="!currentUser" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link to="/console/register" class="nav-link">
            <font-awesome-icon icon="user-plus" /> Sign Up
          </router-link>
        </li>
        <li class="nav-item">
          <router-link to="/console/login" class="nav-link">
            <font-awesome-icon icon="sign-in-alt" /> Login
          </router-link>
        </li>
      </div>
      <div v-if="currentUser" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link to="/console/profile" class="nav-link">
            <font-awesome-icon icon="user" />
            {{ currentUser.username }}
          </router-link>
        </li>
        <li class="nav-item">
          <a class="nav-link" href @click.prevent="logOut">
            <font-awesome-icon icon="sign-out-alt" /> LogOut
          </a>
        </li>
      </div>
    </nav> -->
  </div>
</template>

<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import { namespace } from "vuex-class";
const Auth = namespace("Auth");

@Component
export default class App extends Vue {
  //init data
  activeIndex = "1";

  activeIndex2 = "1";

  //Auth
  @Auth.State("user")
  private currentUser!: any;

  @Auth.Action
  private signOut!: () => void;

  get showAdminBoard(): boolean {
    if (this.currentUser && this.currentUser.roles) {
      return this.currentUser.roles.includes("ROLE_ADMIN");
    }
    return false;
  }
  get showHotelAdminBoard(): boolean {
    if (this.currentUser && this.currentUser.roles) {
      return this.currentUser.roles.includes("ROLE_HOTEL_ADMIN");
    }
    return false;
  }
  logOut() {
    this.signOut();
    this.$router.push("/login");
  }
}
</script>
<style scoped>
 .navbar-box {
 box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
 }
</style>