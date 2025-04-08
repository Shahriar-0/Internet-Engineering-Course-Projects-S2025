import { BrowserRouter, Routes, Route } from "react-router-dom";
import Header from "layout/header/Header";
import Footer from "layout/footer/Footer";
import SignIn from "layout/main/sign-in/SignIn";
import SignUp from "layout/main/sign-up/SignUp";

import UrlService from "services/UrlService";

function App() {
    const urls = UrlService.urls;

    return (
        <BrowserRouter>
            <Header/>
            <Routes>
                <Route path={urls.signIn} element={<SignIn />} />
                <Route path={urls.signUp} element={<SignUp />}/>
                <Route path={urls.home} element={<div>Home</div>} />
            </Routes>
            <Footer />
        </BrowserRouter>
    );
}

export default App;
