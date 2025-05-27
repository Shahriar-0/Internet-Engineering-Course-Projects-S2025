import BookListRow from "./BookListRow";

const BookList = ({ bookList = null, actionName = null, action = null }) => {
    return (
        <table className="table">
            <thead>
                <tr>
                    <th className="d-sm-none bg-gray fw-medium text-muted">Info</th>
                    <th className="bg-gray fw-medium text-muted">Image</th>
                    <th className="d-none d-sm-table-cell bg-gray fw-medium text-muted">Name</th>
                    <th className="d-none d-md-table-cell bg-gray fw-medium text-muted">Price</th>
                    <th className="d-none d-md-table-cell bg-gray fw-medium text-muted">Author</th>
                    <th className="d-none d-md-table-cell bg-gray fw-medium text-muted">Genre</th>
                    <th className="d-none d-md-table-cell bg-gray fw-medium text-muted">Publisher</th>
                    <th className="d-none d-md-table-cell bg-gray fw-medium text-muted">Published Year</th>
                    <th className="d-none d-md-table-cell bg-gray fw-medium text-muted">Status</th>
                    <th className="d-none d-md-table-cell bg-gray fw-medium text-muted">BorrowDays</th>
                    <th className="d-none d-md-table-cell bg-gray fw-medium text-muted"></th>
                </tr>
            </thead>
            <tbody className="align-middle">
                {bookList && bookList.map((book, index) => (
                    <BookListRow key={index} book={book} action={action} actionName={actionName} />
                ))}
            </tbody>
        </table>
    );
};

export default BookList;