import {Link, useNavigate} from "react-router-dom";
import UrlService from "services/UrlService";
import AccessDeniedImage from "assets/images/errors/access-denied.png";
import ApiService from "services/ApiService";

const AccessDenied = () => {
    const navigate = useNavigate();

    return (
        <main>
            <div className="container text-center">
                <img className="w-50 h-auto rounded-4" src={AccessDeniedImage} alt="403"/> <br/>
                <Link to={UrlService.urls.home} className="btn btn-lg fw-bold border-2 green-btn mt-4">Home Page</Link>
                <button onClick={() => navigate(-1)} className="btn btn-lg fw-bold border-2 green-btn mt-4 ms-2">Go Back</button>
                <Link onClick={() => ApiService.signOut()} to={UrlService.urls.signIn} className="btn btn-lg fw-bold border-2 green-btn mt-4 ms-2">Login as Another Person</Link>
            </div>
        </main>
    );
}

export default AccessDenied;