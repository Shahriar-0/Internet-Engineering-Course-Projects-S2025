import { Link } from "react-router-dom";
import UrlService from "services/UrlService";
import BookCoverImg from "assets/images/books/book-img.svg"
import Rating from "common/rating/Rating";

const BookCover = ({ title, rating, cover }) => {
    // Difference between this and the card one is mostly size so i don't know if we even need this
    const formattedTitle = title?.replace(/\s+/g, "%20");
    // TODO: fetch image
    return (
        <div class="col-12 col-md-5 col-lg-4 d-flex justify-content-center mb-4 mb-md-0">
            <div>
                <p class="fw-bold fs-3 d-md-none mb-1">{title}</p>
                <div class="mb-4 d-md-none">
                    <Rating rating={rating} />
                </div>
                <img src={cover || BookCoverImg} alt="book-image" />
            </div>
        </div>
    );
};

export default BookCover;