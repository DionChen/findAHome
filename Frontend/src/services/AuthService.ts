import axios from 'axios';
const API_URL = '/api/auth';
class AuthService {
    login(username: string, password: string) {
        return axios
        .post(API_URL + 'signin', {
            username,
            password
        })
        .then(response => {
            if(response.data.accessToken) {
                localStorage.setItem('user', JSON.stringify(response.data));
            }
            return response.data;
        });
    }
    logout() {
        localStorage.removeItem('user');
    }
    register(username: string, emal: string, password: string) {
        return axios.post(API_URL + 'signup', {
            username,
            emal,
            password
        });
    }
}
export default new AuthService();