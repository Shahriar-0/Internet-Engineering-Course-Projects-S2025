import Modal from "library/modal/Modal";
import CustomInput from "library/form-assets/CustomInput";
import SortByPicker from "./SortByPicker";
import OrderPicker from "./OrderPicker";
import {useEffect, useState} from "react";
import GenreSelector from "./GenreSelector";
import {useNavigate} from "react-router-dom";
import ApiService from "services/ApiService";
import UrlService from "services/UrlService";
import {getInitialFilterState} from "./BookFilterLogic";

const BookFilterModal = ({state, isOpen, onClose, onApply}) => {

    const [filterState, setFilterState] = useState(state);
    const [genreList, setGenreList] = useState([]);
    const navigate = useNavigate();

    const onApplyClick = () => {
        onApply(filterState);
        onClose();
    }

    const onResetClick = () => {
        const initialState = getInitialFilterState();
        setFilterState(initialState);
    };

    useEffect(() => {
       setFilterState(state);
        fetchGenres();
    }, [isOpen]);

    const fetchGenres = async () => {
        const {body} = await ApiService.getGenres();
        if (body === null || body.status !== ApiService.statusCode.OK) {
            navigate(UrlService.urls.unexpectedError);
            return;
        }
        setGenreList(body.data);
    }

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
                    <CustomInput onChange={(e) => {setFilterState({...filterState, bookName: e.target.value})}}
                                 className="form-control w-100 w-sm-75"
                                 value={filterState.bookName}/>
                </div>
                <div className="d-flex flex-wrap align-items-center mb-4">
                    <p className="w-100 w-sm-25 mb-0">Author Name:</p>
                    <CustomInput onChange={(e) => {setFilterState({...filterState, authorName: e.target.value})}}
                                 className="form-control w-100 w-sm-75"
                                 value={filterState.authorName}/>
                </div>
                <div className="d-flex flex-wrap align-items-center mb-4">
                    <p className="w-100 w-sm-25 mb-0">Genre:</p>
                    <GenreSelector onChange={(value) => {setFilterState({...filterState, genre: value})}}
                                   className="form-control w-100 w-sm-75"
                                   genreList={genreList}
                                   value={filterState.genre}/>
                </div>
                <div className="d-flex flex-wrap align-items-center mb-4">
                    <p className="w-100 w-sm-25 mb-0">Published Year:</p>
                    <CustomInput type="number" onChange={(e) => {setFilterState({...filterState, publishedYear: e.target.value})}}
                                 className="form-control w-100 w-sm-75"
                                 value={filterState.publishedYear}/>
                </div>
                <div className="d-flex flex-wrap align-items-center mb-4">
                    <p className="w-100 w-sm-25 mb-0">Sort By:</p>
                    <SortByPicker onChange={(e)=>{setFilterState({...filterState, sortBy: e})}}
                                  className="w-100 w-sm-75"
                                  state={filterState.sortBy}/>
                </div>
                <div className="d-flex flex-wrap align-items-center mb-4">
                    <p className="w-100 w-sm-25 mb-0">Order:</p>
                    <OrderPicker onChange={(e)=>{setFilterState({...filterState, isAscending: e})}}
                                 className="w-100 w-sm-75"
                                 state={filterState.isAscending}/>
                </div>
            </div>
            <div className="d-flex justify-content-center gap-3 mt-auto mx-5">
                <button onClick={onApplyClick} className="btn btn-lg green-btn">Apply</button>
                <button onClick={onResetClick} className="btn btn-lg red-btn">Reset</button>
            </div>
        </Modal>
    )
}

export default BookFilterModal;
