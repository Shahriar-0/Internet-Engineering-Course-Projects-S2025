import {Link} from "react-router-dom";
import UrlService from "services/UrlService";
import UnexpectedErrorImage from "assets/images/errors/unexpected-error.png";

const UnexpectedError = () => {
    return (
        <main>
            <div className="container text-center">
                <img className="w-50 h-auto rounded-4" src={UnexpectedErrorImage} alt="400"/> <br/>
                <Link to={UrlService.urls.home} className="btn btn-lg  fw-bold border-2 green-btn mt-4">Home Page</Link>
            </div>
        </main>
    );
}

export default UnexpectedError;