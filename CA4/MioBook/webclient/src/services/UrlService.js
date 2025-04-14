const urls = Object.freeze({
    signIn: "/sign-in",
    signUp: "/sign-up",
    home: "/home",
    profile: "/profile",
    myBooks: "/purchased-books",
    cart: "/cart",
    history: "/purchased-history",
    unexpectedError: "/unexpected-errors",
    notFound: "/not-found",
});

const doNotShowHeaderUrls = Object.freeze([
    urls.signIn,
    urls.signUp,
    urls.unexpectedError,
    urls.notFound
]);

const defaultAccessUrls = Object.freeze([
    urls.unexpectedError,
    urls.notFound,
    urls.signUp,
    urls.signIn
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
