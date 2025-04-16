import {useState} from "react";

const OrderPicker = ({onChange, className}) => {
    const activeStyle = "flex-grow-1 btn border-3 fw-bold position-relative bg-khaki text-green border-green";
    const inactiveStyle = "flex-grow-1 btn border-3 fw-bold position-relative bg-gray";

    const [isAscending, setIsAscending] = useState(true);

    const setOrderDescending = () => {
        onChange(false);
        setIsAscending(false);
    }

    const setOrderAscending = () => {
        onChange(true);
        setIsAscending(true);
    }

    return (
        <div className={`d-flex ${className}`}>
            <button className={`me-1 ${isAscending === false ? activeStyle : inactiveStyle}`}
                    onClick={setOrderDescending}>
                Descending
            </button>

            <button className={`ms-1 ${isAscending === true ? activeStyle : inactiveStyle}`}
                    onClick={setOrderAscending}>
                Ascending
            </button>
        </div>
    );
}

export default OrderPicker;
