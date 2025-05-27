import PasswordInput from "library/form-assets/PasswordInput";
import ApiService from "services/ApiService";
import AuthenticationService from "services/AuthenticationService";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import UrlService from "services/UrlService";
import { toast } from "react-toastify";
import SpinnerButton from "library/spinner-button/SpinnerButton";
import CustomInput from "library/form-assets/CustomInput";


const SignInForm = () => {
    const incorrectMessage = "Username or password is incorrect.";

    const navigate = useNavigate();

    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [btnDisabled, setBtnDisabled] = useState(true);
    const [errorMessage, setErrorMessage] = useState('');
    const [loading, setLoading] = useState(false);

    const changeName = (e) => {
        const name = e.target.value;
        if (name && password)
            setBtnDisabled(false);
        else
            setBtnDisabled(true);

        setName(name);
    }

    const changePassword = (e) => {
        const password = e.target.value;
        if (name && password)
            setBtnDisabled(false);
        else
            setBtnDisabled(true);

        setPassword(password);
    }

    const submit = async () => {
        setLoading(true);
        const body = await AuthenticationService.login(name, password);
        if (body === null) {
            navigate(UrlService.urls.unexpectedError);
            return;
        }

        if (body.status === ApiService.statusCode.OK) {
            toast.success("You have successfully signed in.");
            navigate(UrlService.urls.home);
        }
        else if (body.status === ApiService.statusCode.UNAUTHORIZED)
            setErrorMessage(incorrectMessage);
        else
            toast.error(body.message);

        setLoading(false);
    }

    const googleLoginHandler = async () => {
        const body = await AuthenticationService.getGoogleLoginUrl();
        console.log(body);
        if (body === null || body.status !== ApiService.statusCode.OK)
            navigate(UrlService.urls.unexpectedError);
        else
            window.location.href = body.message;
    }

    return (
        <div>
            <CustomInput
                type="text"
                className="form-control form-control-lg mb-3"
                placeholder="Username" onChange={changeName}
            />

            <PasswordInput
                divClassName="mb-3"
                inputClassName="form-control form-control-lg"
                onChange={changePassword}
            />

            <p className="text-danger">{errorMessage}</p>

            <SpinnerButton
                className="btn btn-lg w-100 fw-bold border-2 green-btn"
                disabled={btnDisabled}
                loading={loading}
                onClick={submit}
            >
                Sign in
            </SpinnerButton>
            <button onClick={googleLoginHandler} className="btn btn-lg btn-primary w-100 fw-bold border-2 mt-3">Login With Google</button>
        </div>
    );
}

export default SignInForm
