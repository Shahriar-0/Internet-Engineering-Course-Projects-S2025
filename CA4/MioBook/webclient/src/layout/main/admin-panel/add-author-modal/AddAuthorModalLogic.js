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

export const hasEmptyFields = (author) => {
    if (!author) return true;

    return (
        !author.name ||
        !author.penName ||
        !author.nationality ||
        !author.born ||
        !author.died ||
        !author.imageLink
    );
}
