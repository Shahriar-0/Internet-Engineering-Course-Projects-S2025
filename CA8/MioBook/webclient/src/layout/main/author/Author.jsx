import ApiService from "services/ApiService";
import { useEffect, useState } from "react";
import { useParams, useNavigate } from 'react-router-dom';
import { toast } from "react-toastify";
import UrlService from "services/UrlService";
import BookCardContainer from "common/book/BookCardContainer";
import HomeEntityCover from "common/HomeEntity/HomeEntityCover";
import AuthorCoverImg from "assets/images/authors/author-cover.svg"
import AuthorHeaderImg from "assets/images/authors/author-header-img.svg"

const Author = () => {
    const { name } = useParams();

    const [author, setAuthor] = useState(null);
    const [books, setBook] = useState(null);

    const navigate = useNavigate();

    useEffect(() => {
        const fetchAuthor = async () => {
            const { body, error } = await ApiService.getAuthor(name);
            if (body && body.status === ApiService.statusCode.OK)
                setAuthor(body.data);
            else if (body && body.status !== ApiService.statusCode.OK)
                toast.error(body.message);
            else
                navigate(UrlService.urls.unexpectedError);
        };

        const fetchBooks = async () => {
            const { body, error } = await ApiService.searchBooks({ author: name });
            if (body && body.status === ApiService.statusCode.OK)
                setBook(body.data.list);
            else if (body && body.status !== ApiService.statusCode.OK)
                toast.error(body.message);
            else
                navigate(UrlService.urls.unexpectedError);
        };

        fetchAuthor();
        fetchBooks();
    }, [navigate, name]);


    return (
        <main className="container">
            <section className="row rounded-4 shadow-lg border-1 border-bottom border-secondary py-4 mx-2 mb-5">
                {/* FIXME: not exactly like figma */}
                <HomeEntityCover title={author?.name} cover={author?.image || AuthorCoverImg} />

                <div className="col-12 col-md-7 col-lg-8 d-flex flex-column">
                    <p className="fw-bold fs-3 d-none d-md-block">{author?.name}</p>
                    <div className="row mb-auto">
                        <div className="col-4 col-lg">
                            <p className="mb-1 fs-7 text-secondary">PenName</p>
                            <p className="text-truncate">{author?.penName}</p>
                        </div>
                        <div className="col-4 col-lg">
                            <p className="mb-1 fs-7 text-secondary">Nationality</p>
                            <p className="text-truncate">{author?.nationality}</p>
                        </div>
                        <div className="col-4 col-lg">
                            <p className="mb-1 fs-7 text-secondary">Born</p>
                            <p className="text-truncate">{author?.born}</p>
                        </div>
                        <div className="col-4 col-lg">
                            <p className="mb-1 fs-7 text-secondary">Died</p>
                            <p className="text-truncate">{author?.died || "-"}</p>
                        </div>
                        <div className="col-4 col-lg">
                            <p className="mb-1 fs-7 text-secondary">Books</p>
                            <p className="text-truncate">{author?.bookCount}</p>
                        </div>
                    </div>
                </div>
            </section>
            <section className="container mb-5">
                <BookCardContainer bookList={books} />
            </section>
        </main>
    );
};

export default Author;