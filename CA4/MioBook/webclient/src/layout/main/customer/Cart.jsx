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
            if (error)
                toast.error(error);
            else if (body && body.status === ApiService.statusCode.OK)
                setItems(body.data.items);
            else
                navigate(UrlService.urls.unexpectedError);
        };

        fetchCart();
    }, [navigate]);

    console.log(items);

    return (
        <main class="d-flex flex-column align-items-center">
            <section class="container row mb-5">
                <div class="col-12">
                    <div class="shadow rounded-3 p-3">
                        <p class="fw-bold fs-3">
                            <img src={CartIcon} alt="buy-basket" />
                            Cart
                        </p>
                        <div class="text-center">
                            {items && items.length > 0 ? (
                                <BookList bookList={items} />
                            ) : (
                                <img src={NoProduct} alt="no-product" />
                            )}
                        </div>
                        <button class="btn w-100 w-md-auto ms-auto green-btn d-block mx-auto fs-5">Purchase</button>
                    </div>
                </div>

            </section>
        </main>
    );
};

export default Cart;