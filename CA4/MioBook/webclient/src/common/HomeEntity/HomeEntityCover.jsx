import { Link } from "react-router-dom";
import UrlService from "services/UrlService";
import Rating from "common/rating/Rating";

const HomeEntityCover = ({ title, cover, rating = null }) => {
    // TODO: fetch image
    return (
        <div class="col-12 col-md-5 col-lg-4 d-flex justify-content-center mb-4 mb-md-0">
            <div>
                <p class="fw-bold fs-3 d-md-none mb-1">{title}</p>
                {rating !== null && (
                    <div class="mb-4 d-md-none">
                        <Rating rating={rating} />
                    </div>
                )}
                <img src={cover} alt="cover-image" />
            </div>
        </div>
    );
};

export default HomeEntityCover;