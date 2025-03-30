let currentUser = null;

const isAnyUserLoggedIn = () => currentUser !== null;

const isUserLoggedIn = (username) => {
    if (!isAnyUserLoggedIn())
        return false;

    return currentUser.username === username;
}

const getCurrentUser = () => structuredClone(currentUser);

const login = (username, password) => {
    console.log("Logging in...");
    console.log("username: " + username + " password: " + password);
    currentUser = {username: username, password: password};
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
