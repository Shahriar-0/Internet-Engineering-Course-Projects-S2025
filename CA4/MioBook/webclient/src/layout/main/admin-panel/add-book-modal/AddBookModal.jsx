import Modal from "library/modal/Modal";
import CustomInput from "library/form-assets/CustomInput";
import { useEffect, useState } from "react";
import styles from "./AddBookModal.module.css";
import { canSubmit, getInitialStateOfAddBookModal } from "./AddBookModalLogic";
import ApiService from "services/ApiService";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import UrlService from "services/UrlService";

const AddBookModal = ({ isOpen, onClose, onSubmit }) => {

    const [page, setPage] = useState(1);
    const [state, setState] = useState(getInitialStateOfAddBookModal());

    const navigate = useNavigate();

    useEffect(() => {
        setState(getInitialStateOfAddBookModal());
        setPage(1);
    }, [isOpen]);

    const submit = async () => {
        const { body } = await ApiService.addBook(
            state.name,
            state.author,
            state.publisher,
            state.genres.split(','),
            state.publishedYear,
            state.price,
            state.synopsis,
            state.content,
            state.imageLink
        );

        if (body == null)
            navigate(UrlService.urls.unexpectedError);
        else if (body.status !== ApiService.statusCode.CREATED)
            toast.error(body.message);
        else {
            toast.success("Book added successfully.");
            if (onSubmit) onSubmit();
        }

        onClose();
    }

    return (
        <Modal className="bg-custom-white p-3 w-50 w-xl-33 rounded-3 container"
            isOpen={isOpen} onClose={onClose}
        >
            <div className="d-flex mb-5">
                <p className="fs-4 mb-0 ms-auto">Add Author</p>
                <p className="fs-4 pointer mb-0 ms-auto align-self-start" onClick={onClose}>✕</p>
            </div>

            <div className={styles["add-book-form"]}>
                <CustomInput onChange={(e) => setState({ ...state, name: e.target.value })}
                    className={`form-control mb-3 ${page === 2 ? "d-none" : ""}`} placeHolder="Name" />
                <CustomInput onChange={(e) => setState({ ...state, author: e.target.value })}
                    className={`form-control mb-3 ${page === 2 ? "d-none" : ""}`} placeHolder="Author" />
                <CustomInput onChange={(e) => setState({ ...state, publisher: e.target.value })}
                    className={`form-control mb-3 ${page === 2 ? "d-none" : ""}`} placeHolder="Publisher" />
                <CustomInput onChange={(e) => setState({ ...state, genres: e.target.value })}
                    className={`form-control mb-3 ${page === 2 ? "d-none" : ""}`} placeHolder="genres" />
                <CustomInput onChange={(e) => setState({ ...state, publishedYear: e.target.value })}
                    type="number" className={`form-control mb-3 ${page === 2 ? "d-none" : ""}`} placeHolder="Published Year" />
                <CustomInput onChange={(e) => setState({ ...state, price: e.target.value })}
                    type="number" className={`form-control mb-3 ${page === 2 ? "d-none" : ""}`} placeHolder="Price" />
                <CustomInput onChange={(e) => setState({ ...state, imageLink: e.target.value })}
                    className={`form-control mb-3 ${page === 2 ? "d-none" : ""}`} placeHolder="Image Link" />
                <textarea onChange={(e) => setState({ ...state, synopsis: e.target.value })}
                    className={`${styles["synopsis-textarea"]} form-control mb-3 ${page === 1 ? "d-none" : ""}`} placeholder="Synopsis" />
                <textarea onChange={(e) => setState({ ...state, content: e.target.value })}
                    className={`${styles["content-textarea"]} form-control mb-3 ${page === 1 ? "d-none" : ""}`} placeholder="Content" />
            </div>

            <div className="d-flex flex-column">
                <button onClick={() => setPage(2)} className={`btn green-btn mb-2 ${page === 2 ? "d-none" : ""}`}>Next</button>
                <button onClick={onClose} className={`btn green-btn mb-2 ${page === 2 ? "d-none" : ""}`}>Cancel</button>
                <button onClick={submit} className={`btn green-btn mb-2 ${page === 1 ? "d-none" : ""}`} disabled={!canSubmit(state)}>Submit</button>
                <button onClick={() => setPage(1)} className={`btn green-btn mb-2 ${page === 1 ? "d-none" : ""}`}>Back</button>
            </div>

        </Modal>
    );
}

export default AddBookModal;
