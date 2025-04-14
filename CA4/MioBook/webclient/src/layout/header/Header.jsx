import MioBookLogo from "common/mio-book-logo/MioBookLogo";
import ProfileIcon from "common/profile-icon/ProfileIcon";
import HeaderSearchBox from "./header-serch-box/HeaderSearchBox";
import { useLocation } from "react-router-dom";
import UrlService from "services/UrlService";
import ProfileMenu from "./profile-menu/ProfileMenu";
import { useState } from "react";
import AuthenticationService from "../../services/AuthenticationService";

const Header = () => {
    const location = useLocation();
    const [isOpen, setIsOpen] = useState(false);


    const headerContent = (
        <header className="shadow py-3 px-5 sticky-top bg-custom-white">
            <div className="container p-0 m-0 mx-auto d-flex flex-wrap align-items-center justify-content-between">
                <MioBookLogo />
                <HeaderSearchBox />
                <ProfileIcon
                    username={AuthenticationService.getCurrentUser()?.username}
                    onClick={() => setIsOpen(!isOpen)}
                />
                <ProfileMenu
                    onClose={() => setIsOpen(false)}
                    isOpen={isOpen}
                    username={AuthenticationService.getCurrentUser()?.username}
                />
            </div>
        </header>
    )

    return (
        UrlService.mustShowHeader(location.pathname) && headerContent
    );
}

export default Header
