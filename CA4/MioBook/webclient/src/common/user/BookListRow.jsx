import ProfileBook from "assets/images/user/book.svg";
import AuthorName from "../author/AuthorName";
import BookTitle from "../book/BookTitle";

const BookListRow = ({ book, status, action, actionName }) => {

    const getExpirationDate = () => {
        const borrowDate = new Date(book.borrowDate);
        borrowDate.setDate(borrowDate.getDate() + book.borrowDays);
        return borrowDate.toLocaleString();
    };

    return (
        <tr>
            <td className="d-sm-none text-center"><i className="bi bi-info-circle-fill"></i></td>
            <td><img src={ProfileBook} alt="book-image" /></td>
            <td className="d-none d-sm-table-cell"><BookTitle title={book.title} isBold={false} /></td>
            <td className="d-none d-sm-table-cell">
                {book.isBorrowed && <span className="me-1 text-decoration-line-through">${book.price}</span>}
                ${book.finalPrice}
            </td>
            <td className="d-none d-sm-table-cell"><AuthorName author={book.author} /></td>
            <td className="d-none d-sm-table-cell">{book.genres && book.genres.join(", ")}</td>
            <td className="d-none d-md-table-cell">{book.publisher}</td>
            <td className="d-none d-md-table-cell">{book.year}</td>
            <td className="d-none d-md-table-cell">{book.isBorrowed ? (<>
                <div>Borrowed</div>
                <div style={{ fontSize: '0.8em' }}>Until: {getExpirationDate()}</div>
            </>) : "Owned"}</td>
            <td className="d-none d-md-table-cell">{book.borrowDays || "-"}</td>
            <td className="d-none d-md-table-cell">
                {action && <button className="btn green-btn" onClick={() => action(book.title)}>
                    {actionName}
                </button>}
            </td>
        </tr>
    );
};

export default BookListRow;