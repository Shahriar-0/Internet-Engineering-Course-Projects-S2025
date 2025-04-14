import { Link } from "react-router-dom";
import UrlService from "services/UrlService";
import NotFoundImage from "assets/images/errors/not-found.jpg";
import { useNavigate } from "react-router-dom";

const NotFound = () => {
    const navigate = useNavigate();

    return (
        <main>
            <div className="container text-center">
                <img className="w-50 h-auto rounded-4" src={NotFoundImage} alt="404" /> <br />
                <Link to={UrlService.urls.home} className="btn btn-lg  fw-bold border-2 green-btn mt-4">Home Page</Link>
                <button onClick={() => navigate(-1)} className="btn btn-lg fw-bold border-2 green-btn mt-4 ms-2">Go Back</button>
            </div>
        </main>
    );
}

export default NotFound;