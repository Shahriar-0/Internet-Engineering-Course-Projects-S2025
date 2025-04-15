import BookCardContainer from "common/book/BookCardContainer";
import PagedContainer from "library/paged-container/PagedContainer";

const SearchResult = () => {
    const tempBookList = Array.from({ length: 10 }, (_, i) => ({
        bookTitle: "Book Title",
        authorName: "Author McName",
        price: 10.25,
        rating: 4,
    }));

    const onPageChange = (page) => {
        console.log(`Page ${page} selected`);
    };

    return (
        <main className="container">
            <section>
                <p className="fw-bold fs-3 mb-4">Result for <br className="d-sm-none"/> &lt;Search Parameters&gt;</p>

                <PagedContainer pageNumberClassName="px-3 py-2 mx-1 rounded-3 hover-gray fw-bold"
                                currentPageNumberClassName="px-3 py-2 mx-1 rounded-3 bg-green text-white fw-bold"
                                totalPages={10}
                                onPageChange={onPageChange}>
                    <BookCardContainer bookList={tempBookList}  />
                </PagedContainer>
            </section>
        </main>
    );
}

export default SearchResult;
