import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { useEffect } from "react";

import Header from "layout/header/Header";
import Footer from "layout/footer/Footer";
import SignIn from "layout/main/sign-in/SignIn";
import SignUp from "layout/main/sign-up/SignUp";
import Home from "layout/main/home/Home";
import SearchResult from "layout/main/search-result/SearchResult";
import Book from "layout/main/books/Book";
import Author from "layout/main/author/Author";
import Cart from "layout/main/customer/Cart";
import CustomerProfile from "layout/main/customer/CustomerProfile";
import BookContent from "layout/main/books/BookContent";

import UnexpectedError from "layout/main/errors/UnexpectedError";
import NotFound from "layout/main/errors/NotFound";
import AccessDenied from "layout/main/errors/AccessDenied";

import UrlAccessControl from "services/UrlAccessControl";
import UrlService from "services/UrlService";
import AuthenticationService from "services/AuthenticationService";
import AdminPanel from "./layout/main/admin-panel/AdminPanel";
import PurchasedHistory from "./layout/main/purchased-history/PurchasedHistory";

function App() {
    const urls = UrlService.urls;

    useEffect(() => {
        const handleBeforeUnload = () => {
            console.log("Clearing localStorage before unload");
            // localStorage.clear();
        };

        window.addEventListener("beforeunload", handleBeforeUnload);

        return () => {
            window.removeEventListener("beforeunload", handleBeforeUnload);
        };
    }, []);

    return (
        <>
            <ToastContainer position="bottom-right" />
            <BrowserRouter>
                <UrlAccessControl />
                <Header />
                <Routes>
                    <Route path="" element={<Navigate to={AuthenticationService.isAnyUserLoggedIn() ? urls.home : urls.signIn} replace />} />
                    <Route path={urls.signIn} element={<SignIn />} />
                    <Route path={urls.signUp} element={<SignUp />} />
                    <Route path={urls.home} element={<Home />} />
                    <Route path={urls.searchResult} element={<SearchResult />} />
                    <Route path={urls.profile} element={<CustomerProfile />} />
                    <Route path={urls.adminPanel} element={<AdminPanel />} />
                    <Route path={urls.cart} element={<Cart />} />
                    <Route path={urls.history} element={<PurchasedHistory />} />
                    <Route path={`${urls.books}/:title`} element={<Book />} />
                    <Route path={`${urls.books}/:title/content`} element={<BookContent />} />
                    <Route path={`${urls.authors}/:name`} element={<Author />} />
                    <Route path={urls.unexpectedError} element={<UnexpectedError />} />
                    <Route path={urls.notFound} element={<NotFound />} />
                    <Route path={urls.accessDenied} element={<AccessDenied />} />
                </Routes>
                <Footer />
            </BrowserRouter>
        </>
    );
}

export default App;