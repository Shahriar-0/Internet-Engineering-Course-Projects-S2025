import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import ApiService from "services/ApiService";
import UrlService from "services/UrlService";
import NoProduct from "assets/images/user/no-product.svg";
import CartIcon from "assets/icons/cart-icon.svg";
import BookList from "common/user/BookList";

const Cart = () => {
    const [items, setItems] = useState(null);

    const navigate = useNavigate();

    useEffect(() => {
        const fetchCart = async () => {
            const { body, error } = await ApiService.getCart();
            if (body && body.status === ApiService.statusCode.OK)
                setItems(body.data.items);
            else if (body && body.status !== ApiService.statusCode.OK)
                toast.error(body.message);
            else
                navigate(UrlService.urls.unexpectedError);
        };

        fetchCart();
    }, [navigate]);

    const deleteFromCart = async (title) => {
        const { body, error } = await ApiService.deleteFromCart(title);
        if (body && body.status === ApiService.statusCode.OK) {
            toast.success(body.message);
            setItems((prevItems) => prevItems.filter((item) => item.title !== title));
        }
        else if (body && body.status !== ApiService.statusCode.OK)
            toast.error(body.message);
        else
            navigate(UrlService.urls.unexpectedError);
    }

    const purchase = async () => {
        const { body, error } = await ApiService.purchaseCart();
        if (body && body.status === ApiService.statusCode.CREATED) {
            toast.success("You have successfully purchased your cart.");
            setItems(null);
        }
        else if (body && body.status !== ApiService.statusCode.CREATED)
            toast.error(body.message);
        else
            navigate(UrlService.urls.unexpectedError);
    }

    return (
        <main class="d-flex flex-column align-items-center">
            <section class="container row mb-5">
                <div class="col-12">
                    <div class="shadow rounded-3 p-3">
                        <p class="fw-bold fs-3 flex align-items-center">
                            <img className="me-2" src={CartIcon} alt="buy-basket" />
                            Cart
                        </p>
                        <div class="text-center">
                            {items && items.length > 0 ? (
                                <BookList bookList={items} action={deleteFromCart} actionName={"Remove"} />
                            ) : (
                                <img src={NoProduct} alt="no-product" />
                            )}
                        </div>
                        <button onClick={purchase} class="btn w-100 w-md-auto ms-auto green-btn d-block mx-auto fs-5">Purchase</button>
                    </div>
                </div>

            </section>
        </main>
    );
};

export default Cart;