import Rating from "common/rating/Rating";

const Review = ({ user, review, rating, date }) => {
    const formattedDate = new Date(date).toLocaleDateString("en-US", {
        year: "numeric",
        month: "long",
        day: "numeric"
    })

    return (
        <div class="row align-items-center p-2 mb-4 shadow-sm rounded-1 border-1 border-bottom border-secondary">
            <div class="d-none d-md-flex rounded-circle bg-gray p-3 align-self-start profile-img-container pointer">{user.charAt(0).toUpperCase()}</div>
            <div class="row col-12 col-md-9">
                <div class="col-3 d-md-none rounded-circle bg-gray p-3 align-self-start profile-img-container pointer">TW</div>
                <div class="col-8">
                    <p class="mb-0 fw-bold">{user}</p>
                    <div class="d-md-none mb-3">
                        <Rating rating={rating} />
                        <span class="align-middle fs-8 text-muted">{formattedDate}</span>
                    </div>
                </div>
                <p class="mb-0 fs-7">{review}</p>
            </div>
            <div class="d-none d-md-inline col-0 col-md-2 px-0 ms-auto text-end">
                <Rating className="justify-content-end" rating={rating} />
                <span class="align-middle fs-8 text-muted">{formattedDate}</span>
            </div>
        </div>
    );
};

export default Review;