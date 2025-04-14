import defaultBookCardImage from "assets/images/books/book-card-image.svg";
import AddCart from "./AddCart";
import Rating from "common/rating/Rating";
import BookCover from "./BookCover";
import BookTitle from "./BookTitle";
import AuthorName from "common/author/AuthorName";

const BookCard = ({ title, author, price, averageRating, bookImage = defaultBookCardImage }) => {
    return (
        <div className="card text-center shadow rounded-4 bg-khaki">
            <BookCover cover={bookImage} />
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