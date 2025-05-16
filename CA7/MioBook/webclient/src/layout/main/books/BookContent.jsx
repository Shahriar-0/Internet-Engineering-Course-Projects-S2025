import { useNavigate, useParams } from "react-router-dom";
import BookContentIcon from "assets/icons/book-content-icon.svg"
import AuthorName from "common/author/AuthorName";
import { useEffect, useState } from "react";
import ApiService from "services/ApiService";
import { toast } from "react-toastify";
import UrlService from "services/UrlService";

const BookContent = () => {
    const { title } = useParams();
    const [book, setBook] = useState(null);

    const navigate = useNavigate();

    useEffect(() => {
        fetchBook();
    }, []);

    const fetchBook = async () => {
        const { body, error } = await ApiService.getBookContent(title);
        if (body && body.status === ApiService.statusCode.OK)
            setBook(body.data);
        else if (body && body.status !== ApiService.statusCode.OK)
            toast.error(body.message);
        else
            navigate(UrlService.urls.unexpectedError);
    }

    return (
        <main className="container p-4 justify-content-center align-items-center">
            <div className="p-5 shadow rounded-4 d-flex justify-content-between align-items-center mb-4">
                <h3 className="fw-bold fs-2 d-flex">
                    <img className="me-2" src={BookContentIcon} alt="book-content-icon" />
                    {book?.title}
                </h3>
                <span className="d-flex">By <AuthorName className={"ms-1"} author={book?.authorName} /></span>
            </div>
            <div className="shadow rounded-4 p-5 justified-text">
                {book?.content}
            </div>
        </main>
    );
};

export default BookContent;