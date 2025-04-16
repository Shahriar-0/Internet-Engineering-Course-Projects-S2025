import {useState} from "react";

const SortByPicker = ({onChange, className}) => {
    const sortBy = Object.freeze({
        rating: "rating",
        review: "review"
    })

    const activeStyle = "flex-grow-1 btn border-3 fw-bold position-relative bg-khaki text-green border-green";
    const inactiveStyle = "flex-grow-1 btn border-3 fw-bold position-relative bg-gray";

    const [sortByValue, setSortByValue] = useState(sortBy.rating);

    const setSortByRating = () => {
        onChange(sortBy.rating);
        setSortByValue(sortBy.rating);
    }

    const setSortByReview = () => {
        onChange(sortBy.review);
        setSortByValue(sortBy.review);
    }

    return (
        <div className={`d-flex ${className}`}>
                <button className={`me-1 ${sortByValue === sortBy.rating ? activeStyle : inactiveStyle}`}
                        onClick={setSortByRating}>
                    Rating
                </button>

                <button className={`ms-1 ${sortByValue === sortBy.review ? activeStyle : inactiveStyle}`}
                        onClick={setSortByReview}>
                    Reviews
                </button>
        </div>
    );
}

export default SortByPicker;
