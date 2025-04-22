import BookCard from "./BookCard";
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import ApiService from "services/ApiService";
import UrlService from "services/UrlService";
import { transformBook, getBookCover } from "./BookCardLogic";

const BookCardContainer = ({ bookList = null, url }) => {

    const [books, setBooks] = useState([]);
    const [errorMessage, setErrorMessage] = useState(null);
    // TODO: add loading

    const navigate = useNavigate();

    useEffect(() => {
        if (bookList == null && url != null) {
            const fetchBooks = async () => {
                const { body, error } = await ApiService.getBooks(url);
                if (error) {
                    setErrorMessage(error);
                    navigate(UrlService.urls.unexpectedError);
                }
                else if (body && body.status === ApiService.statusCode.OK) {
                    const transformedBooks = body.data.list.map((book) => transformBook(book));
                    setBooks(transformedBooks);
                    setErrorMessage(null);
                }
            }

            fetchBooks();
        }
        else
            setBooks(bookList);
    }, [bookList, navigate, url]);

    return (
        <div className="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 row-cols-xl-5">
            {errorMessage && (
                <div className="alert alert-danger" role="alert">
                    {errorMessage}
                </div>
            )}
            {
                books?.map((book, index) => (
                    <div className="mb-2" key={index}>
                        <BookCard key={index}  {...book} />
                    </div>
                ))
            }
        </div>
    );
}

export default BookCardContainer;
