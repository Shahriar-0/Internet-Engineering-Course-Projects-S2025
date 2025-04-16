import BookCardContainer from "common/book/BookCardContainer";
import PagedContainer from "library/paged-container/PagedContainer";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import ApiService from "services/ApiService";
import UrlService from "services/UrlService";
import filterIcon from "assets/icons/filter-icon.svg";
import BookFilterModal from "./book-filter-modal/BookFilterModal";
import {getInitialFilterState} from "./book-filter-modal/BookFilterLogic";

const SearchResult = () => {
    const DEFAULT_PAGE_SIZE = 10;

    const [filterModalOpen, setFilterModalOpen] = useState(false);
    const [books, setBooks] = useState([]);
    const [totalPages, setTotalPages] = useState(0);

    const navigate = useNavigate();

    let currentFilter = getInitialFilterState();
    let currentPage = 1;

    useEffect(() => {
        fetchBooks();
    }, []);

    const fetchBooks = async () => {
        const {body} = await ApiService.searchBooks({
            title: currentFilter.bookName,
            author: currentFilter.authorName,
            genre: currentFilter.genre,
            from: currentFilter.publishedYear,
            to: currentFilter.publishedYear,
            sortBy: currentFilter.sortBy,
            isAscending: currentFilter.isAscending,
            pageNumber: currentPage,
            pageSize: DEFAULT_PAGE_SIZE
        });

        if (body === null || body.status !== ApiService.statusCode.OK) {
            navigate(UrlService.urls.unexpectedError);
            return;
        }

        setBooks(body.data.list);
        setTotalPages(body.data.totalPageNumber);
    }

    const onPageChange = (page) => {
        currentPage = page;
        fetchBooks();
    };

    const onFilterApply = (filter) => {
        currentFilter = filter;
        fetchBooks();
    };

    return (
        <main className="container">
            <section>
                <BookFilterModal isOpen={filterModalOpen} onClose={() => setFilterModalOpen(false)} onApply={onFilterApply}/>

                <div className="d-flex justify-content-between mb-3">
                    <p className="fw-bold fs-3 mb-0">Result for <br className="d-sm-none"/> &lt;Search Parameters&gt;</p>
                    <button className="d-flex align-items-center btn btn-lg green-btn px-3" onClick={() => setFilterModalOpen(true)}>
                        <img className="me-1" src={filterIcon} alt="filter icon"/>Filter
                    </button>
                </div>

                <PagedContainer pageNumberClassName="px-3 py-2 mx-1 rounded-3 hover-gray fw-bold"
                                currentPageNumberClassName="px-3 py-2 mx-1 rounded-3 bg-green text-white fw-bold"
                                totalPages={totalPages}
                                onPageChange={onPageChange}>
                    <BookCardContainer bookList={books}/>
                </PagedContainer>
            </section>
        </main>
    );
}

export default SearchResult;
