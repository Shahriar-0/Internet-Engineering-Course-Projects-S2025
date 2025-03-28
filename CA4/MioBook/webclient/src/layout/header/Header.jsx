
const Header = () => {
    return (
      <header className="shadow py-3 px-5 mb-5">
          <div className="container p-0 m-0 mx-auto d-flex flex-wrap align-items-center justify-content-between">
              <a href="../homepage/homepage.html"><img src="../../assets/../assets/icons/logo-icon.svg" alt="MioBook"/></a>
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
              <button className="d-sm-none btn border-0 fw-bolder bg-gray"><i className="bi bi-search"></i></button>
              <button className="btn border-1 fw-bold green-btn">Buy now</button>
          </div>
      </header>
    );
}

export default Header
