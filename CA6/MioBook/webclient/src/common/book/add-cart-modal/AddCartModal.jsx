import Modal from "library/modal/Modal";
import SpinnerButton from "library/spinner-button/SpinnerButton";
import { getModalInitialState } from "./AddCartModalLogic";
import { useEffect, useState } from "react";
import ApiService from "services/ApiService";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import UrlService from "services/UrlService";
import styles from "./AddCartModal.module.css";

const AddCartModal = ({ isOpen, onClose, title, price }) => {
    const navigate = useNavigate();

    const [state, setState] = useState(getModalInitialState());

    useEffect(() => {
        setState(getModalInitialState());
    }, [isOpen]);

    const submit = async () => {
        if (state.isBorrowing) {
            const { body, error } = await ApiService.borrowCart(title, state.borrowedDays);
            if (body && body.status === ApiService.statusCode.CREATED)
                toast.success(body.message);
            else if (body && body.status !== ApiService.statusCode.CREATED)
                toast.error(body.message);
            else
                navigate(UrlService.urls.unexpectedError);
        }
        else {
            const { body, error } = await ApiService.addToCart(title);
            if (body && body.status === ApiService.statusCode.CREATED)
                toast.success(body.message);
            else if (body && body.status !== ApiService.statusCode.CREATED)
                toast.error(body.message);
            else
                navigate(UrlService.urls.unexpectedError);
        }
        onClose();
    }

    return (
        <Modal className="bg-custom-white p-3 w-50 w-xl-33 rounded-3 container"
            isOpen={isOpen} onClose={onClose}
        >
            <div className="d-flex mb-5">
                <p className="fs-4 mb-0 ms-auto">Add to Cart</p>
                <p className="fs-4 pointer mb-0 ms-auto align-self-start" onClick={onClose}>✕</p>
            </div>

            <div className="form-check mb-3">
                <input
                    className="form-check-input"
                    type="checkbox"
                    id="isBorrowingCheckbox"
                    checked={state.isBorrowing}
                    onChange={(e) => setState({ ...state, isBorrowing: e.target.checked })}
                />
                <label className="form-check-label" htmlFor="isBorrowingCheckbox">
                    Borrow this book
                </label>
            </div>

            {state.isBorrowing && (
                <div className="row">
                    {Array.from({ length: 9 }, (_, i) => (
                        <div key={i} className="col-4 mb-2">
                            <button key={i}
                                onClick={() => setState({ ...state, borrowedDays: i + 1 })}
                                className={`btn btn-sm w-100 py-2
                        ${i + 1 === state.borrowedDays ? styles["enable-day-picker-btn"] : styles["disable-day-picker-btn"]}`}
                            >{i + 1} Days</button>
                        </div>
                    ))}
                </div>
            )}
            <div className="d-flex justify-content-between align-items-center">
                <p className="mb-0">Final Price: ${state.isBorrowing ? price * state.borrowedDays / 10 : price}</p>
                <SpinnerButton onClick={submit} className="btn green-btn px-5">Add</SpinnerButton>
            </div>
        </Modal>
    );
};

export default AddCartModal;