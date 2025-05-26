import {useLocation, useNavigate, useSearchParams} from "react-router-dom";
import UrlService from "./UrlService";
import AuthenticationService from "./AuthenticationService";
import {useEffect} from "react";

const SetLoginUser = () => {
    // const location = useLocation();
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();

    useEffect(() => {
        if (AuthenticationService.isAnyUserLoggedIn()) {
            console.log("saladmd")
            AuthenticationService.setLoggedInUser(searchParams.get("username"), searchParams.get("role"));
        }
        console.log("saladmd")
        navigate(UrlService.urls.home)
    }, []);
}

export default SetLoginUser;
