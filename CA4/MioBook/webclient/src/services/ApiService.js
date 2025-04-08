const baseUrl = process.env.REACT_APP_API_URL + "/api";
const defaultHeader = {"Content-Type": "application/json"};

const signInUrl = baseUrl + "/auth/login";
const signUpUrl = baseUrl + "/users";


const signUp = async (username, password, email, country, city, role) => {
    const response = await fetch(signUpUrl, {
            method: "POST",
            headers: defaultHeader,
            body: JSON.stringify({
                username: username,
                password: password,
                email: email,
                address: {
                    country: country,
                    city: city
                },
                role: role
            })
        }
    );
    const body = await response.json();
    return {response, body};
}

const signIn = async (username, password) => {
    const response = await fetch(signInUrl, {
            method: "POST",
            headers: defaultHeader,
            body: JSON.stringify({
                username: username,
                password: password
            })
        }
    );
    const body = await response.json();
    return {response, body};
}


const statusCode = Object.freeze({
    OK: 200,
    CREATED: 201,
    BAD_REQUEST: 400,
    UNAUTHORIZED: 401
})


const ApiService = Object.freeze({
    signIn,
    signUp,
    statusCode
});

export default ApiService;
