import { useParams } from "react-router-dom";
import BookContentIcon from "assets/icons/book-content-icon.svg"
import AuthorName from "common/author/AuthorName";

const BookContent = () => {
    const { title } = useParams();


    return (
        <main className="container p-4 justify-content-center align-items-center">
            <div className="p-5 shadow rounded-4 d-flex justify-content-between align-items-center mb-4">
                <h3 className="fw-bold fs-2 d-flex">
                    <img className="me-2" src={BookContentIcon} alt="book-content-icon"/>
                    Book
                </h3>
                <span className="d-flex">By <AuthorName className={"ms-1"} author="author" /></span>
            </div>
            <div className="shadow rounded-4 p-5 justified-text">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam ornare eros augue, tempus porttitor nisl auctor id. Integer at mattis nisi, sed vestibulum diam. Aenean a elit lectus. Suspendisse fringilla, magna sit amet luctus aliquam, turpis sapien accumsan nisl, ac ullamcorper turpis turpis quis dui. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Donec at convallis sem, a sollicitudin ante. Nullam quis convallis dolor.Sed commodo ac risus vitae viverra. Nunc aliquet facilisis leo quis congue. Maecenas lobortis augue nibh, sit amet ullamcorper leo gravida eget. Suspendisse vitae ante at risus pellentesque placerat quis mattis nulla. Duis sed aliquam augue, ac finibus velit. Mauris fringilla bibendum felis quis porttitor. Suspendisse nisi dui, blandit at iaculis vel, condimentum ac leo. Pellentesque justo quam, imperdiet et lacinia eget, rutrum vel elit.Cras cursus nibh ac tincidunt imperdiet. Donec auctor ex leo, non dictum nibh pulvinar eget. Donec pulvinar fermentum eros, vel accumsan mauris commodo eget. Aliquam erat volutpat. Aenean libero orci, gravida vel nisl a, tristique maximus dolor. Cras feugiat nisl quis massa auctor pulvinar. Etiam vel nibh tincidunt, commodo massa porttitor, facilisis sem. Duis ac quam mauris. Morbi pretium dapibus lacus vel eleifend. Ut nec lorem porttitor, dapibus ante nec, tempus risus. Quisque lorem felis, lacinia eget augue eu, facilisis pellentesque justo. Praesent vel dictum odio, id pulvinar velit. Duis nec eros massa. Phasellus eu elementum ligula, non commodo diam. Mauris semper dui sapien, tristique tempus turpis lacinia non. Etiam tristique et nisi et cursus.
            </div>
        </main>
    );
};

export default BookContent;