import defaultBookCardImage from "assets/images/books/book-card-image.svg";
import AddCart from "./AddCart";
import { Link } from "react-router-dom";
import UrlService from "services/UrlService";
import Rating from "common/rating/Rating";

const BookCard = ({ title, author, price, averageRating, bookImage = defaultBookCardImage }) => {

    const formattedTitle = title.replace(/\s+/g, "%20");
    const formattedAuthor = author.replace(/\s+/g, "%20");

    return (
        <div className="card text-center shadow rounded-4 bg-khaki">
            <Link to={`${UrlService.urls.books}/${formattedTitle}`} className="text-decoration-none">
                <img
                    className="card-img-top rounded-top-4"
                    src={bookImage}
                    alt="book-image"
                />
            </Link>
            <div className="card-body">
                <Link to={`${UrlService.urls.books}/${formattedTitle}`} className="text-decoration-none">
                    <p className="card-title fw-bold text-dark">{title}</p>
                </Link>
                <Link to={`${UrlService.urls.books}/${formattedAuthor}`} className="text-decoration-none">
                    <p className="card-text text-dark">{author}</p>
                </Link>
                <div className="d-flex justify-content-between mb-2">
                    <Rating rating={averageRating} />
                    <span>${price}</span>
                </div>
                <AddCart />
            </div>
        </div>
    );
};

export default BookCard;