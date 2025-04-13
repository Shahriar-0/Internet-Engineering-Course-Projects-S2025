import React from 'react';
import styles from "./SpinnerButton.module.css";

const SpinnerButton = ({ loading, className, disabled, spinnerClassName, children, ...props }) => (
    <button
        className={className}
        disabled={loading || disabled}
        {...props}
    >
        {loading ? <span className={styles.spinner + " " + spinnerClassName}></span> : children}
    </button>
);

export default SpinnerButton;
