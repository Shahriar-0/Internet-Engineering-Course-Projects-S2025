import emptyStar from "assets/icons/empty-star.svg";
import filledStar from "assets/icons/filled-star.svg";
import styles from "./AddReviewModal.module.css";
import { useState } from "react";

const RatingPicker = ({ onChange }) => {
    const [rating, setRating] = useState(0);

    const setRatingHandler = (index) => {
        setRating(index);
        if (onChange) onChange(index);
    }

    const getStarIcon = (index) => {
        if (index <= rating)
            return filledStar;
        else
            return emptyStar;
    }

    return (
        <>
            <p>Rating</p>
            <div className="d-flex justify-content-between mb-1">
                <button onClick={() => setRatingHandler(1)} className="btn btn-lg border-0 bg-gray">
                    <img className={styles["star-img"]} src={getStarIcon(1)} alt="empty-star" />
                </button>
                <button onClick={() => setRatingHandler(2)} className="btn btn-lg border-0 bg-gray">
                    <img className={styles["star-img"]} src={getStarIcon(2)} alt="empty-star" />
                </button>
                <button onClick={() => setRatingHandler(3)} className="btn btn-lg border-0 bg-gray">
                    <img className={styles["star-img"]} src={getStarIcon(3)} alt="empty-star" />
                </button>
                <button onClick={() => setRatingHandler(4)} className="btn btn-lg border-0 bg-gray">
                    <img className={styles["star-img"]} src={getStarIcon(4)} alt="empty-star" />
                </button>
                <button onClick={() => setRatingHandler(5)} className="btn btn-lg border-0 bg-gray">
                    <img className={styles["star-img"]} src={getStarIcon(5)} alt="empty-star" />
                </button>
            </div>
            <p className="text-secondary fs-7">Tap to Rate</p>
        </>
    );
}

export default RatingPicker;
