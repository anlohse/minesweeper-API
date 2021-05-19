import http from './http-service';

const BASE_USER_URL = '/api/user';

const UserService = http.RestService(BASE_USER_URL, class {
    async resendActivation(email:string) {
        return await http.post(BASE_USER_URL + '/resendActivation', {
            body: 'email=' + escape(email),
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        });
    }
    async current() {
        return await http.get(BASE_USER_URL + '/current', {});
    }
    async activate(activationCode:string) {
        return await http.post(BASE_USER_URL + '/activate/' + activationCode, {});
    }
    async recover(email:string) {
        return await http.post(BASE_USER_URL + '/recover/' + email, {});
    }
    async changePassword(recoverCode:string, password:string, passwordConfirmation:string) {
        return await http.post(BASE_USER_URL + '/recover', {
            body: { recoverCode, password, passwordConfirmation },
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
        });
    }
}, { delete: false });
const userService : any = new UserService();

export default userService;