import BookCardContainer from "common/book/BookCardContainer";
import PagedContainer from "library/paged-container/PagedContainer";
import { useEffect, useRef, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import ApiService from "services/ApiService";
import UrlService from "services/UrlService";
import filterIcon from "assets/icons/filter-icon.svg";
import BookFilterModal from "./book-filter-modal/BookFilterModal";
import { getInitialFilterState } from "./book-filter-modal/BookFilterLogic";

const SearchResult = () => {
    const DEFAULT_PAGE_SIZE = 10;

    const [filterModalOpen, setFilterModalOpen] = useState(false);
    const [books, setBooks] = useState([]);
    const [totalPages, setTotalPages] = useState(0);

    const navigate = useNavigate();
    const [searchParams] = useSearchParams();

    const filter = useRef(getInitialFilterState());
    const page = useRef(1);

    useEffect(() => {
        const filterType = searchParams.get("filterType");
        const filterValue = searchParams.get("filterValue");

        if (filterType && filterValue) {
            const newFilter = { ...filter.current };
            if (filterType === "Name") newFilter.bookName = filterValue;
            else if (filterType === "Author") newFilter.authorName = filterValue;
            else if (filterType === "Genre") newFilter.genre = filterValue;

            filter.current = newFilter;
        }

        fetchBooks(filter.current, page.current);
    }, [searchParams]);

    const fetchBooks = async (filter, page) => {
        const { body } = await ApiService.searchBooks({
            title: filter.bookName,
            author: filter.authorName,
            genre: filter.genre,
            from: filter.publishedYear,
            to: filter.publishedYear,
            sortBy: filter.sortBy,
            isAscending: filter.isAscending,
            pageNumber: page,
            pageSize: DEFAULT_PAGE_SIZE
        });

        if (body === null || body.status !== ApiService.statusCode.OK) {
            navigate(UrlService.urls.unexpectedError);
            return;
        }

        setBooks(body.data.list);
        setTotalPages(body.data.totalPageNumber);
    }

    const onPageChange = (newPage) => {
        page.current = newPage;
        fetchBooks(filter.current, page.current);
    };

    const onFilterApply = (newFilter) => {
        filter.current = newFilter;
        fetchBooks(filter.current, page.current);
    };

    return (
        <main className="container">
            <section>
                <BookFilterModal state={filter.current} isOpen={filterModalOpen} onClose={() => setFilterModalOpen(false)} onApply={onFilterApply} />

                <button className="ms-auto mb-3 d-flex align-items-center btn btn-lg green-btn px-3" onClick={() => setFilterModalOpen(true)}>
                    <img className="me-1" src={filterIcon} alt="filter icon" />Filter
                </button>

                <PagedContainer pageNumberClassName="px-3 py-2 mx-1 rounded-3 hover-gray fw-bold"
                    currentPageNumberClassName="px-3 py-2 mx-1 rounded-3 bg-green text-white fw-bold"
                    totalPages={totalPages}
                    onPageChange={onPageChange}>
                    <BookCardContainer bookList={books} />
                </PagedContainer>
            </section>
        </main>
    );
}

export default SearchResult;
