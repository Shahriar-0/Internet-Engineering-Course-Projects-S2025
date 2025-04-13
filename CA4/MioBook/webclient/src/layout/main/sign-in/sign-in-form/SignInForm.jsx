import PasswordInput from "library/form-assets/PasswordInput";
import ApiService from "services/ApiService";
import AuthenticationService from "services/AuthenticationService";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import UrlService from "../../../../services/UrlService";
import {toast} from "react-toastify";

const SignInForm = () => {
    const incorrectMessage = "Username or password is incorrect.";

    const navigate = useNavigate();

    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [btnDisabled, setBtnDisabled] = useState(true);
    const [errorMessage, setErrorMessage] = useState('');

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
    }

    return (
        <div>
            <input type="text" className="form-control form-control-lg mb-3"
                   placeholder="Username" onChange={changeName}/>

            <PasswordInput divClassName="mb-3" inputClassName="form-control form-control-lg"
                           onChange={changePassword}/>

            <p className="text-danger">{errorMessage}</p>
            <button className="btn btn-lg w-100 fw-bold border-2 green-btn" disabled={btnDisabled}
                    onClick={() => submit()}>Sign in</button>
        </div>
    );
}

export default SignInForm
