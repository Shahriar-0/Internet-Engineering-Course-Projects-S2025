import {useState} from "react";

const PasswordInput = ({divClassName, inputClassName, onChange}) => {
    const [isVisible, setIsVisible] = useState(false);


    const slashEyeIcon = (
        <i className="fa fa-eye-slash pointer position-absolute top-50 end-0 translate-middle-y me-3"
           onClick={() => setIsVisible(true)}></i>
    );

    const eyeIcon = (
        <i className="fa fa-eye pointer position-absolute top-50 end-0 translate-middle-y me-3"
           onClick={() => setIsVisible(false)}></i>
    );

    return (
        <div className={"position-relative " + divClassName}>
            <input type={ isVisible ? "text" : "password"} placeholder="Password" className={inputClassName}
                   onChange={onChange}/>
            {isVisible ? eyeIcon : slashEyeIcon}
        </div>
    );
}

export default PasswordInput;
