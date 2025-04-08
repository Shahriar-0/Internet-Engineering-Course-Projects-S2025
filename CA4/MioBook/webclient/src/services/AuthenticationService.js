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
    const {data} = await ApiService.signIn(username, password);

    if (data.status === ApiService.statusCode.OK)
        currentUser = {username: username, password: password};

    return data;
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
