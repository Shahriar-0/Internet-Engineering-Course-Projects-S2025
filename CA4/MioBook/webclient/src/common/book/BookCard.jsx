import defaultBookCardImage from "assets/images/books/book-card-image.svg";
import AddCart from "./AddCart";
import Rating from "common/rating/Rating";
import BookCardCover from "./BookCardCover";
import BookTitle from "./BookTitle";
import AuthorName from "common/author/AuthorName";

const BookCard = ({ title, author, price, averageRating, bookImage = defaultBookCardImage }) => {
    return (
        <div className="card text-center shadow rounded-4 bg-khaki">
            <BookCardCover cover={bookImage} title={title} />
            <div className="card-body">
                <BookTitle title={title} />
                <AuthorName author={author} />
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