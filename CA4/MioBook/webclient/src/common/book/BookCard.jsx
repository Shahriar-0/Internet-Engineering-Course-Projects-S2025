import defaultBookCardImage from "assets/images/books/book-card-image.svg";
import AddCartModal from "./add-cart-modal/AddCartModal";
import Rating from "common/rating/Rating";
import BookCardCover from "./BookCardCover";
import BookTitle from "./BookTitle";
import AuthorName from "common/author/AuthorName";
import { useState } from "react";

const BookCard = ({ title, author, price, averageRating, bookImage = defaultBookCardImage }) => {
    const [addCartModalOpen, setAddCartModalOpen] = useState(false);

    return (
        <>
            <AddCartModal title={title} price={price} isOpen={addCartModalOpen} onClose={() => setAddCartModalOpen(false)} />
            <div className="card text-center shadow rounded-4 bg-khaki">
                <BookCardCover cover={bookImage} title={title} />
                <div className="card-body">
                    <BookTitle title={title} />
                    <AuthorName author={author} />
                    <div className="d-flex justify-content-between mb-2">
                        <Rating rating={averageRating} />
                        <span>${price}</span>
                    </div>
                    <button onClick={() => setAddCartModalOpen(true)} class="btn green-btn w-100">Add to cart</button>
                </div>
            </div>
        </>
    );
};

export default BookCard;