import SignUpForm from "./sign-up-form/SignUpForm";
import { Link } from "react-router-dom";
import UrlService from "services/UrlService";

const SignUp = () => {
    return (
        <main className="container d-flex justify-content-center align-items-center text-center p-4">
            <div className="card shadow py-3 px-4 px-sm-5 border-0 rounded-4 w-100 w-md-75 w-lg-50">
                <h3 className="fw-bold fs-2">Sign Up</h3>
                <p className="text-muted">MioBook</p>
                <SignUpForm />
                <p className="mt-3 text-muted">Already have an account?
                    <></> <Link to={UrlService.urls.signIn} className="text-green fw-bold">Sign in</Link></p>
            </div>
        </main>
    );
}

export default SignUp;
