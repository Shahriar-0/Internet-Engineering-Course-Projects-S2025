import ApiService from "./ApiService";

let currentUser = null;

const isAnyUserLoggedIn = () => currentUser !== null;

const isUserLoggedIn = (username) => {
    if (!isAnyUserLoggedIn())
        return false;

    return currentUser.username === username;
}

const getCurrentUser = () => structuredClone(currentUser);

const login = async (username, password) => {
    const {body} = await ApiService.signIn(username, password);

    if (body.status === ApiService.statusCode.OK)
        currentUser = {username: username, password: password};

    return body;
};

const logout = () => {
    console.log("Logging out...");
    currentUser = null;
};

const AuthenticationService = Object.freeze({
    isAnyUserLoggedIn,
    isUserLoggedIn,
    getCurrentUser,
    login,
    logout,
});


export default AuthenticationService;
