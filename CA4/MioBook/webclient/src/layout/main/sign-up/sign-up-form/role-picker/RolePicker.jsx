import customerIcon from "assets/icons/customer-role-icon.svg";
import adminIcon from "assets/icons/admin-role-icon.svg";
import {useState} from "react";

const RolePicker = () => {
    const role = Object.freeze({
        customer: "customer",
        admin: "admin"
    })

    const activeStyle = "btn btn-lg border-3 fw-bold w-100 position-relative bg-khaki text-green border-green";
    const inactiveStyle = "btn btn-lg border-3 fw-bold w-100 position-relative bg-gray";

    const [roleValue, setRoleValue] = useState(role.customer);



    return (
        <>
            <p className="fw-bold text-start p-0 mb-1 w-sm-100">I am</p>
            <div className="mb-2 mb-sm-3 col-12 col-sm-6 px-0 pe-sm-1">
                <button className={roleValue === role.customer ? activeStyle : inactiveStyle}
                        onClick={() => setRoleValue(role.customer)}>
                    <i className="position-absolute top-50 start-0 translate-middle-y ms-2 ms-xl-4 ms-xxl-5"><img
                        src={customerIcon} alt="customer-icon"/></i>
                    Customer
                </button>
            </div>

            <div className="mb-3 col-12 col-sm-6 px-0 ps-sm-1">
                <button className={roleValue === role.admin ? activeStyle : inactiveStyle}
                        onClick={() => setRoleValue(role.admin)}>
                    <i className="position-absolute top-50 start-0 translate-middle-y ms-2 ms-xl-4 ms-xxl-5"><img
                        src={adminIcon} alt="manager-icon"/></i>
                    Admin
                </button>
            </div>
        </>
    );
}

export default RolePicker;
