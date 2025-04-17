import ProfileIcon from "assets/icons/profile-icon.svg";
import UserEmailIcon from "assets/icons/user-email-icon.svg";
import ApiService from "services/ApiService";
import {toast} from "react-toastify";
import UrlService from "services/UrlService";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import AuthenticationService from "services/AuthenticationService";
import BooksIcon from "assets/icons/admin-books-icon.svg";
import AuthorsIcon from "assets/icons/admin-authors-icon.svg";
import BookList from "../../../common/user/BookList";

const AdminPanel = () => {

    const [user, setUser] = useState(null);
    const [books, setBooks] = useState(null);

    const navigate = useNavigate();

    const fetchUser = async () => {
        const { body, error } = await ApiService.getProfile();
        if (body && body.status === ApiService.statusCode.OK)
            setUser(body.data);
        else if (body && body.status !== ApiService.statusCode.OK)
            toast.error(body.message);
        else
            navigate(UrlService.urls.unexpectedError);
    }

    const fetchBooks = async () => {
        const { body } = await ApiService.searchBooks();
        if (body && body.status === ApiService.statusCode.OK)
            setBooks(body.data.list);
        else if (body && body.status !== ApiService.statusCode.OK)
            toast.error(body.message);
        else
            navigate(UrlService.urls.unexpectedError);
    }

    useEffect(() => {
        fetchUser();
        fetchBooks();
    }, []);

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
        <main className="container">
            <section className="shadow rounded-3 p-3 mb-5 h-100 d-flex justify-content-between align-items-center">
                <div>
                    <p className="text-nowrap overflow-auto">
                        <img src={ProfileIcon} alt="user-icon"/> {user?.username}
                    </p>
                    <p className="text-nowrap overflow-auto mb-0">
                        <img src={UserEmailIcon} alt="user-icon"/> {user?.email}
                    </p>
                </div>
                <div>
                    <button onClick={onLogout} className="btn btn-sm bg-gray border-0 px-4 bg-gray">logout
                    </button>
                </div>
            </section>

            <section>
                <div className="d-flex justify-content-center mb-5">
                    <button className="btn btn-lg green-btn mx-4">Add Author</button>
                    <button className="btn btn-lg green-btn mx-4">Add Book</button>
                </div>

                <div className="shadow rounded-3 p-3 mb-4">
                    <p className="fs-3 fw-bold d-flex align-items-center">
                        <img className="me-2" src={BooksIcon} alt="books-icon"/> Books
                    </p>
                    <BookList bookList={books} />
                </div>

                <div className="shadow rounded-3 p-3">
                    <p className="fs-3 fw-bold d-flex align-items-center">
                        <img className="me-2" src={AuthorsIcon} alt="books-icon"/> Authors
                    </p>

                </div>
            </section>
        </main>
    );
}

export default AdminPanel;
