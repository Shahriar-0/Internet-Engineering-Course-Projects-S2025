import {useLocation, useNavigate} from "react-router-dom";
import {useEffect} from "react";
import UrlService from "./UrlService";
import AuthenticationService from "./AuthenticationService";

const UrlAccessControl = () => {
    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        if (!UrlService.isAvailable(location.pathname))
            navigate(UrlService.urls.notFound);

        if(!AuthenticationService.isAnyUserLoggedIn())
            checkDefaultAccess();

        // TODO: add check access for a user
    }, [location]);

    const checkDefaultAccess = () => {
        if (!UrlService.hasDefaultAccess(location.pathname)) {
            navigate(UrlService.urls.accessDenied);
        }
    }
}

export default UrlAccessControl;