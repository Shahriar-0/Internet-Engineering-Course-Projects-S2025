import AddReviewImg from "assets/icons/add-review-icon.svg";

const AddReview = () => {
    return (
        <button class="btn border-0 bg-gray px-3 bg-gray">
            Add reviews
            <img class="ms-2" src={AddReviewImg} alt="add-review" />
        </button>
    );
};

export default AddReview;