import ApiService from "./ApiService";
import rolePicker from "../layout/main/sign-up/sign-up-form/RolePicker";

const SESSION_ID_HEADER_KEY = "Session-Id";
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

    const sessionId = JSON.parse(localStorage.getItem(LOGGED_IN_USER_KEY)).sessionId;
    const sessionAddedHeader = {...header, [SESSION_ID_HEADER_KEY]: sessionId};
    return sessionAddedHeader;
}

const getCurrentUser = () => structuredClone(JSON.parse(localStorage.getItem(LOGGED_IN_USER_KEY)));

const login = async (username, password) => {
    const { response, body } = await ApiService.signIn(username, password);

    if (body !== null && body.status === ApiService.statusCode.OK)
        localStorage.setItem(LOGGED_IN_USER_KEY, JSON.stringify({
            username: username,
            role: body.data,
            sessionId: response.headers.get(SESSION_ID_HEADER_KEY)
        }));

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

const getGoogleLoginUrl = async () => {
    const { body } = await ApiService.getGoogleLoginUrl();
    return body;
}

const setLoggedInUser = (username, role) => {
    localStorage.setItem(LOGGED_IN_USER_KEY, JSON.stringify({
        username: username,
        role: role,
    }));
}

const AuthenticationService = Object.freeze({
    setLoggedInUser,
    getGoogleLoginUrl,
    isAnyUserLoggedIn,
    isUserLoggedIn,
    addSessionToHeader,
    getCurrentUser,
    isLoggedInUserAdmin,
    login,
    logout,
});

export default AuthenticationService;