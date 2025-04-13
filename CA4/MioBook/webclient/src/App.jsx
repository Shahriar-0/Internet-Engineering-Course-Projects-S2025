import {BrowserRouter, Routes, Route} from "react-router-dom";
import Header from "layout/header/Header";
import Footer from "layout/footer/Footer";
import SignIn from "layout/main/sign-in/SignIn";
import SignUp from "layout/main/sign-up/SignUp";
import UnexpectedError from "layout/main/errors/unexpected-error/UnexpectedError";
import UrlAccessControl from "services/UrlAccessControl";

import UrlService from "services/UrlService";
import {ToastContainer} from "react-toastify";

function App() {
    const urls = UrlService.urls;

    return (
        <>
        <ToastContainer />
        <BrowserRouter>
            <UrlAccessControl />
            <Header />
            <Routes>
                <Route path={urls.signIn} element={<SignIn />} />
                <Route path={urls.signUp} element={<SignUp />}/>
                <Route path={urls.home} element={<h1>Home</h1>} />
                <Route path={urls.unexpectedError} element={<UnexpectedError />} />
            </Routes>
            <Footer />
        </BrowserRouter>
        </>
    );
}

export default App;
