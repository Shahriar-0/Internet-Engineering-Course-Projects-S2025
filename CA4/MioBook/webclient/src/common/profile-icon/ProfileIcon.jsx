import styles from "./ProfileIcon.module.css";
import {Link} from "react-router-dom";
import AuthenticationService from "services/AuthenticationService";

const ProfileIcon = ({username}) => {

    const createUrl = (username) => {
        const userUrl = "/user/" + username.toLocaleLowerCase();
        return (AuthenticationService.isUserLoggedIn(username)) ? "/profile" : userUrl;
    }

    const getBackgroundColorClass = (username) => {
        return (AuthenticationService.isUserLoggedIn(username)) ? "bg-green" : "bg-gray";
    }

    return (
        <Link to={createUrl(username)}>
            <div className={
                "d-flex align-items-center justify-content-center text-white rounded-circle " +
                getBackgroundColorClass(username) + " " +
                styles["profile-icon-size"]
            }>
                {username.charAt(0).toUpperCase()}
            </div>
        </Link>
    );
}

export default ProfileIcon;
