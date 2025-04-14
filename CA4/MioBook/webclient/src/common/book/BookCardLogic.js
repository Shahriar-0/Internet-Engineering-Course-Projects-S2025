export const transformBook = (book) => {
    return {
        title: book.title,
        author: book.author,
        price: book.price,
        averageRating: book.averageRating,
        cover: book.cover
    }
}

export const getBookCover = (bookCover) => {
    return new Promise((resolve, reject) => {
        const image = new Image();
        image.src = bookCover;
        image.onload = () => resolve(image);
        image.onerror = (error) => reject(error);
    });
}
