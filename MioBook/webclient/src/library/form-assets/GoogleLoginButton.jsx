import React from "react";
import GoogleIcon from "assets/icons/google.svg";
import styles from "./GoogleLoginButton.module.css";

const GoogleLoginButton = ({ onClick, className = "" }) => (
    <button
        type="button"
        className={`${styles["google-login-btn"]} ${className}`}
        onClick={onClick}
    >
        <img src={GoogleIcon} alt="Google" className={`${styles["google-icon"]} me-2`} />
        <span>Sign in with Google</span>
    </button>
);

export default GoogleLoginButton;
