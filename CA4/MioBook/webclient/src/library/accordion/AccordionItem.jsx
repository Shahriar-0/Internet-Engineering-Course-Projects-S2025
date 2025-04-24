import OpenIcon from "./assets/open-icon.svg";
import CloseIcon from "./assets/close-icon.svg";

const AccordionItem = ({ children, isOpen, index, onToggle, headerText, headerClassName, activeHeaderClassName, ...props }) => {
    return (
        <div {...props}>
            <div onClick={() => onToggle(index)}
                className={`d-flex justify-content-between align-items-center p-2 pointer ${isOpen ? activeHeaderClassName : headerClassName}`}>
                <p className="mb-0">{headerText}</p>
                <img src={isOpen ? OpenIcon : CloseIcon} alt="open-icon" />
            </div>
            {isOpen ? children : <></>}
        </div>
    );
}

export default AccordionItem;
