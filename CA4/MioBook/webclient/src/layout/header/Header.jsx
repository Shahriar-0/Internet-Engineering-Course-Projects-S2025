import MioBookLogo from "common/mio-book-logo/MioBookLogo";
import ProfileIcon from "common/profile-icon/ProfileIcon";
import HeaderSearchBox from "./header-serch-box/HeaderSearchBox";
import {useLocation} from "react-router-dom";
import UrlService from "services/UrlService";

const Header = () => {
    const location = useLocation();

    const headerContent = (
        <header className="shadow py-3 px-5 mb-5">
            <div
                className="container p-0 m-0 mx-auto d-flex flex-wrap align-items-center justify-content-between">
                <MioBookLogo/>
                <HeaderSearchBox/>
                <ProfileIcon username="Shahnam"/>
            </div>
        </header>
    )

    return (
        UrlService.mustShowHeader(location.pathname) && headerContent
    );
}

export default Header
