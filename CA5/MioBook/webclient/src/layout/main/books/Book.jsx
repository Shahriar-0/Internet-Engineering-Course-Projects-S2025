import ApiService from "services/ApiService";
import Review from "./Review";
import { useEffect, useState } from "react";
import { useParams, useNavigate } from 'react-router-dom';
import { toast } from "react-toastify";
import UrlService from "services/UrlService";
import HomeEntityCover from "common/HomeEntity/HomeEntityCover";
import BookCoverImg from "assets/images/books/book-img.svg"
import Rating from "common/rating/Rating";
import AuthorName from "common/author/AuthorName";
import AddCartModal from "common/book/add-cart-modal/AddCartModal";
import AddReviewImg from "assets/icons/add-review-icon.svg";
import AddReviewModal from "./add-review-modal/AddReviewModal";

const Book = () => {
    const { title } = useParams();

    const [book, setBook] = useState(null);
    const [reviews, setReviews] = useState(null);
    const [addReviewModalOpen, setAddReviewModalOpen] = useState(false);
    const [addCartModalOpen, setAddCartModalOpen] = useState(false);
    const [averageRating, setAverageRating] = useState(0); // TODO: remove this, it's just because backend is buggy and doesn't return reviews by default since db

    const navigate = useNavigate();

    useEffect(() => {
        fetchBook();
        fetchReviews();
    }, [navigate, title]);

    const fetchBook = async () => {
        const { body, error } = await ApiService.getBook(title);
        if (body && body.status === ApiService.statusCode.OK)
            setBook(body.data);
        else if (body && body.status !== ApiService.statusCode.OK)
            toast.error(body.message);
        else
            navigate(UrlService.urls.unexpectedError);
    }

    const fetchReviews = async () => {
        const { body, error } = await ApiService.getBookReviews(title);
        if (body && body.status === ApiService.statusCode.OK) {
            setAverageRating(body.data.list.reduce((a, b) => a + b.rating, 0) / body.data.list.length);
            setReviews(body.data);
        }
        else if (body && body.status !== ApiService.statusCode.OK)
            toast.error(body.message);
        else
            navigate(UrlService.urls.unexpectedError);
    }

    const reviewSubmit = () => {
        fetchReviews();
        fetchBook();
    }

    return (
        <main className="container">
            <section className="row rounded-4 shadow-lg border-1 border-bottom border-secondary py-4 mx-2 mb-5">
                <AddReviewModal title={book?.title} isOpen={addReviewModalOpen}
                    onSubmit={reviewSubmit} onClose={() => setAddReviewModalOpen(false)} />
                <HomeEntityCover title={book?.title} rating={averageRating?.toFixed(1)} cover={book?.cover || BookCoverImg} />

                <div className="col-12 col-md-7 col-lg-8 d-flex flex-column">
                    <p className="fw-bold fs-3 d-none d-md-block">{book?.title}</p>
                    <div className="mb-4 d-none d-md-flex align-items-end">
                        <Rating rating={averageRating?.toFixed(1)} />
                        <span className="align-middle ms-2 fs-7">{averageRating?.toFixed(1)}</span> {/* FIXME: fix alignment*/}
                    </div>

                    <div className="row mb-auto">
                        <div className="col-4 col-lg-3">
                            <p className="mb-1 fs-7 text-secondary">Author</p>
                            <AuthorName author={book?.author} />
                        </div>
                        <div className="col-4 col-lg-3">
                            <p className="mb-1 fs-7 text-secondary">Publisher</p>
                            <p className="text-truncate">{book?.publisher}</p>
                        </div>
                        <div className="col-4 col-lg-3">
                            <p className="mb-1 fs-7 text-secondary">Year</p>
                            <p className="text-truncate">{book?.year}</p>
                        </div>
                        <div className="col-12">
                            <p className="mb-1 fs-7 text-secondary">About</p>
                            <p className="about-text overflow-auto">{book?.synopsis}</p>
                        </div>
                    </div>

                    <div>
                        <p className="fw-bold fs-5">${book?.price}</p>
                        <AddCartModal title={book?.title} price={book?.price} isOpen={addCartModalOpen} onClose={() => setAddCartModalOpen(false)} />
                        <button onClick={() => setAddCartModalOpen(true)} className="btn green-btn">Add to cart</button>
                    </div>
                </div>
            </section>

            <section className="row rounded-4 shadow-lg border-1 border-bottom border-secondary p-4 mx-2 mb-5 d-flex flex-column">
                <div className="d-flex justify-content-between align-items-center mb-3">
                    <p className="align-middle mb-0">Reviews <span className="text-muted">({reviews?.list.length})</span></p>
                    <button onClick={() => setAddReviewModalOpen(true)} className="btn border-0 bg-gray px-3 bg-gray">
                        Add reviews
                        <img className="ms-2" src={AddReviewImg} alt="add-review" />
                    </button>
                </div>

                {reviews?.list.slice(0, 4).map((review, index) => (
                    <Review
                        key={index}
                        user={review.customer}
                        review={review.comment}
                        rating={review.rating}
                        date={review.date}
                    />
                ))}
                {/* FIXME: fix star position here */}

                <ul className="pagination justify-content-center">
                    <li className="px-3 py-2 mx-1 rounded-3 pagination-item">&lt;</li>
                    <li className="px-3 py-2 mx-1 rounded-3 pagination-item active">1</li>
                    <li className="px-3 py-2 mx-1 rounded-3 pagination-item">2</li>
                    <li className="px-3 py-2 mx-1 rounded-3 pagination-item">3</li>
                    <li className="d-sm-none px-3 py-2 mx-1 rounded-3 pagination-item">...</li>
                    <li className="d-none d-sm-block px-3 py-2 mx-1 rounded-3 pagination-item">4</li>
                    <li className="d-none d-sm-block px-3 py-2 mx-1 rounded-3 pagination-item">5</li>
                    <li className="px-3 py-2 mx-1 rounded-3 pagination-item">&gt;</li>
                </ul>
            </section>
        </main>
    );
};

export default Book;