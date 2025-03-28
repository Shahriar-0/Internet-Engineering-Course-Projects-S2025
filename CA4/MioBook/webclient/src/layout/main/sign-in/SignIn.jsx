
const SignIn = () => {
    return (
      <main className="container p-4 d-flex justify-content-center align-items-center text-center">
          <div className="card shadow py-3 px-4 px-sm-5 border-0 rounded-4 w-100 w-md-75 w-lg-50">
              <h3 className="fw-bold fs-2">Sign in</h3>
              <p className="text-muted">MioBook</p>

              <form>
                  <div className="mb-3">
                      <label htmlFor="username" className="visually-hidden">Username</label>
                      <input type="text" id="username" className="form-control form-control-lg" placeholder="Username"
                             required/>
                  </div>

                  <div className="mb-3 position-relative">
                      <label htmlFor="password" className="visually-hidden">Password</label>
                      <input type="password" id="password" className="form-control form-control-lg"
                             placeholder="Password" required/>
                      <i
                        className="bi bi-eye-slash btn border-0 position-absolute top-50 end-0 translate-middle-y me-3"></i>
                  </div>

                  <button type="submit" className="btn btn-lg w-100 fw-bold border-2 green-btn">Sign in</button>
              </form>
              <p className="text-center text-muted mt-3 mb-4">Not a member yet? <a href="../signup/signup.html">Sign
                  Up</a></p>
          </div>
      </main>
    );
}

export default SignIn;
