import AuthorName from "./AuthorName";
import BookTitle from "common/book/BookTitle";
import AuthorImg from "assets/images/authors/author-img.svg";

const AuthorListRow = ({ author, status, button }) => {
    console.log("author", author);

    return (
        <tr>
            <td className="d-sm-none text-center"><i class="bi bi-info-circle-fill"></i></td>
            <td><img src={AuthorImg} alt="author-image"/></td>
            <td className="d-none d-sm-table-cell"><AuthorName author={author.name} /></td>
            <td className="d-none d-md-table-cell">{author.penName}</td>
            <td className="d-none d-md-table-cell">{author.nationality}</td>
            <td className="d-none d-md-table-cell">{author.born}</td>
            <td className="d-none d-md-table-cell">{author.died || "-"}</td>
        </tr>
    );
};

export default AuthorListRow;