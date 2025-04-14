import {Link} from "react-router-dom";
import UrlService from "services/UrlService";
import NotFoundImage from "assets/images/errors/not-found.jpg";

const NotFound = () => {
    return (
        <main>
            <div className="container text-center">
                <img className="w-50 h-auto rounded-4" src={NotFoundImage} alt="404"/> <br/>
                <Link to={UrlService.urls.home} className="btn btn-lg  fw-bold border-2 green-btn mt-4">Home Page</Link>
            </div>
        </main>
    );
}

export default NotFound;