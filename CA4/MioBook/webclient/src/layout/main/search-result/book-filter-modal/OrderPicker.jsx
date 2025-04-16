import {useState} from "react";

const OrderPicker = ({onChange, className}) => {
    const order = Object.freeze({
        descending: "descending",
        ascending: "ascending"
    })

    const activeStyle = "flex-grow-1 btn border-3 fw-bold position-relative bg-khaki text-green border-green";
    const inactiveStyle = "flex-grow-1 btn border-3 fw-bold position-relative bg-gray";

    const [orderValue, setOrderValue] = useState(order.descending);

    const setOrderDescending = () => {
        onChange(order.descending);
        setOrderValue(order.descending);
    }

    const setOrderAscending = () => {
        onChange(order.ascending);
        setOrderValue(order.ascending);
    }

    return (
        <div className={`d-flex ${className}`}>
            <button className={`me-1 ${orderValue === order.descending ? activeStyle : inactiveStyle}`}
                    onClick={setOrderDescending}>
                Descending
            </button>

            <button className={`ms-1 ${orderValue === order.ascending ? activeStyle : inactiveStyle}`}
                    onClick={setOrderAscending}>
                Ascending
            </button>
        </div>
    );
}

export default OrderPicker;
