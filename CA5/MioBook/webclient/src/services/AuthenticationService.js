import ApiService from "./ApiService";

const Role = Object.freeze({ CUSTOMER: "customer", ADMIN: "admin" });

const isAnyUserLoggedIn = () => localStorage.getItem("user") !== null;

const isUserLoggedIn = (username) => {
    if (!isAnyUserLoggedIn())
        return false;

    return JSON.parse(localStorage.getItem("user")).username === username;
}

const getCurrentUser = () => structuredClone(JSON.parse(localStorage.getItem("user")));

const login = async (username, password) => {
    const { body } = await ApiService.signIn(username, password);

    if (body !== null && body.status === ApiService.statusCode.OK)
        localStorage.setItem("user", JSON.stringify({ username: username, password: password, role: body.data }));

    return body;
};

const isLoggedInUserAdmin = () => {
    if (!isAnyUserLoggedIn())
        return false;

    return JSON.parse(localStorage.getItem("user")).role === Role.ADMIN;
}

const logout = async () => {
    const { body } = await ApiService.signOut();
    if (body)
        localStorage.removeItem("user");

    return body;
};

const AuthenticationService = Object.freeze({
    isAnyUserLoggedIn,
    isUserLoggedIn,
    getCurrentUser,
    isLoggedInUserAdmin,
    login,
    logout,
});

export default AuthenticationService;
