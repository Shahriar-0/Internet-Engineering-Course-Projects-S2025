import bookCardImage from "assets/images/books/book-card-image.svg";
import stars from "assets/icons/stars.svg";

const BookCard = ({bookTitle, authorName, price, rating, bookImage = bookCardImage}) => {
    return (
        <div className="card text-center shadow rounded-4 bg-khaki">
            <img className="card-img-top rounded-top-4" src={bookImage} alt="book-image"/>
            <div className="card-body">
                <p className="card-title fw-bold">{bookTitle}</p>
                <p className="card-text">{authorName}</p>
                <div className="d-flex justify-content-between mb-2">
                    <img src={stars} alt="stars-rate"/>
                    <span>${price}</span>
                </div>
                <button className="btn border-1 fw-bold green-btn w-100">Add to Cart</button>
            </div>
        </div>
    );
}

export default BookCard;