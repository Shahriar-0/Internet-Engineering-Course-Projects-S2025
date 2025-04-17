import AuthorListRow from "./AuthorListRow";

const AuthorList = ({ authorList = null }) => {
    return (
        <table class="table">
            <thead>
                <tr>
                    <th class="d-sm-none bg-gray fw-medium text-muted">Info</th>
                    <th class="bg-gray fw-medium text-muted">Image</th>
                    <th class="d-none d-sm-table-cell bg-gray fw-medium text-muted">Name</th>
                    <th class="d-none d-md-table-cell bg-gray fw-medium text-muted">PenName</th>
                    <th class="d-none d-md-table-cell bg-gray fw-medium text-muted">Nationality</th>
                    <th class="d-none d-md-table-cell bg-gray fw-medium text-muted">Born</th>
                    <th class="d-none d-md-table-cell bg-gray fw-medium text-muted">Died</th>
                </tr>
            </thead>
            <tbody class="align-middle">
                {authorList && authorList.map((author, index) => (
                    <AuthorListRow key={index} author={author} />
                ))}
            </tbody>
        </table>
    );
};

export default AuthorList;