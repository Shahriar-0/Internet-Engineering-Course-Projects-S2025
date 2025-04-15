import { Link } from "react-router-dom";
import logo from "assets/icons/mio-book-logo.svg";

const MioBookLogo = () => {
    return (
        <Link to="/home"><img src={logo} alt="MioBook"/></Link>
    );
}

export default MioBookLogo
