import OpenIcon from "./assets/open-icon.svg";
import CloseIcon from "./assets/close-icon.svg";

const AccordionItem = ({ children, isOpen, index, onToggle, headerText, headerTextClassName, headerClassName }) => {
    return (
        <div>
            <div className={`bg-gray d-flex justify-content-between align-items-center p-2 ${headerClassName}`}>
                <p className={`mb-0 ${headerTextClassName}`}>{headerText}</p>
                <img  className="pointer" onClick={() => onToggle(index)} src={isOpen ? OpenIcon : CloseIcon} alt="open-icon"/>
            </div>
            { isOpen ? children : <></>}
        </div>
    );
}

export default AccordionItem;
