<template>
<div>
    <navbar></navbar>
    <div class="container">
        <header class="jumbotron">
            <h3>{{ content }}</h3>
        </header>
    </div>
</div>    
</template>
<script lang="ts">
import navbar from "../shared/Navbar.vue";
import { Component, Vue } from "vue-property-decorator";
import UserService from "@/services/UserService";

@Component({
  components: {
    navbar,
  },
})
export default class UserBoard extends Vue {
    private content = "";

    mounted() {
        UserService.getUserBoard().then(
            (response) => {
                this.content = response.data;
            },
            (error) => {
                this.content = 
                (error.response && error.response.data && error.response.data.message) ||
                error.message ||
                error.toString();
            }
        );
    }
}
</script>