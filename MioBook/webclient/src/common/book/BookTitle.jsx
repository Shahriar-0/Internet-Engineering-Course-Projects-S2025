import { Link } from "react-router-dom";
import UrlService from "services/UrlService";

const BookTitle = ({ title, isBold = true }) => {
    
    const formattedTitle = encodeURIComponent(title);

    return (
        <Link to={`${UrlService.urls.books}/${formattedTitle}`} className="text-decoration-none">
            <p className={`card-title ${isBold ? "fw-bold" : ""} text-dark`}>{title}</p>
        </Link>
    );
};

export default BookTitle;