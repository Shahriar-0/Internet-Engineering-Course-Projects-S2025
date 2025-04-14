import { Link } from "react-router-dom";
import UrlService from "services/UrlService";

const BookCover = ({ cover }) => {
    const formattedTitle = cover.replace(/\s+/g, "%20");
    // TODO: fetch image
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

export default BookCover;