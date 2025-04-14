import { useLocation, useNavigate } from "react-router-dom";
import { useEffect, useCallback } from "react";
import UrlService from "./UrlService";
import AuthenticationService from "./AuthenticationService";

const UrlAccessControl = () => {
    const location = useLocation();
    const navigate = useNavigate();

    const checkAccess = useCallback(() => {
        if (!AuthenticationService.isAnyUserLoggedIn()) {
            if (!UrlService.hasDefaultAccess(location.pathname)) {
                navigate(UrlService.urls.accessDenied);
            }
        }
        else {
            // Define logic for authenticated users
        }
    }, [location.pathname, navigate]);

    useEffect(() => {
        if (!UrlService.isAvailable(location.pathname))
            navigate(UrlService.urls.notFound);
        else
            checkAccess();
    }, [location.pathname, navigate, checkAccess]);
}

export default UrlAccessControl;