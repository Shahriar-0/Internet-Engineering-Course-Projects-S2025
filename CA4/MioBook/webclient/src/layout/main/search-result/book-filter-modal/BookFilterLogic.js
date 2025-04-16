export const getInitialFilterState = () => {
    return {
        bookName: "",
        authorName: "",
        genre: "",
        publishedYear: "",
        sortBy: "rating",
        order: "descending"
    };
}

