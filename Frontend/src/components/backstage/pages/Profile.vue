<template>
<div>
    <navbar></navbar>
    <div class="container">
        <header class="jumbotron">
            <h3>
                <strong>{{ currentUser.username }}</strong>
            </h3>
        </header>
        <p>
            <strong>Token:</strong>
            {{ currentUser.accessToken.substring(0,20) }}
            {{ currentUser.accessToken.substr(currentUser.accessToken.length - 20) }}
        </p>
        <p>
            <strong>Email:</strong>
            {{ currentUser.email }}
        </p>
        <strong>Authorities:</strong>
        <ul>
            <li v-for="(role, index) in currentUser.roles" :key="index">
                {{ role }}
            </li>
        </ul>
    </div>
</div>
</template>

<script lang="ts">
import navbar from "../shared/Navbar.vue";
import { Component, Vue } from "vue-property-decorator";
import { namespace } from "vuex-class";
const Auth = namespace("Auth");

@Component({
  components: {
    navbar,
  },
})
export default class Profile extends Vue {
    
    @Auth.State("user")
    private currentUser!: any;

    mounted() {
        if(!this.currentUser) {
            this.$router.push("/login");
        }
    }
}

</script>