import Accordion from "library/accordion/Accordion";
import AccordionItem from "library/accordion/AccordionItem";
import HistoryIcon from "assets/icons/purchased-history-icon.svg";

const PurchasedHistory = () => {
    return (
        <main className="container">
            <section className="shadow rounded-3 p-3 mb-4">
                <p className="fs-3 fw-bold d-flex align-items-center">
                    <img className="me-2" src={HistoryIcon} alt="books-icon" /> History
                </p>

                <Accordion className="rounded border bg-custom-white">
                    <AccordionItem headerText="Book" headerClassName="bg-gray" activeHeaderClassName="bg-khaki text-green rounded-top" className="border rounded-top">
                        <p className="fw-bold mb-0">Book Title</p>
                    </AccordionItem>
                    <AccordionItem headerText="Book" headerClassName="bg-gray" activeHeaderClassName="bg-khaki text-green" className="border">
                        <p className="fw-bold mb-0">Book Title</p>
                    </AccordionItem>
                    <AccordionItem headerText="Book" headerClassName="bg-gray" activeHeaderClassName="bg-khaki text-green" className="border">
                        <p className="fw-bold mb-0">Book Title</p>
                    </AccordionItem>
                    <AccordionItem headerText="Book" headerClassName="bg-gray" activeHeaderClassName="bg-khaki text-green" className="border rounded-bottom">
                        <p className="fw-bold mb-0">Book Title</p>
                    </AccordionItem>
                </Accordion>
            </section>
        </main>
    );
}

export default PurchasedHistory;
