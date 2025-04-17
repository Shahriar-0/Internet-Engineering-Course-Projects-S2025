import Modal from "library/modal/Modal";
import CustomInput from "library/form-assets/CustomInput";
import SpinnerButton from "library/spinner-button/SpinnerButton";
import {getAddAuthorModalInitialState, hasEmptyFields} from "./AddAuthorModalLogic";
import {useEffect, useState} from "react";

const AddAuthorModal = ({isOpen, onClose, onSubmit}) => {
    const [state, setState] = useState(getAddAuthorModalInitialState());

    useEffect(() => {
        setState(getAddAuthorModalInitialState());
    }, [isOpen]);

    return (
        <Modal className="bg-custom-white p-3 w-50 w-xl-33 rounded-3 container"
               isOpen={isOpen} onClose={onClose}
        >

            <div className="d-flex mb-5">
                <p className="fs-4 mb-0 ms-auto">Add Author</p>
                <p className="fs-4 pointer mb-0 ms-auto align-self-start" onClick={onClose}>✕</p>
            </div>

            <div>
                <CustomInput onChange={(e) => setState({...state, name: e.target.value})}
                             className="form-control mb-3" placeholder="Name" />
                <CustomInput onChange={(e) => setState({...state, penName: e.target.value})}
                             className="form-control mb-3" placeholder="Pen Name" />
                <CustomInput onChange={(e) => setState({...state, nationality: e.target.value})}
                             className="form-control mb-3" placeholder="Nationality" />
                <CustomInput type="date" onChange={(e) => setState({...state, born: e.target.value})}
                             className="form-control mb-3" placeholder="Born" />
                <CustomInput type="date" onChange={(e) => setState({...state, died: e.target.value})}
                             className="form-control mb-3" placeholder="Died" />
                <CustomInput onChange={(e) => setState({...state, imageLink: e.target.value})}
                             className="form-control mb-3" placeholder="Image Link" />
                <SpinnerButton disabled={hasEmptyFields(state)} className="btn green-btn w-100 mb-2"
                               onClick={() => onSubmit(state)}>Submit</SpinnerButton>
                <button onClick={onClose} className="btn green-btn w-100">Cancel</button>
            </div>

        </Modal>
    );
}

export default AddAuthorModal;
