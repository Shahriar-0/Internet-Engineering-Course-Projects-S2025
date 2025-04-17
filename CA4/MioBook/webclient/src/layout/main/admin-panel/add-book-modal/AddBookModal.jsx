import Modal from "library/modal/Modal";
import CustomInput from "library/form-assets/CustomInput";
import {useState} from "react";
import styles from "./AddBookModal.module.css";

const AddBookModal = ({isOpen, onClose, onSubmit}) => {

    const [page, setPage] = useState(1);
    const [state, setState] = useState();

    const submit = async () => {
        console.log(state);
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
                <CustomInput className={`form-control mb-3 ${page === 2 ? "d-none" : ""}`} placeHolder="Name" />
                <CustomInput className={`form-control mb-3 ${page === 2 ? "d-none" : ""}`} placeHolder="Author" />
                <CustomInput className={`form-control mb-3 ${page === 2 ? "d-none" : ""}`} placeHolder="Publisher" />
                <CustomInput className={`form-control mb-3 ${page === 2 ? "d-none" : ""}`} placeHolder="genres" />
                <CustomInput className={`form-control mb-3 ${page === 2 ? "d-none" : ""}`} placeHolder="Published Year" />
                <CustomInput className={`form-control mb-3 ${page === 2 ? "d-none" : ""}`} placeHolder="Price" />
                <CustomInput className={`form-control mb-3 ${page === 2 ? "d-none" : ""}`} placeHolder="Image Link" />
                <textarea className={`${styles["synopsis-textarea"]} form-control mb-3 ${page === 1 ? "d-none" : ""}`} placeholder="Synopsis" />
                <textarea className={`${styles["content-textarea"]} form-control mb-3 ${page === 1 ? "d-none" : ""}`} placeholder="Content" />
            </div>

            <div className="d-flex flex-column">
                <button onClick={()=>setPage(2)} className={`btn green-btn mb-2 ${page === 2 ? "d-none" : ""}`}>Next</button>
                <button onClick={onClose} className={`btn green-btn mb-2 ${page === 2 ? "d-none" : ""}`}>Cancel</button>
                <button onClick={submit} className={`btn green-btn mb-2 ${page === 1 ? "d-none" : ""}`}>Submit</button>
                <button onClick={()=>setPage(1)} className={`btn green-btn mb-2 ${page === 1 ? "d-none" : ""}`}>Back</button>
            </div>

        </Modal>
    );
}

export default AddBookModal;
