const urls = Object.freeze({
    signIn: "/sign-in",
    signUp: "/sign-up",
    home: "/home",
    profile: "/profile",
    user: "/user",
    unexpectedError: "/unexpected-errors"
});

const doNotShowHeaderUrls = Object.freeze([
    urls.signIn,
    urls.signUp,
    urls.unexpectedError
]);

const mustShowHeader = (url) => {
    if (!Object.values(urls).includes(url))
        return false;

    return !doNotShowHeaderUrls.includes(url);
}

const UrlService = Object.freeze({
    mustShowHeader,
    urls
});


export default UrlService;
