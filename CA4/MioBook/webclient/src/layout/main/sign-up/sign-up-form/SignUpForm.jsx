import PasswordInput from "library/form-assets/PasswordInput";
import RolePicker from "./role-picker/RolePicker";

const SignUpForm = () => {

    const canSubmit = () => {
        return true
    }

    return (
        <div className="row">
            <input type="text" className="form-control form-control-lg mb-3" placeholder="Username"/>
            <PasswordInput divClassName="mb-3 p-0" inputClassName="form-control form-control-lg" />
            <input type="email" className="form-control form-control-lg mb-3" placeholder="Email"/>
            <div className="mb-3 col-12 col-sm-6 px-0 pe-sm-1">
                <input type="text" className="form-control form-control-lg" placeholder="Country"/>
            </div>
            <div className="mb-3 col-sm-6 col-12 px-0 ps-sm-1">
                <input type="text" className="form-control form-control-lg" placeholder="City"/>
            </div>
            <RolePicker />
            <button className="btn btn-lg w-100 fw-bold border-2 green-btn" disabled={!canSubmit()}>Sign up</button>
        </div>
    );
}

export default SignUpForm;
