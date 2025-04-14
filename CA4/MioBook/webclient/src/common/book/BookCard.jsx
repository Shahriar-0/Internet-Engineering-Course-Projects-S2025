import defaultBookCardImage from "assets/images/books/book-card-image.svg";
import filledStar from "assets/icons/filled-star.svg";
import emptyStar from "assets/icons/empty-star.svg";
import halfStar from "assets/icons/half-star.svg";
import quarterStar from "assets/icons/one-fourth-filled-star.svg";
import threeQuarterStar from "assets/icons/three-fourth-filled-star.svg";
import AddCart from "./AddCart";
import { Link } from "react-router-dom";
import UrlService from "services/UrlService";

const BookCard = ({ title, author, price, averageRating, bookImage = defaultBookCardImage }) => {
    const renderStars = () => {
        const stars = [];
        for (let i = 1; i <= 5; i++) {
            if (averageRating >= i)
                stars.push(<img key={i} src={filledStar} alt="filled-star" />);
            else if (averageRating >= i - 0.75)
                stars.push(<img key={i} src={threeQuarterStar} alt="three-fourth-filled-star" />);
            else if (averageRating >= i - 0.5)
                stars.push(<img key={i} src={halfStar} alt="half-star" />);
            else if (averageRating >= i - 0.25)
                stars.push(<img key={i} src={quarterStar} alt="one-fourth-filled-star" />);
            else
                stars.push(<img key={i} src={emptyStar} alt="empty-star" />);
        }
        return stars;
    };

    const formattedTitle = title.replace(/\s+/g, "%20");
    const formattedAuthor = author.replace(/\s+/g, "%20");

    return (
        <div className="card text-center shadow rounded-4 bg-khaki">
            <Link to={`${UrlService.urls.books}/${formattedTitle}`} className="text-decoration-none">
                <img
                    className="card-img-top rounded-top-4"
                    src={bookImage}
                    alt="book-image"
                />
            </Link>
            <div className="card-body">
                <Link to={`${UrlService.urls.books}/${formattedTitle}`} className="text-decoration-none">
                    <p className="card-title fw-bold text-dark">{title}</p>
                </Link>
                <Link to={`${UrlService.urls.books}/${formattedAuthor}`} className="text-decoration-none">
                    <p className="card-text text-dark">{author}</p>
                </Link>
                <div className="d-flex justify-content-between mb-2">
                    <div className="d-flex">{renderStars()}</div>
                    <span>${price}</span>
                </div>
                <AddCart />
            </div>
        </div>
    );
};

export default BookCard;