import PasswordInput from "library/form-assets/PasswordInput";
import {useState} from "react";

const SignInForm = () => {
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

    const submit = () => {
        setErrorMessage("submit");
    }

    return (
        <div>
            <input type="text" className="form-control form-control-lg mb-3"
                   placeholder="Username" onChange={changeName}/>

            <PasswordInput divClassName="mb-3" inputClassName="form-control form-control-lg"
                           onChange={changePassword}/>

            <p className="text-danger">{errorMessage}</p>
            <button className="btn btn-lg w-100 fw-bold border-2 green-btn" disabled={btnDisabled}
                    onClick={submit}>Sign in</button>
        </div>
    );
}

export default SignInForm
