import defaultBookCardImage from "assets/images/books/book-card-image.svg";
import stars from "assets/icons/stars.svg";

const BookCard = ({ title, author, price, rating, bookImage = defaultBookCardImage }) => {
    return (
        <div className="card text-center shadow rounded-4 bg-khaki">
            <img className="card-img-top rounded-top-4" src={bookImage} alt="book-image" />
            <div className="card-body">
                <p className="card-title fw-bold">{title}</p>
                <p className="card-text">{author}</p>
                <div className="d-flex justify-content-between mb-2">
                    <img src={stars} alt="stars-rate" />
                    <span>${price}</span>
                </div>
                <button className="btn border-1 fw-bold green-btn w-100">Add to Cart</button>
            </div>
        </div>
    );
}

export default BookCard;