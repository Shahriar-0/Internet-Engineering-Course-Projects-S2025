import { useNavigate } from "react-router-dom";
import searchIcon from "assets/icons/magnifying-glass.svg";
import UrlService from "services/UrlService";
import { useState } from "react";

const HeaderSearchBox = () => {
    const [filterType, setFilterType] = useState(null);
    const [filterValue, setFilterValue] = useState(null);

    const handleFilterTypeChange = (event) => {
        setFilterType(event.target.value);
    };

    const handleFilterValueChange = (event) => {
        setFilterValue(event.target.value);
    };

    const navigate = useNavigate();

    const handleSearch = () => {
        let query = "";
        if (filterType && filterValue)
            query = `?filterType=${encodeURIComponent(filterType)}&filterValue=${encodeURIComponent(filterValue)}`;
        navigate(`${UrlService.urls.searchResult}?${query}`);
    };

    return (
        <div className="d-flex bg-gray w-xl-75 p-2 rounded-3">
            <label htmlFor="filter-type" className="visually-hidden">Filter Type</label>
            <select id="filter-type" name="filterType"
                className="d-none d-md-block text-secondary me-2 px-3 border-0 bg-transparent outline-0 pointer"
                value={filterType} onChange={handleFilterTypeChange}>
                <option className="text-secondary bg-gray" value="Name">Name</option>
                <option className="text-secondary bg-gray" value="Author">Author</option>
                <option className="text-secondary bg-gray" value="Genre">Genre</option>
            </select>
            <label htmlFor="filter-value" className="visually-hidden">Filter Value</label>

            <input id="filter-value" name="filterValue" type="search"
                className="d-none d-md-block flex-grow-1 border-start border-secondary ps-3 border-0 bg-transparent outline-0"
                placeholder="Search" value={filterValue} onChange={handleFilterValueChange} />
            <img className="me-md-3 pointer" src={searchIcon} alt="search icon" onClick={handleSearch} />
        </div>
    );
}

export default HeaderSearchBox;
