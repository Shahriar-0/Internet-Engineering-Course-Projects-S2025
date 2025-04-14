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

        checkAccess();
    }, [location]);

    const checkAccess = () => {
        if (!AuthenticationService.isAnyUserLoggedIn())
            if (!UrlService.hasDefaultAccess(location.pathname))
                navigate(UrlService.urls.accessDenied);

        else {
            //? maybe define a list of protected urls which are not available for all users (other than things for admin and customer)
        }
    }
}

export default UrlAccessControl;