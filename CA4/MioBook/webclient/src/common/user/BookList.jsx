import BookListRow from "./BookListRow";

const BookList = ({ bookList = null }) => {
    return (
        <table class="table">
            <thead>
                <tr>
                    <th class="d-sm-none bg-gray fw-medium text-muted">Info</th>
                    <th class="bg-gray fw-medium text-muted">Image</th>
                    <th class="d-none d-sm-table-cell bg-gray fw-medium text-muted">Name</th>
                    <th class="d-none d-md-table-cell bg-gray fw-medium text-muted">Price</th>
                    <th class="d-none d-md-table-cell bg-gray fw-medium text-muted">Author</th>
                    <th class="d-none d-md-table-cell bg-gray fw-medium text-muted">Genre</th>
                    <th class="d-none d-md-table-cell bg-gray fw-medium text-muted">Publisher</th>
                    <th class="d-none d-md-table-cell bg-gray fw-medium text-muted">Published Year</th>
                    <th class="d-none d-md-table-cell bg-gray fw-medium text-muted">Status</th>
                    <th class="d-none d-md-table-cell bg-gray fw-medium text-muted">BorrowDays</th>
                    <th class="d-none d-md-table-cell bg-gray fw-medium text-muted">PlaceHolder</th>
                    {/* FIXME: shahnammmmmmmmmmmmmmm, it should be empty */}
                </tr>
            </thead>
            <tbody class="align-middle">
                {bookList && bookList.map((book, index) => (
                    <BookListRow key={index} book={book} />
                ))}
            </tbody>
        </table>
    );
};

export default BookList;