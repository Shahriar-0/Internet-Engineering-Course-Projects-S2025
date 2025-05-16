import { useEffect, useState } from "react";
import WelcomeSection from "./WelcomeSection";
import BookCardContainer from "common/book/BookCardContainer";
import ApiService from "services/ApiService";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import UrlService from "services/UrlService";

const Home = () => {

    const [newBooks, setNewBooks] = useState(null);
    const [topBooks, setTopBooks] = useState(null);

    const navigate = useNavigate();

    const DEFAULT_PAGE_SIZE = 5;


    useEffect(() => {
        const fetchNewBooks = async () => {
            const { body, error } = await ApiService.searchBooks({ sortBy: "date", isAscending: false, pageSize: DEFAULT_PAGE_SIZE });
            if (body && body.status === ApiService.statusCode.OK)
                setNewBooks(body.data.list);
            else if (body && body.status !== ApiService.statusCode.OK)
                toast.error(body.message);
            else
                navigate(UrlService.urls.unexpectedError);
        };

        const fetchTopBooks = async () => {
            const { body, error } = await ApiService.searchBooks({ sortBy: "rating", isAscending: false, pageSize: DEFAULT_PAGE_SIZE });
            if (body && body.status === ApiService.statusCode.OK)
                setTopBooks(body.data.list);
            else if (body && body.status !== ApiService.statusCode.OK)
                toast.error(body.message);
            else
                navigate(UrlService.urls.unexpectedError);
        };

        fetchNewBooks();
        fetchTopBooks();
    }, [navigate]);

    return (
        <main>
            <WelcomeSection />

            <section className="container mb-5">
                <p className="fs-3">New Releases</p>
                <BookCardContainer bookList={newBooks} />
            </section>

            <section className="container mb-5">
                <p className="fs-3">Top Rated</p>
                <BookCardContainer bookList={topBooks} />
            </section>
        </main>
    );
}

export default Home;
