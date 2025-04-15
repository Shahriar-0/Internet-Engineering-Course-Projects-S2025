const baseUrl = process.env.REACT_APP_API_URL + "/api";
const defaultHeader = { "Content-Type": "application/json" };

const signInUrl = baseUrl + "/auth/login";
const signOutUrl = baseUrl + "/auth/logout";
const signUpUrl = baseUrl + "/users";
const booksUrl = baseUrl + "/books";

const HttpMethod = Object.freeze({
    GET: "GET",
    POST: "POST",
    PUT: "PUT",
    DELETE: "DELETE"
});

const statusCode = Object.freeze({
    OK: 200,
    CREATED: 201,
    BAD_REQUEST: 400,
    UNAUTHORIZED: 401
})

const apiCallTemplate = async (httpMethod, url, reqBody) => {
    try {
        const options = {
            method: httpMethod,
            headers: defaultHeader
        };

        if (reqBody && httpMethod !== "GET") {
            options.body = JSON.stringify(reqBody);
        }

        const response = await fetch(url, options);
        const body = await response.json();
        return { response, body, error: null };
    }
    catch (e) {
        return { response: null, body: null, error: e };
    }
}

const signUp = async (username, password, email, country, city, role) => {
    const reqBody = {
        username: username,
        password: password,
        email: email,
        address: {
            country: country,
            city: city
        },
        role: role
    };
    return await apiCallTemplate(HttpMethod.POST, signUpUrl, reqBody);
}

const signIn = async (username, password) => {
    const reqBody = {
        username: username,
        password: password
    };
    return await apiCallTemplate(HttpMethod.POST, signInUrl, reqBody);
}

const signOut = async () => {
    return await apiCallTemplate(HttpMethod.DELETE, signOutUrl, null);
}

const getBooks = async (url) => {
    return await apiCallTemplate(HttpMethod.GET, booksUrl + url, null);
}

const ApiService = Object.freeze({
    signIn,
    signOut,
    signUp,
    getBooks,
    statusCode
});

export default ApiService;
