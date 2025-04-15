import WelcomeSection from "./WelcomeSection";
import BookCardContainer from "common/book/BookCardContainer";

const Home = () => {
    return (
        <main>
            <WelcomeSection />

            <section className="container mb-5">
                <p className="fs-3">New Releases</p>
                <BookCardContainer url="/new" />
            </section>

            <section className="container mb-5">
                <p className="fs-3">Top Rated</p>
                <BookCardContainer url="/top" />
            </section>
        </main>
    );
}

export default Home;
