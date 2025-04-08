import PasswordInput from "library/form-assets/PasswordInput";
import RolePicker from "./RolePicker";
import {useState} from "react";
import {
    canSubmit, clearError,
    getInitFormState,
    hasAnyError,
    isKnownBadRequestError,
    setError,
    validateForm
} from "./SignUpFormLogic";
import ApiService from "services/ApiService";

const SignUpForm = () => {
    const [formState, setFormState] = useState(getInitFormState());

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
        const tempState = validateForm(formState);
        if (hasAnyError(tempState)) {
            setFormState(tempState);
            return;
        }

        const {body} = await ApiService.signUp(
            formState.username.value,
            formState.password.value,
            formState.email.value,
            formState.country,
            formState.city,
            formState.role
        );

        if (body.status === ApiService.statusCode.CREATED) {
            // TODO: Add toast message for success
            setFormState(clearError(formState));
        }
        else if (body.status === ApiService.statusCode.BAD_REQUEST) {
            if (isKnownBadRequestError(body.data))
                setFormState(setError(formState, body.data));
            else
                // TODO: Add toast message for unknown bad request error
            ;
        }
        else {
            // TODO: Add toast message for unknown status
        }
    }
    return (
        <div className="row">
            <input type="text" className="form-control form-control-lg mb-3" placeholder="Username" onChange={changeName}/>
            {formState.username.error && <p className="text-danger text-start">{formState.username.error}</p>}

            <PasswordInput divClassName="mb-3 p-0" inputClassName="form-control form-control-lg" onChange={changePassword}/>
            {formState.password.error && <p className="text-danger text-start">{formState.password.error}</p>}

            <input type="email" className="form-control form-control-lg mb-3" placeholder="Email" onChange={changeEmail}/>
            {formState.email.error && <p className="text-danger text-start">{formState.email.error}</p>}

            <div className="mb-3 col-12 col-sm-6 px-0 pe-sm-1">
                <input type="text" className="form-control form-control-lg" placeholder="Country" onChange={changeCountry}/>
            </div>
            <div className="mb-3 col-sm-6 col-12 px-0 ps-sm-1">
                <input type="text" className="form-control form-control-lg" placeholder="City" onChange={changeCity}/>
            </div>
            <RolePicker onChange={changeRole}/>
            <button className="btn btn-lg w-100 fw-bold border-2 green-btn" disabled={!canSubmit(formState)}
                    onClick={submit}>Sign up</button>
        </div>
    );
}

export default SignUpForm;
