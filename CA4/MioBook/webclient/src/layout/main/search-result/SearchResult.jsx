import BookCardContainer from "../../../common/book/BookCardContainer";

const SearchResult = () => {
    const tempBookList = Array.from({ length: 10 }, (_, i) => ({
        bookTitle: "Book Title",
        authorName: "Author McName",
        price: 10.25,
        rating: 4,
    }));

    return (
        <main className="container">
            <section>
                <p className="fw-bold fs-3 mb-4">Result for <br className="d-sm-none"/> &lt;Search Parameters&gt;</p>
                <BookCardContainer bookList={tempBookList}  />

                <ul className="pagination justify-content-center">
                    <li className="px-3 py-2 mx-1 rounded-3 pagination-item">&lt;</li>
                    <li className="px-3 py-2 mx-1 rounded-3 pagination-item active">1</li>
                    <li className="px-3 py-2 mx-1 rounded-3 pagination-item">2</li>
                    <li className="px-3 py-2 mx-1 rounded-3 pagination-item">3</li>
                    <li className="d-sm-none px-3 py-2 mx-1 rounded-3 pagination-item">...</li>
                    <li className="d-none d-sm-block px-3 py-2 mx-1 rounded-3 pagination-item">4</li>
                    <li className="d-none d-sm-block px-3 py-2 mx-1 rounded-3 pagination-item">5</li>
                    <li className="px-3 py-2 mx-1 rounded-3 pagination-item">&gt;</li>
                </ul>
            </section>
        </main>
    );
}

export default SearchResult;
