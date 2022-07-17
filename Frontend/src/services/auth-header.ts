import { AxiosRequestHeaders } from "axios";
export default function authHeader() : AxiosRequestHeaders {
    const storeUser = localStorage.getItem('user');
    let user = JSON.parse(storeUser ? storeUser : "");
    if(user && user.accessToken) {
        return { Authorization: 'Bearer ' + user.accessToken };
    }else {
        return {};
    }
}