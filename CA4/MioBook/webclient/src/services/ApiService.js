const baseUrl = process.env.REACT_APP_API_URL + "/api/";
const defaultHeader = {"Content-Type": "application/json"};

const signInUrl = baseUrl + "auth/login";


const signIn = async (username, password) => {
    const body = {username: username, password: password};
    return await fetch(signInUrl, {
            method: "POST",
            headers: defaultHeader,
            body: JSON.stringify(body)
        }
    );
}


const statusCode = Object.freeze({
    OK: 200,
    UNAUTHORIZED: 401
})


const ApiService = Object.freeze({
    signIn,
    statusCode
});

export default ApiService;
