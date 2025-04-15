import shelfImage from "assets/images/home/shelf.svg";

const WelcomeSection = () => {
    return (
        <section className="bg-khaki py-5 mb-5">
            <div className="container row justify-content-center mx-auto">
                <div className="col-12 col-lg-6 col-xxl-4 ps-5 order-lg-2 d-flex justify-content-center">
                    <img src={shelfImage} alt="shelf" className="ps-lg-5"/>
                </div>
                <div className="col-12 col-lg-6 col-xxl-4 order-lg-1">
                    Welcome to MioBook – the online bookstore where you can buy or borrow books with ease.
                    <br/><br/>
                    Whether you're looking for the latest bestseller, a classic novel, or a niche title, MioBook has
                    you covered.
                    <br/><br/>
                    Here, you can quickly find books by title, author, and genre. And if you’re not sure to buy? Try
                    borrowing instead! Rent a book for just a fraction of the price and enjoy full access for a set
                    period.
                    <br/><br/>
                    Your next great read is just a click away. Visit MioBook today and let the perfect book find
                    you! 📚✨
                </div>
            </div>
        </section>
    );
}

export default WelcomeSection;
