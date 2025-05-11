export const getAddAuthorModalInitialState = () => {
    return {
        name: '',
        penName: '',
        nationality: '',
        born: '',
        died: '',
        imageLink: '',
    };
}

export const canSubmit = (author) => {
    if (!author) return false;

    return (
        author.name &&
        author.penName &&
        author.nationality &&
        author.born
    );
}
