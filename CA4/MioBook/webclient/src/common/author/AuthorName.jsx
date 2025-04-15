import { Link } from "react-router-dom";
import UrlService from "services/UrlService";

const AuthorName = ({ author }) => {
    const formattedAuthor = author?.replace(/\s+/g, "%20");

    return (
        <Link to={`${UrlService.urls.authors}/${formattedAuthor}`} className="text-decoration-none">
            <p className="card-title text-dark">{author}</p>
        </Link>
    );
};

export default AuthorName;