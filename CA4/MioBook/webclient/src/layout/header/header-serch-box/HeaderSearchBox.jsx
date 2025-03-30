
const HeaderSearchBox = () => {
    return (
        <div className="d-none d-sm-flex bg-gray w-50 w-xl-75 p-2 rounded-3">
            <label htmlFor="filter-type" className="visually-hidden">Filter Type</label>
            <select id="filter-type" name="filterType"
                    className="text-secondary me-2 px-3 border-0 bg-transparent outline-0 pointer">
                <option className="text-secondary bg-gray">Name</option>
                <option className="text-secondary bg-gray">Author</option>
                <option className="text-secondary bg-gray">Genre</option>
            </select>
            <label htmlFor="filter-value" className="visually-hidden">Filter Value</label>
            <input id="filter-value" name="filterValue" type="search"
                   className="flex-grow-1 border-start border-secondary ps-3 border-0 bg-transparent outline-0"
                   placeholder="Search"/>
        </div>
    );
}

export default HeaderSearchBox;
