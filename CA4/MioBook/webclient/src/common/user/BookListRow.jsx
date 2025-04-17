import { useNavigate } from "react-router-dom";
import ProfileBook from "assets/images/user/book.svg";

const BookListRow = ({ book, status, button }) => {
    const navigate = useNavigate();

    {/* FIXME: use other components here as well like author name */ }

    const getExpirationDate = () => {
        const borrowDate = new Date(book.borrowDate);
        borrowDate.setDate(borrowDate.getDate() + book.borrowDays);
        return borrowDate.toLocaleString();
    };

    return (
        <tr>
            <td class="d-sm-none text-center"><i class="bi bi-info-circle-fill"></i></td>
            <td><img src={ProfileBook} alt="book-image" /></td>
            <td class="d-none d-sm-table-cell">{book.title}</td>
            <td class="d-none d-sm-table-cell">${book.finalPrice}</td>
            <td class="d-none d-sm-table-cell">{book.author}</td>
            <td class="d-none d-sm-table-cell">{book.genres && book.genres.join(", ")}</td>
            <td class="d-none d-md-table-cell">{book.publisher}</td>
            <td class="d-none d-md-table-cell">{book.year}</td>
            <td class="d-none d-md-table-cell">{book.isBorrowed ? (<><div>Borrowed</div><div style={{ fontSize: '0.8em' }}>Until: {getExpirationDate()}</div></>) : "Owned"}</td>
            <td class="d-none d-md-table-cell">{book.borrowDays || "-"}</td>
            <td class="d-none d-md-table-cell">button</td>
        </tr>
    );
};

export default BookListRow;