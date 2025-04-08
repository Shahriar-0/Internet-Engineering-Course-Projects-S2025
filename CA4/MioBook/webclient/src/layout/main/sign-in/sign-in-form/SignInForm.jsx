import PasswordInput from "library/form-assets/PasswordInput";
import ApiService from "services/ApiService";
import AuthenticationService from "services/AuthenticationService";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import UrlService from "../../../../services/UrlService";

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
        try {
            const body = await AuthenticationService.login(name, password);
            const status = body.status;
            if (status === ApiService.statusCode.OK)
                navigate(UrlService.urls.home);
            else if (status === ApiService.statusCode.UNAUTHORIZED)
                setErrorMessage(incorrectMessage);
            // TODO: Add toast and use it in unknown status
            else throw new Error("unknown status");
        }
        catch (e) {
            console.error(e);
            // TODO: Add toast and use it in unknown status
            throw e;
        }
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
