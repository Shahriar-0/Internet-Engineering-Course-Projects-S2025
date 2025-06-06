import { Link } from "react-router-dom";
import UrlService from "services/UrlService";

const BookCardCover = ({ cover, title }) => {
    const formattedTitle = title.replace(/\s+/g, "%20");
    return (
        <Link to={`${UrlService.urls.books}/${formattedTitle}`} className="text-decoration-none">
            <img
                className="card-img-top rounded-top-4"
                src={cover}
                alt="book-image"
            />
        </Link>
    );
};

export default BookCardCover;