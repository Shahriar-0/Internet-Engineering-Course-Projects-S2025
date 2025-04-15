import styles from "./ProfileIcon.module.css";
import AuthenticationService from "services/AuthenticationService";

const ProfileIcon = ({ username, onClick }) => {

    const getBackgroundColorClass = (username) => {
        return (username && AuthenticationService.isUserLoggedIn(username)) ? "bg-green" : "bg-gray";
    }

    return (
        <div
            className={
                "d-flex align-items-center justify-content-center text-white rounded-circle pointer " +
                getBackgroundColorClass(username) + " " +
                styles["profile-icon-size"]
            }
            onClick={onClick}
        >
            {username?.charAt(0).toUpperCase()}
        </div>
    );
}

export default ProfileIcon;
