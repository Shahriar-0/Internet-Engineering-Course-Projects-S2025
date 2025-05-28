import ProfileIcon from "assets/icons/profile-icon.svg";
import UserEmailIcon from "assets/icons/user-email-icon.svg";
import ApiService from "services/ApiService";
import { toast } from "react-toastify";
import UrlService from "services/UrlService";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import AuthenticationService from "services/AuthenticationService";
import BooksIcon from "assets/icons/admin-books-icon.svg";
import AuthorsIcon from "assets/icons/admin-authors-icon.svg";
import AuthorList from "common/author/AuthorList";
import BookList from "common/user/BookList";
import AddAuthorModal from "./add-author-modal/AddAuthorModal";
import AddBookModal from "./add-book-modal/AddBookModal";

const AdminPanel = () => {

    const [authors, setAuthors] = useState(null);
    const [user, setUser] = useState(null);
    const [books, setBooks] = useState(null);
    const [addAuthorModalOpen, setAddAuthorModalOpen] = useState(false);
    const [addBookModalOpen, setAddBookModalOpen] = useState(false);

    const navigate = useNavigate();

    useEffect(() => {
        fetchAuthors();
        fetchUser();
    }, [navigate]);

    const fetchAuthors = async () => {
        const { body } = await ApiService.getAllAuthors();
        if (body && body.status === ApiService.statusCode.OK)
            setAuthors(body.data);
        else if (body && body.status !== ApiService.statusCode.OK)
            toast.error(body.message);
        else
            navigate(UrlService.urls.unexpectedError);
    };

    const fetchUser = async () => {
        const { body, error } = await ApiService.getProfile();
        if (body && body.status === ApiService.statusCode.OK) {
            setUser(body.data);
            fetchBooksForAdmin(body.data.username);
        }
        else if (body && body.status !== ApiService.statusCode.OK)
            toast.error(body.message);
        else
            navigate(UrlService.urls.unexpectedError);
    };

    const fetchBooksForAdmin = async (adminUsername) => {
        if (!adminUsername && user)
            adminUsername = user.username;
        const { body } = await ApiService.searchBooks({ admin: adminUsername });
        if (body && body.status === ApiService.statusCode.OK)
            setBooks(body.data.list.map(book => ({ ...book, finalPrice: book.price })));
        else if (body && body.status !== ApiService.statusCode.OK)
            toast.error(body.message);
        else
            navigate(UrlService.urls.unexpectedError);
    };

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
            <AddAuthorModal onSubmit={fetchAuthors} isOpen={addAuthorModalOpen} onClose={() => setAddAuthorModalOpen(false)} />
            <AddBookModal onSubmit={fetchBooksForAdmin} isOpen={addBookModalOpen} onClose={() => setAddBookModalOpen(false)} />

            <section className="shadow rounded-3 p-3 mb-5 h-100 d-flex justify-content-between align-items-center">
                <div>
                    <p className="text-nowrap overflow-auto">
                        <img src={ProfileIcon} alt="user-icon" /> {user?.username}
                    </p>
                    <p className="text-nowrap overflow-auto mb-0">
                        <img src={UserEmailIcon} alt="user-icon" /> {user?.email}
                    </p>
                </div>
                <div>
                    <button onClick={onLogout} className="btn btn-sm bg-gray border-0 px-4 bg-gray">logout
                    </button>
                </div>
            </section>

            <section>
                <div className="d-flex justify-content-center mb-5">
                    <button onClick={() => setAddAuthorModalOpen(true)} className="btn btn-lg green-btn mx-4">Add Author</button>
                    <button onClick={() => setAddBookModalOpen(true)} className="btn btn-lg green-btn mx-4">Add Book</button>
                </div>

                <div className="shadow rounded-3 p-3 mb-4">
                    <p className="fs-3 fw-bold d-flex align-items-center">
                        <img className="me-2" src={BooksIcon} alt="books-icon" /> Books
                    </p>
                    <BookList bookList={books} />
                </div>

                <div className="shadow rounded-3 p-3">
                    <p className="fs-3 fw-bold d-flex align-items-center">
                        <img className="me-2" src={AuthorsIcon} alt="books-icon" /> Authors
                    </p>
                    <AuthorList authorList={authors} />
                </div>
            </section>
        </main>
    );
}

export default AdminPanel;
