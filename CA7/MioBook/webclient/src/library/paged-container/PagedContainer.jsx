import { useState } from "react";

const PagedContainer = ({ children, totalPages, pageNumberClassName, currentPageNumberClassName, onPageChange }) => {
    const [currentPage, setCurrentPage] = useState(1);

    const onFirstPage = () => {
        onPageChange(1);
        setCurrentPage(1);
    }

    const onLastPage = () => {
        onPageChange(totalPages);
        setCurrentPage(totalPages);
    }

    const onNextPage = () => {
        if (currentPage < totalPages) {
            setCurrentPage(currentPage + 1);
            onPageChange(currentPage + 1);
        }
    }

    const onPreviousPage = () => {
        if (currentPage > 1) {
            onPageChange(currentPage - 1);
            setCurrentPage(currentPage - 1);
        }
    }

    return (
        <div>
            <div className="mb-3">
                {children}
            </div>

            <ul className="pagination justify-content-center">
                <li onClick={onPreviousPage} className={`pointer ${pageNumberClassName}`}>&lt;</li>
                <li onClick={onFirstPage} className={`pointer ${pageNumberClassName}`}>First</li>
                <li className={currentPageNumberClassName}>{currentPage}</li>
                <li onClick={onLastPage} className={`pointer ${pageNumberClassName}`}>Last</li>
                <li onClick={onNextPage} className={`pointer ${pageNumberClassName}`}>&gt;</li>
            </ul>
        </div>
    );
}

export default PagedContainer;
