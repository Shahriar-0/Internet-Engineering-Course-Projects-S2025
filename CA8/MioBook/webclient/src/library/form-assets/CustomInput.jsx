const CustomInput = ({ error, className, errorClassName, ...props }) => {
    return (
        <>
            <input className={`${className} ${error ? "danger-shadow border-danger" : ""}`} {...props} />
            {error && <p className={`${errorClassName} text-danger text-start`}>{error}</p>}
        </>
    );
}

export default CustomInput;
