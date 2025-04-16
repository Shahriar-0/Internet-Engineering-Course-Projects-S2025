import Modal from "library/modal/Modal";
import CustomInput from "library/form-assets/CustomInput";
import SortByPicker from "./SortByPicker";
import OrderPicker from "./OrderPicker";

const BookFilterModal = ({isOpen, onClose}) => {
    return (
        <Modal className="d-flex flex-column overflow-auto w-100 w-md-75 w-lg-66 w-xl-50 w-xxl-33 h-100 bg-custom-white align-self-start me-auto p-4"
               isOpen={isOpen} onClose={onClose}
        >
            <p className="text-end fs-2 pointer mb-0" onClick={onClose}>✕</p>
            <p className="text-center fw-bold fs-2 mt-5">Filters</p>
            <hr className="mx-4 border-2 opacity-100"/>
            <div className="">
                <div className="d-flex flex-wrap align-items-center mb-4">
                    <p className="w-100 w-sm-25 mb-0">Book Name:</p>
                    <CustomInput className="form-control w-100 w-sm-75" />
                </div>
                <div className="d-flex flex-wrap align-items-center mb-4">
                    <p className="w-100 w-sm-25 mb-sm-0">Author Name:</p>
                    <CustomInput className="form-control w-100 w-sm-75" />
                </div>
                <div className="d-flex flex-wrap align-items-center mb-4">
                    <p className="w-100 w-sm-25 mb-0">Genre:</p>
                    <CustomInput className="form-control w-100 w-sm-75" />
                </div>
                <div className="d-flex flex-wrap align-items-center mb-4">
                    <p className="w-100 w-sm-25 mb-0">Published Year:</p>
                    <CustomInput className="form-control w-100 w-sm-75" />
                </div>
                <div className="d-flex flex-wrap align-items-center mb-4">
                    <p className="w-100 w-sm-25 mb-0">Sort By:</p>
                    <SortByPicker onChange={(e)=>{console.log(e)}} className="w-100 w-sm-75" />
                </div>
                <div className="d-flex flex-wrap align-items-center mb-4">
                    <p className="w-100 w-sm-25 mb-0">Order:</p>
                    <OrderPicker onChange={(e)=>{console.log(e)}} className="w-100 w-sm-75" />
                </div>
            </div>
            <button className="btn btn-lg green-btn mt-auto mx-5">Apply</button>
        </Modal>
    )
}

export default BookFilterModal;
