export const getInitialStateOfAddBookModal = () => {
    return {
        name: '',
        author: '',
        publisher: '',
        genres: '',
        publishedYear: '',
        price: '',
        imageLink: '',
        synopsis: '',
        content: ''
    };
}

export const canSubmit = (book) => {
    if (!book) return false;

    return (
        book.name &&
        book.author &&
        book.publisher &&
        book.genres &&
        book.publishedYear &&
        book.price &&
        book.synopsis &&
        book.content
    );
}
