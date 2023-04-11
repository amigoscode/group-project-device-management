import {aboutMe} from "./userClient.js";

export {
    user,
    login,
    logout
};

function user() {
    return JSON.parse(localStorage.getItem('user'));
}

function login(username, password) {

    aboutMe(username, password).then(res => {
        // login successful if there's a user in the response
        const user = { ...res.data }
        if (user.email === username) {
            // store user details and basic auth credentials in local storage
            // to keep user logged in between page refreshes
            user.auth_username = username;
            user.auth_password = password;
            localStorage.setItem('user', JSON.stringify(user));
            window.location.reload()
            // redirect to dashboard
            window.location.assign("/about-me")
        }
    }).catch(err => {
        logout()
    }).finally(() => {

    })

}

function logout() {
    // remove user from local storage to log user out
    localStorage.removeItem('user');
    // redirect to login page
    window.location.assign("/")
}
