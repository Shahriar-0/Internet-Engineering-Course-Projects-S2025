import {Link, useNavigate} from "react-router-dom";
import styles from "./ProfileMenu.module.css";
import profileIcon from "assets/icons/profile-icon.svg";
import cartIcon from "assets/icons/cart-icon.svg";
import logoutIcon from "assets/icons/logout-icon.svg";
import historyIcon from "assets/icons/history-icon.svg";
import myBooksIcon from "assets/icons/my-books-icon.svg";
import UrlService from "services/UrlService";
import AuthenticationService from "services/AuthenticationService";
import urlService from "services/UrlService";
import ApiService from "services/ApiService";
import {toast} from "react-toastify";
import {useEffect, useRef} from "react";

const ProfileMenu = ({isOpen, username, onClose}) => {
    const navigate = useNavigate();
    const menuRef = useRef(null);


    const close = () => onClose && onClose();

    const onLogout = async () => {
        const body = await AuthenticationService.logout();

        if (!body)
            navigate(urlService.urls.unexpectedError);
        else if (body.status !== ApiService.statusCode.OK)
            toast.error(body.message);
        else
            navigate(urlService.urls.signIn);

        close();
    }

    useEffect(() => {
        const handleClickOutside = (event) => {
            if (menuRef.current && !menuRef.current.contains(event.target)) {
                close();
            }
        };

        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, [onClose]);

    return (
        <div ref={menuRef} className={`${styles["profile-menu"]} ${isOpen ? " d-block" : ""}`}>
            <p className="px-2 pt-2 fw-bold fs-5 text-truncate">{username}</p>
            <hr className="my-2 border-2 opacity-100" />
            <Link onClick={close} className="d-flex text-black hover-gray p-2 rounded align-middle" to={UrlService.urls.profile}>
                <img className="me-2" src={profileIcon} alt="profile-icon" />  Profile
            </Link>
            <Link onClick={close} className="d-flex text-black hover-gray p-2 rounded align-middle" to={UrlService.urls.myBooks}>
                <img className="me-2" src={myBooksIcon} alt="my-books-icon" />  My Books
            </Link>
            <Link onClick={close} className="d-flex text-black hover-gray p-2 rounded align-middle" to={UrlService.urls.cart}>
                <img className="me-2" src={cartIcon} alt="cart-icon" />  Buy Cart
            </Link>
            <Link onClick={close} className="d-flex text-black hover-gray p-2 rounded align-middle" to={UrlService.urls.history}>
                <img className="me-2" src={historyIcon} alt="history-icon" />  Purchase History
            </Link>
            <hr className="my-2" />
            <button onClick={onLogout} className="btn hover-gray w-100 text-start"><img className="me-2" src={logoutIcon}  alt="logout"/>Logout</button>
        </div>
    );
}

export default ProfileMenu;