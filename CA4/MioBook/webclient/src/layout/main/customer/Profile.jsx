import { useState, useEffect } from "react";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import ApiService from "services/ApiService";
import UrlService from "services/UrlService";
import ProfileIcon from "assets/icons/profile-icon.svg";
import UserEmailIcon from "assets/icons/user-email-icon.svg";
import MyBooks from "assets/icons/profile-books-icon.svg";
import NoProduct from "assets/images/user/no-product.svg";
import NoResult from "assets/images/user/no-result.svg";
import AuthenticationService from "services/AuthenticationService";

const Profile = () => {
    const [user, setUser] = useState(null);
    const [books, setBooks] = useState(null);

    const navigate = useNavigate();

    useEffect(() => {
        const fetchUser = async () => {
        const { body, error } = await ApiService.getProfile();
            if (error)
                toast.error(error);
            else if (body && body.status === ApiService.statusCode.OK)
                setUser(body.data);
            else
                navigate(UrlService.urls.unexpectedError);
        }

        const fetchBooks = async () => {
            const { body, error } = await ApiService.getProfileBooks();
            if (error)
                toast.error(error);
            else if (body && body.status === ApiService.statusCode.OK)
                setBooks(body.data.list);
            else
                navigate(UrlService.urls.unexpectedError);
        }

        fetchUser();
        fetchBooks();
    }, [navigate]);

    const onLogout = async () => {
        const body = await AuthenticationService.logout();

        if (!body)
            navigate(UrlService.urls.unexpectedError);
        else if (body.status !== ApiService.statusCode.OK)
            toast.error(body.message);
        else
            navigate(UrlService.urls.signIn);
    }

    return (
        <main class="d-flex flex-column align-items-center">
            <section class="container row mb-5">
                <div class="col-12 col-sm-6 col-xl-8 mb-4 mb-sm-0">
                    <div class="shadow rounded-3 p-3 h-100 d-flex flex-column justify-content-between">
                        <p class="fw-bold fs-3">${user?.balance}</p>
                        <div class="d-flex flex-wrap">
                            <label for="credit-amount" class="visually-hidden">Credit Amount</label>
                            <input class="form-control w-100 w-md-50 w-xl-75 mb-2 mb-md-0 credit-input" id="credit-amount" type="number" placeholder="$Amount" />
                            <button class="btn w-100 w-md-auto ms-auto green-btn">Add more credit</button>
                            {/* FIXME: it should be enable until a value is entered */}
                        </div>
                    </div>
                </div>

                <div class="col-12 col-sm-6 col-xl-4">
                    <div class="shadow rounded-3 p-3 h-100 d-flex flex-column justify-content-between">
                        <p class="text-nowrap overflow-auto">
                            <img src={ProfileIcon} alt="user-icon" /> {user?.username}
                        </p>
                        <p class="text-nowrap overflow-auto">
                            <img src={UserEmailIcon} alt="user-icon" /> {user?.email}
                        </p>
                        <div>
                            <button onClick={onLogout} class="btn btn-sm bg-gray border-0 px-4 bg-gray">logout</button>
                        </div>
                    </div>
                </div>
            </section>

            <section class="container row mb-5">
                <div class="col-12">
                    <div class="shadow rounded-3 p-3">
                        <p class="fw-bold fs-3">
                            <img src={MyBooks} alt="buy-basket" />
                            MyBooks
                        </p>
                        <div class="text-center">
                            <img src={NoResult} alt="no-product" />
                            {/* TODO */}
                        </div>
                    </div>
                </div>
            </section>
        </main>
    );
};

export default Profile;