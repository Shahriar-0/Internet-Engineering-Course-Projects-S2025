import {useLocation, useNavigate, useSearchParams} from "react-router-dom";
import UrlService from "./UrlService";
import AuthenticationService from "./AuthenticationService";
import {useEffect} from "react";

const SetLoginUser = () => {
    // const location = useLocation();
    const navigate = useNavigate();
    const [searchParams] = useSearchParams();

    useEffect(() => {
        AuthenticationService.login(searchParams.get("username"), searchParams.get("password"));
        navigate(UrlService.urls.home)
    }, []);
}

export default SetLoginUser;