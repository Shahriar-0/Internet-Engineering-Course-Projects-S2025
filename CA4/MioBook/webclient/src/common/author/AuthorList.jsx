import AuthorListRow from "./AuthorListRow";

const AuthorList = ({ authorList = null }) => {
    return (
        <table className="table">
            <thead>
                <tr>
                    <th className="d-sm-none bg-gray fw-medium text-muted">Info</th>
                    <th className="bg-gray fw-medium text-muted">Image</th>
                    <th className="d-none d-sm-table-cell bg-gray fw-medium text-muted">Name</th>
                    <th className="d-none d-md-table-cell bg-gray fw-medium text-muted">PenName</th>
                    <th className="d-none d-md-table-cell bg-gray fw-medium text-muted">Nationality</th>
                    <th className="d-none d-md-table-cell bg-gray fw-medium text-muted">Born</th>
                    <th className="d-none d-md-table-cell bg-gray fw-medium text-muted">Died</th>
                </tr>
            </thead>
            <tbody className="align-middle">
                {authorList && authorList.map((author, index) => (
                    <AuthorListRow key={index} author={author} />
                ))}
            </tbody>
        </table>
    );
};

export default AuthorList;