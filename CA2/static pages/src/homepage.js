const booksNew = document.getElementById('books-new');
const booksTop = document.getElementById('books-top');

const bookData = [
    { title: "Book Title", author: "Author McName", price: "$10.25", rating: 4 },
    { title: "Book Title", author: "Author McName", price: "$10.25", rating: 4 },
    { title: "Book Title", author: "Author McName", price: "$10.25", rating: 4 },
    { title: "Book Title", author: "Author McName", price: "$10.25", rating: 4 },
    { title: "Book Title", author: "Author McName", price: "$10.25", rating: 4 },
];

function createBookCard(book) {
    return `
    <div class="col-md-2 col-sm-6 mb-4">
        <div class="card">
            <img src="book-placeholder.jpg" class="card-img-top" alt="Book">
            <div class="card-body">
                <h5 class="card-title">${book.title}</h5>
                <p class="card-text">${book.author}</p>
                <p class="price">${book.price}</p>
                <button class="btn btn-success">Add to Cart</button>
            </div>
        </div>
    </div>
    `;
}

bookData.forEach(book => {
    booksNew.innerHTML += createBookCard(book);
    booksTop.innerHTML += createBookCard(book);
});
