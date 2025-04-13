import WelcomeSection from "./WelcomeSection";
import BookCardContainer from "common/book/BookCardContainer";

const Home = () => {
    const tempBookList = Array.from({ length: 5 }, (_, i) => ({
        bookTitle: "Book Title",
        authorName: "Author McName",
        price: 10.25,
        rating: 4,
    }));

    return (
        <main>
            <WelcomeSection />

            <section className="container mb-5">
                <p className="fs-3">New Releases</p>
                <BookCardContainer bookList={tempBookList}  />
            </section>

            <section className="container mb-5">
                <p className="fs-3">Top Rated</p>
                <BookCardContainer bookList={tempBookList}  />
            </section>
        </main>
    );
}

export default Home;
