import PasswordInput from "library/form-assets/PasswordInput";
import RolePicker from "./RolePicker";
import {useState} from "react";
import {
    canSubmit,
    getInitFormState,
    hasAnyError,
    isKnownBadRequestError,
    setError,
    validateForm
} from "./SignUpFormLogic";
import ApiService from "services/ApiService";
import {toast} from "react-toastify";
import AuthenticationService from "services/AuthenticationService";
import {useNavigate} from "react-router-dom";
import UrlService from "services/UrlService";
import SpinnerButton from "library/spinner-button/SpinnerButton";
import CustomInput from "library/form-assets/CustomInput";

const SignUpForm = () => {
    const [formState, setFormState] = useState(getInitFormState());
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const changeName = (e) => {
        const username = e.target.value;
        setFormState({...formState, username: {...formState.username, value: username}});
    }

    const changePassword = (e) => {
        const password = e.target.value;
        setFormState({...formState, password: {...formState.password, value: password}});
    }

    const changeEmail = (e) => {
        const email = e.target.value;
        setFormState({...formState, email: {...formState.email, value: email}});
    }

    const changeCountry = (e) => {
        const country = e.target.value;
        setFormState({...formState, country: country});
    }

    const changeCity = (e) => {
        const city = e.target.value;
        setFormState({...formState, city: city});
    }

    const changeRole = (role) => {
        setFormState({...formState, role: role});
    }

    const submit = async () => {
        setLoading(true);
        const tempState = validateForm(formState);
        if (hasAnyError(tempState)) {
            setFormState(tempState);
            setLoading(false);
            return;
        }

        const {response, body} = await ApiService.signUp(
            formState.username.value,
            formState.password.value,
            formState.email.value,
            formState.country,
            formState.city,
            formState.role
        );

        if (response === null) {
            navigate(UrlService.urls.unexpectedError)
            return;
        }

        if (body.status === ApiService.statusCode.CREATED) {
            toast.success("You have successfully signed up.");
            const loginBody = await AuthenticationService.login(formState.username.value, formState.password.value);
            if (loginBody.status === ApiService.statusCode.OK)
                navigate(UrlService.urls.home);
            else
                navigate(UrlService.urls.unexpectedError);
        }
        else if (body.status === ApiService.statusCode.BAD_REQUEST && isKnownBadRequestError(body.data))
            setFormState(setError(formState, body.data));
        else
            toast.error(body.message);

        setLoading(false);
    }

    return (
        <div className="row">
            <CustomInput type="text" errorClassName="p-0" className="form-control form-control-lg mb-3" placeholder="Username"
                   error={formState.username.error} onChange={changeName}/>

            <PasswordInput divClassName="mb-3 p-0" inputClassName="form-control form-control-lg" errorClassName="p-0"
                           error={formState.password.error} onChange={changePassword}/>

            <CustomInput type="email" errorClassName="p-0" className="form-control form-control-lg mb-3" placeholder="Email"
                   error={formState.email.error} onChange={changeEmail}/>

            <div className="mb-3 col-12 col-sm-6 px-0 pe-sm-1">
                <CustomInput type="text" className="form-control form-control-lg" placeholder="Country" onChange={changeCountry}/>
            </div>
            <div className="mb-3 col-sm-6 col-12 px-0 ps-sm-1">
                <CustomInput type="text" className="form-control form-control-lg" placeholder="City" onChange={changeCity}/>
            </div>
            <RolePicker onChange={changeRole}/>
            <SpinnerButton className="btn btn-lg w-100 fw-bold border-2 green-btn" spinnerClassName="border-green" disabled={!canSubmit(formState)} loading={loading} onClick={submit}>Sign up</SpinnerButton>
        </div>
    );
}

export default SignUpForm;
