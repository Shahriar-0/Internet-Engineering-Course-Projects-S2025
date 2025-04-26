import SignInForm from "./sign-in-form/SignInForm";
import { Link } from "react-router-dom";
import UrlService from "services/UrlService";

const SignIn = () => {
    return (
        <main className="container p-4 d-flex justify-content-center align-items-center text-center">
            <div className="card shadow py-3 px-4 px-sm-5 border-0 rounded-4 w-100 w-md-75 w-lg-50">
                <h3 className="fw-bold fs-2">Sign in</h3>
                <p className="text-muted">MioBook</p>
                <SignInForm />
                <p className="text-center text-muted mt-3 mb-4">Not a member yet?
                    <></> <Link to={UrlService.urls.signUp} className="text-green fw-bold">Sign Up</Link></p>
            </div>
        </main>
    );
}

export default SignIn;
