const urls = Object.freeze({
    signIn: "/sign-in",
    signUp: "/sign-up",
    home: "/home",
    searchResult: "/search-result",
    profile: "/profile",
    myBooks: "/purchased-books",
    cart: "/cart",
    history: "/purchased-history",
    books: "/books",
    authors: "/authors",
    unexpectedError: "/unexpected-errors",
    notFound: "/not-found",
    accessDenied: "/access-denied",
});

const doNotShowHeaderUrls = Object.freeze([
    urls.signIn,
    urls.signUp,
    urls.unexpectedError,
    urls.notFound,
    urls.accessDenied,
]);

const defaultAccessUrls = Object.freeze([
    urls.signIn,
    urls.signUp,
    urls.unexpectedError,
    urls.notFound,
    urls.accessDenied,
]);

const mustShowHeader = (url) => {
    if (!Object.values(urls).includes(url))
        return false;

    return !doNotShowHeaderUrls.includes(url);
}

const hasDefaultAccess = (url) => {
    if (!Object.values(urls).includes(url))
        return true;

    return defaultAccessUrls.includes(url);
}

const isAvailable = (url) => {
    return Object.values(urls).includes(url);
}

const UrlService = Object.freeze({
    mustShowHeader,
    hasDefaultAccess,
    isAvailable,
    urls
});

export default UrlService;
