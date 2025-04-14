import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { ToastContainer } from "react-toastify";

import Header from "layout/header/Header";
import Footer from "layout/footer/Footer";
import SignIn from "layout/main/sign-in/SignIn";
import SignUp from "layout/main/sign-up/SignUp";
import Home from "layout/main/home/Home";
import UnexpectedError from "layout/main/errors/unexpected-error/UnexpectedError";
import NotFound from "layout/main/errors/not-found/NotFound";

import UrlAccessControl from "services/UrlAccessControl";
import UrlService from "services/UrlService";

function App() {
    const urls = UrlService.urls;

    return (
        <>
            <ToastContainer />
            <BrowserRouter>
                <UrlAccessControl />
                <Header />
                <Routes>
                    <Route path="" element={<Navigate to={urls.home} replace />} />
                    <Route path={urls.signIn} element={<SignIn />} />
                    <Route path={urls.signUp} element={<SignUp />} />
                    <Route path={urls.home} element={<Home />} />
                    <Route path={urls.unexpectedError} element={<UnexpectedError />} />
                    <Route path={urls.notFound} element={<NotFound />} />
                </Routes>
                <Footer />
            </BrowserRouter>
        </>
    );
}

export default App;
