import ApiService from "./ApiService";

const LOGGED_IN_USER_KEY = "user";
const Role = Object.freeze({ CUSTOMER: "customer", ADMIN: "admin" });

const isAnyUserLoggedIn = () => localStorage.getItem(LOGGED_IN_USER_KEY) !== null;

const isUserLoggedIn = (username) => {
    if (!isAnyUserLoggedIn())
        return false;

    return JSON.parse(localStorage.getItem(LOGGED_IN_USER_KEY)).username === username;
}

const addSessionToHeader = (header) => {
    if (!isAnyUserLoggedIn())
        return header;
    const jwt = JSON.parse(localStorage.getItem(LOGGED_IN_USER_KEY)).jwt;
    if (!jwt)
        return header;
    return { ...header, Authorization: `Bearer ${jwt}` };
}

const getCurrentUser = () => structuredClone(JSON.parse(localStorage.getItem(LOGGED_IN_USER_KEY)));

const login = async (username, password) => {
    const { response, body } = await ApiService.signIn(username, password);
    let jwt = null;
    if (response && response.headers && response.headers.get("Authorization")) {
        const authHeader = response.headers.get("Authorization");
        if (authHeader.startsWith("Bearer "))
            jwt = authHeader.substring(7);
    }
    if (body !== null && body.status === ApiService.statusCode.OK && jwt) {
        localStorage.setItem(LOGGED_IN_USER_KEY, JSON.stringify({
            username: username,
            role: body.data,
            jwt: jwt
        }));
    }
    return body;
};

const isLoggedInUserAdmin = () => {
    if (!isAnyUserLoggedIn())
        return false;

    return JSON.parse(localStorage.getItem(LOGGED_IN_USER_KEY)).role === Role.ADMIN;
}

const logout = async () => {
    const { body } = await ApiService.signOut();
    if (body)
        localStorage.removeItem(LOGGED_IN_USER_KEY);

    return body;
};

const AuthenticationService = Object.freeze({
    isAnyUserLoggedIn,
    isUserLoggedIn,
    addSessionToHeader,
    getCurrentUser,
    isLoggedInUserAdmin,
    login,
    logout,
});

export default AuthenticationService;
