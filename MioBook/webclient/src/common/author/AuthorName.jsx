import { Link } from "react-router-dom";
import UrlService from "services/UrlService";

const AuthorName = ({ author, className }) => {
    const formattedAuthor = encodeURIComponent(author);

    return (
        <Link to={`${UrlService.urls.authors}/${formattedAuthor}`} className="text-decoration-none">
            <p className={`card-title text-dark ${className}`}>{author}</p>
        </Link>
    );
};

export default AuthorName;