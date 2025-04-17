import {useNavigate} from "react-router-dom";
import searchIcon from "assets/icons/magnifying-glass.svg";
import UrlService from "services/UrlService";

const HeaderSearchBox = () => {
    const navigate = useNavigate();

    return (
        <div className="d-flex bg-gray w-xl-75 p-2 rounded-3">
            <label htmlFor="filter-type" className="visually-hidden">Filter Type</label>
            <select id="filter-type" name="filterType"
                    className="d-none d-md-block text-secondary me-2 px-3 border-0 bg-transparent outline-0 pointer">
                <option className="text-secondary bg-gray">Name</option>
                <option className="text-secondary bg-gray">Author</option>
                <option className="text-secondary bg-gray">Genre</option>
            </select>
            <label htmlFor="filter-value" className="visually-hidden">Filter Value</label>

            <input id="filter-value" name="filterValue" type="search"
                className="d-none d-md-block flex-grow-1 border-start border-secondary ps-3 border-0 bg-transparent outline-0"
                placeholder="Search" />
            <img className="me-md-3 pointer" src={searchIcon} alt="search icon"
                 onClick={() => navigate(UrlService.urls.searchResult)} />
        </div>
    );
}

export default HeaderSearchBox;
