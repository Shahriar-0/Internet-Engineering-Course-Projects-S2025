import BookCard from "./BookCard";

const BookCardContainer = ({bookList}) => {
    return (
        <div className="row row-cols-1 row-cols-sm-2 row-cols-md-3 row-cols-lg-4 row-cols-xl-5">
            {bookList.map((book, index) => (
                <div className="mb-2" key={index}>
                    <BookCard key={index}  {...book}/>
                </div>
            ))}
        </div>
    );
}

export default BookCardContainer;
