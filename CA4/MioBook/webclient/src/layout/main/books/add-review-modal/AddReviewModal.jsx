import Modal from "library/modal/Modal";
import RatingPicker from "./RatingPicker";
import styles from "./AddReviewModal.module.css";
import SpinnerButton from "library/spinner-button/SpinnerButton";
import { getModalInitialState } from "./AddReviewModalLogic";
import { useEffect, useState } from "react";
import ApiService from "services/ApiService";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import UrlService from "services/UrlService";

const AddReviewModal = ({ isOpen, onClose, onSubmit, title }) => {
    const navigate = useNavigate();

    const [state, setState] = useState(getModalInitialState());

    useEffect(() => {
        setState(getModalInitialState());
    }, [isOpen]);

    const canSubmit = () => {
        return state.rating !== 0 && state.review !== "";
    }

    const submit = async () => {
        const { body } = await ApiService.addReview(title, state.rating, state.review);

        if (body == null)
            navigate(UrlService.urls.unexpectedError);
        else if (body.status !== ApiService.statusCode.CREATED)
            toast.error(body.message);
        else {
            toast.success("Review added successfully.");
            if (onSubmit) onSubmit();
        }

        onClose();
    }

    return (
        <Modal className="bg-custom-white p-3 w-50 w-xl-33 rounded-3 container"
            isOpen={isOpen} onClose={onClose}
        >
            <div className="d-flex mb-5">
                <p className="fs-4 mb-0 ms-auto">Add Review</p>
                <p className="fs-4 pointer mb-0 ms-auto align-self-start" onClick={onClose}>✕</p>
            </div>

            <RatingPicker onChange={(index) => setState({ ...state, rating: index })} />
            <textarea onChange={(e) => setState({ ...state, review: e.target.value })}
                className={`form-control mb-3 ${styles["review-textarea"]}`} placeholder="Type your review..." />
            <SpinnerButton onClick={submit} className="btn green-btn w-100 mb-2" disabled={!canSubmit()}>Submit Review</SpinnerButton>
            <SpinnerButton onClick={onClose} className="btn green-btn w-100">Cancel</SpinnerButton>
        </Modal>
    );
};

export default AddReviewModal;