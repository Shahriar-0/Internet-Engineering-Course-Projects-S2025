import AuthenticationService from "./AuthenticationService";

const baseUrl = process.env.REACT_APP_API_URL + "/api";
const defaultHeader = { "Content-Type": "application/json" };

const signInUrl = baseUrl + "/auth/login";
const signOutUrl = baseUrl + "/auth/logout";
const signUpUrl = baseUrl + "/users";
const booksUrl = baseUrl + "/books";
const getReviewsUrlByTitle = (title) => booksUrl + "/" + title + "/reviews";
const genresUrl = booksUrl + "/genres";
const authorsUrl = baseUrl + "/authors";
const usersUrl = baseUrl + "/users";
const profileUrl = baseUrl + "/profile";
const cartUrl = profileUrl + "/cart";

const HttpMethod = Object.freeze({
    GET: "GET",
    POST: "POST",
    PUT: "PUT",
    DELETE: "DELETE",
    PATCH: "PATCH"
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

const createFilterQueryForSearchBook = (filter) => {
    if (!filter) return "";

    let query = "?";

    if (filter.title) query += `title=${filter.title}&`;
    if (filter.author) query += `author=${filter.author}&`;
    if (filter.genre) query += `genre=${filter.genre}&`;
    if (filter.from) query += `from=${filter.from}&`;
    if (filter.to) query += `to=${filter.to}&`;
    if (filter.sortBy) query += `sortBy=${filter.sortBy}&`;
    query += (filter.isAscending) ? "isAscending=true&" : "isAscending=false&";
    if (filter.pageNumber) query += `pageNumber=${filter.pageNumber}&`;
    if (filter.pageSize) query += `pageSize=${filter.pageSize}&`;
    query = query.endsWith("&") ? query.slice(0, -1) : query;

    return query;
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

const getBook = async (title) => {
    return await apiCallTemplate(HttpMethod.GET, booksUrl + "/" + title, null);
}

const getBookReviews = async (title) => {
    return await apiCallTemplate(HttpMethod.GET, getReviewsUrlByTitle(title), null);
}

const addReview = async (title, rating, review) => {
    const reqBody = { rating: rating, comment: review };
    return await apiCallTemplate(HttpMethod.POST, getReviewsUrlByTitle(title), reqBody);
}

const getAuthor = async (name) => {
    return await apiCallTemplate(HttpMethod.GET, authorsUrl + "/" + name, null);
}

const addAuthor = async (name, penName, nationality, born, died, imageLink) => {
    const reqBody = {
        name: name,
        penName: penName,
        nationality: nationality,
        born: born,
        died: (died) ? died : null,
        imageLink: (imageLink) ? imageLink : null
    };

    return await apiCallTemplate(HttpMethod.POST, authorsUrl, reqBody);
}

const getProfile = async () => {
    return await apiCallTemplate(HttpMethod.GET, usersUrl + "/" + AuthenticationService.getCurrentUser().username, null);
}

const getProfileBooks = async () => {
    return await apiCallTemplate(HttpMethod.GET, profileUrl + "/books", null);
}

const addCredit = async (amount) => {
    const reqBody = {
        amount: amount
    }
    return await apiCallTemplate(HttpMethod.PATCH, profileUrl + "/credit", reqBody);
}

const getCart = async () => {
    return await apiCallTemplate(HttpMethod.GET, profileUrl + "/cart", null);
}

const searchBooks = async (filter = null) => {
    return await apiCallTemplate(HttpMethod.GET, booksUrl + createFilterQueryForSearchBook(filter), null);
}

const addBook = async (title, author, publisher, genres, publishedYear, price, synopsis, content, imageLink) => {
    const reqBody = {
        title: title,
        author: author,
        publisher: publisher,
        genres: genres,
        year: publishedYear,
        price: price,
        imageLink: imageLink,
        synopsis: synopsis,
        content: content
    }
    return await apiCallTemplate(HttpMethod.POST, booksUrl, reqBody);
}

const getGenres = async () => {
    return await apiCallTemplate(HttpMethod.GET, genresUrl, null);
}

const getAllAuthors = async () => {
    return await apiCallTemplate(HttpMethod.GET, authorsUrl, null);
}

const purchaseCart = async () => {
    return await apiCallTemplate(HttpMethod.POST, profileUrl + "/purchase", null);
}

const getPurchasedHistory = async () => {
    return await apiCallTemplate(HttpMethod.GET, profileUrl + "/history", null);
}

const getBookContent = async (title) => {
    return await apiCallTemplate(HttpMethod.GET, booksUrl + "/" + title + "/content", null);
}

const deleteFromCart = async (title) => {
    return await apiCallTemplate(HttpMethod.DELETE, cartUrl + "/" + title);
}

const addToCart = async (title) => {
    const reqBody = {
        title: title
    }
    return await apiCallTemplate(HttpMethod.POST, cartUrl, reqBody);
}

const borrowCart = async (title, days) => {
    const reqBody = {
        title: title,
        borrowedDays: days
    }
    return await apiCallTemplate(HttpMethod.POST, profileUrl + "/borrow", reqBody);
}

const ApiService = Object.freeze({
    signIn,
    signOut,
    signUp,
    getBooks,
    getBook,
    getBookReviews,
    getAuthor,
    addAuthor,
    getProfile,
    getProfileBooks,
    addCredit,
    getCart,
    searchBooks,
    addBook,
    getGenres,
    addReview,
    getAllAuthors,
    purchaseCart,
    getPurchasedHistory,
    getBookContent,
    deleteFromCart,
    addToCart,
    borrowCart,
    statusCode
});

export default ApiService;
