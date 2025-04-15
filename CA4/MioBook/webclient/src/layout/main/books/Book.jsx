import AddReview from "./AddReview";
import Review from "./Review";

const Book = () => {
    return (
        <main class="container">
            <section class="row rounded-4 shadow-lg border-1 border-bottom border-secondary py-4 mx-2 mb-5">
                <div class="col-12 col-md-5 col-lg-4 d-flex justify-content-center mb-4 mb-md-0">
                    <div>
                        <p class="fw-bold fs-3 d-md-none mb-1">Book Title</p>
                        <div class="mb-4 d-md-none">
                            <img src="../../assets/icons/stars.svg" alt="stars-rate" />
                            <span class="align-middle ms-2">4.0</span>
                        </div>
                        <img src="../../assets/images/book/book-img.svg" alt="book-image" />
                    </div>
                </div>

                <div class="col-12 col-md-7 col-lg-8 d-flex flex-column">
                    <p class="fw-bold fs-3 d-none d-md-block">Book Title</p>
                    <div class="mb-4 d-none d-md-block">
                        <img src="../../assets/icons/stars.svg" alt="stars-rate" />
                        <span class="align-middle ms-2">4.0</span>
                    </div>

                    <div class="row mb-auto">
                        <div class="col-4 col-lg-3">
                            <p class="mb-1 fs-7 text-secondary">Author</p>
                            <p class="text-truncate">Author McName</p>
                        </div>
                        <div class="col-4 col-lg-3">
                            <p class="mb-1 fs-7 text-secondary">Publisher</p>
                            <p class="text-truncate">The Publisher</p>
                        </div>
                        <div class="col-4 col-lg-3">
                            <p class="mb-1 fs-7 text-secondary">Year</p>
                            <p class="text-truncate">2025</p>
                        </div>
                        <div class="col-12">
                            <p class="mb-1 fs-7 text-secondary">About</p>
                            <p class="about-text overflow-auto">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
                        </div>
                    </div>

                    <div>
                        <p class="fw-bold fs-5">$18.99</p>
                        <button class="btn px-4 fw-bold mt-auto green-btn">Add to Cart</button>
                    </div>
                </div>
            </section>

            <section class="row rounded-4 shadow-lg border-1 border-bottom border-secondary p-4 mx-2 mb-5 d-flex flex-column">
                <div class="d-flex justify-content-between align-items-center mb-3">
                    <p class="align-middle mb-0">Reviews <span class="text-muted">130</span></p>
                    <AddReview />
                </div>

                <Review user="The Person" review="I bought it 3 weeks ago and now come back just to say “Awesome”. I really enjoyed it." rating={4} date="February 20, 2025" />
                <Review user="The Person" review="I bought it 3 weeks ago and now come back just to say “Awesome”. I really enjoyed it." rating={4} date="February 20, 2025" />
                <Review user="The Person" review="I bought it 3 weeks ago and now come back just to say “Awesome”. I really enjoyed it." rating={4} date="February 20, 2025" />
                <Review user="The Person" review="I bought it 3 weeks ago and now come back just to say “Awesome”. I really enjoyed it." rating={4} date="February 20, 2025" />

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