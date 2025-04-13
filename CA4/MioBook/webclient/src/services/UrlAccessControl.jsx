import {useLocation, useNavigate} from "react-router-dom";
import {useEffect} from "react";
import UrlService from "./UrlService";

const UrlAccessControl = () => {
    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        checkDefaultAccess();
    }, [location]);

    const checkDefaultAccess = () => {
        if (!UrlService.hasDefaultAccess(location.pathname)) {
            navigate(UrlService.urls.signIn);
        }
    }
}

export default UrlAccessControl;