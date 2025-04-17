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
import AddCart from "common/book/AddCart";
import AddReviewImg from "assets/icons/add-review-icon.svg";
import AddReviewModal from "./add-review-modal/AddReviewModal";

const Book = () => {
    const { title } = useParams();

    const [book, setBook] = useState(null);
    const [reviews, setReviews] = useState(null);
    const [addReviewModalOpen, setAddReviewModalOpen] = useState(false);

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
        if (body && body.status === ApiService.statusCode.OK)
            setReviews(body.data);
        else if (body && body.status !== ApiService.statusCode.OK)
            toast.error(body.message);
        else
            navigate(UrlService.urls.unexpectedError);
    }

    const reviewSubmit = () => {
        fetchReviews();
    }


    return (
        <main class="container">
            <section class="row rounded-4 shadow-lg border-1 border-bottom border-secondary py-4 mx-2 mb-5">
                <AddReviewModal title={book?.title} isOpen={addReviewModalOpen}
                                onSubmit={reviewSubmit} onClose={() => setAddReviewModalOpen(false)} />
                <HomeEntityCover title={book?.title} rating={book?.averageRating} cover={book?.cover || BookCoverImg} />

                <div class="col-12 col-md-7 col-lg-8 d-flex flex-column">
                    <p class="fw-bold fs-3 d-none d-md-block">{book?.title}</p>
                    <div class="mb-4 d-none d-md-flex align-items-end">
                        <Rating rating={book?.averageRating} />
                        <span class="align-middle ms-2 fs-7">{book?.averageRating?.toFixed(1)}</span> {/* FIXME: fix alignment*/}
                    </div>

                    <div class="row mb-auto">
                        <div class="col-4 col-lg-3">
                            <p class="mb-1 fs-7 text-secondary">Author</p>
                            <AuthorName author={book?.author} />
                        </div>
                        <div class="col-4 col-lg-3">
                            <p class="mb-1 fs-7 text-secondary">Publisher</p>
                            <p class="text-truncate">{book?.publisher}</p>
                        </div>
                        <div class="col-4 col-lg-3">
                            <p class="mb-1 fs-7 text-secondary">Year</p>
                            <p class="text-truncate">{book?.year}</p>
                        </div>
                        <div class="col-12">
                            <p class="mb-1 fs-7 text-secondary">About</p>
                            <p class="about-text overflow-auto">{book?.synopsis}</p>
                        </div>
                    </div>

                    <div>
                        <p class="fw-bold fs-5">${book?.price}</p>
                        <AddCart book={book} /> {/* FIXME: fix the size, previous version below */}
                        {/* <button class="btn px-4 fw-bold mt-auto green-btn">Add to Cart</button> */}

                    </div>
                </div>
            </section>

            <section class="row rounded-4 shadow-lg border-1 border-bottom border-secondary p-4 mx-2 mb-5 d-flex flex-column">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <p class="align-middle mb-0">Reviews <span class="text-muted">({reviews?.list.length})</span></p>
                    <button onClick={() => setAddReviewModalOpen(true)} className="btn border-0 bg-gray px-3 bg-gray">
                        Add reviews
                        <img className="ms-2" src={AddReviewImg} alt="add-review"/>
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

                <ul class="pagination justify-content-center">
                    <li class="px-3 py-2 mx-1 rounded-3 pagination-item">&lt;</li>
                    <li class="px-3 py-2 mx-1 rounded-3 pagination-item active">1</li>
                    <li class="px-3 py-2 mx-1 rounded-3 pagination-item">2</li>
                    <li class="px-3 py-2 mx-1 rounded-3 pagination-item">3</li>
                    <li class="d-sm-none px-3 py-2 mx-1 rounded-3 pagination-item">...</li>
                    <li class="d-none d-sm-block px-3 py-2 mx-1 rounded-3 pagination-item">4</li>
                    <li class="d-none d-sm-block px-3 py-2 mx-1 rounded-3 pagination-item">5</li>
                    <li class="px-3 py-2 mx-1 rounded-3 pagination-item">&gt;</li>
                </ul>
            </section>
        </main>
    );
};

export default Book;