import Accordion from "library/accordion/Accordion";
import AccordionItem from "library/accordion/AccordionItem";
import HistoryIcon from "assets/icons/purchased-history-icon.svg";
import NoResult from "assets/images/user/no-result.svg";
import { useEffect, useState } from "react";
import ApiService from "services/ApiService";
import UrlService from "services/UrlService";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import BookList from "common/user/BookList";

const PurchasedHistory = () => {

    const [purchasedList, setPurchasedList] = useState([]);

    const navigate = useNavigate();

    useEffect(() => {
        fetchPurchasedHistory();
    }, []);

    const fetchPurchasedHistory = async () => {
        const { body } = await ApiService.getPurchasedHistory();
        if (body && body.status === ApiService.statusCode.OK)
            setPurchasedList(body.data.purchaseHistory);
        else if (body && body.status !== ApiService.statusCode.OK)
            toast.error(body.message);
        else
            navigate(UrlService.urls.unexpectedError);
    }

    const formatDateTime = (dateTimeStr) => {
        const date = new Date(dateTimeStr);

        const yyyy = date.getFullYear();
        const mm = String(date.getMonth() + 1).padStart(2, '0');
        const dd = String(date.getDate()).padStart(2, '0');
        const hh = String(date.getHours()).padStart(2, '0');
        const min = String(date.getMinutes()).padStart(2, '0');

        return `${yyyy}-${mm}-${dd} ${hh}:${min}`;
    }

    return (
        <main className="container">
            <section className="shadow rounded-3 p-3 mb-4">
                <p className="fs-3 fw-bold d-flex align-items-center">
                    <img className="me-2" src={HistoryIcon} alt="books-icon" /> History
                </p>

                {purchasedList.length > 0 ? (
                    <Accordion className="rounded border bg-custom-white">
                        {purchasedList.map((purchase) => (
                            <AccordionItem headerText={`${formatDateTime(purchase.datePurchased)} | $${purchase.totalCost}`} headerClassName="bg-gray"
                                activeHeaderClassName="bg-khaki text-green rounded-top" className="border rounded-top"
                            >
                                <div className="p-3 pb-0">
                                    <BookList bookList={purchase.books} />
                                </div>
                            </AccordionItem>
                        ))}
                    </Accordion>
                ) : (
                    <div className="text-center">
                        <img src={NoResult} alt="no-result" />
                    </div>
                )}
            </section>
        </main>
    );
}

export default PurchasedHistory;
