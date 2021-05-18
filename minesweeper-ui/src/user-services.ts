import http from './http-service';

const BASE_USER_URL = '/api/user';

const UserService = http.RestService(BASE_USER_URL, class {
    async resendActivation(email:string) {
        return await (await http.post(BASE_USER_URL + '/resendActivation', {
            body: 'email=' + escape(email),
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        })).json();
    }
    async current() {
        return await (await http.get(BASE_USER_URL + '/current', {})).json();
    }
    async activate(activationCode:string) {
        return await (await http.post(BASE_USER_URL + '/activate/' + activationCode, {})).json();
    }
    async recover(email:string) {
        return await (await http.post(BASE_USER_URL + '/recover/' + email, {})).json();
    }
    async changePassword(recoverCode:string, password:string, passwordConfirmation:string) {
        return await (await http.post(BASE_USER_URL + '/recover', {
            body: { recoverCode, password, passwordConfirmation },
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        })).json();
    }
}, { delete: false });
const userService : any = new UserService();

export default userService;