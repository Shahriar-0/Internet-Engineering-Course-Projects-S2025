import { useNavigate } from "react-router-dom";
import ProfileBook from "assets/images/user/book.svg";

const BookListRow = ({ book, status, button }) => {
    const navigate = useNavigate();

    return (
        <tr>
            <td class="d-sm-none text-center"><i class="bi bi-info-circle-fill"></i></td>
            <td><img src={ProfileBook} alt="book-image" /></td>
            <td class="d-none d-sm-table-cell">{book.bookTitle}</td>
            <td class="d-none d-md-table-cell">{book.author}</td>
            <td>${book.price}</td>
            
        </tr>
    );
}