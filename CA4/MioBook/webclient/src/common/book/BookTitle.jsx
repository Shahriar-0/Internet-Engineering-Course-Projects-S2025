import { Link } from "react-router-dom";
import UrlService from "services/UrlService";

const BookTitle = ({ title }) => {
    const formattedTitle = title.replace(/\s+/g, "%20");

    return (
        <Link to={`${UrlService.urls.books}/${formattedTitle}`} className="text-decoration-none">
            <p className="card-title fw-bold text-dark">{title}</p>
        </Link>
    );
};

export default BookTitle;