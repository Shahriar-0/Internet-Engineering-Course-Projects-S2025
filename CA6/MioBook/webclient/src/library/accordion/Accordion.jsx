import React, { useState } from "react";
import AccordionItem from "./AccordionItem";

const Accordion = ({ children, className }) => {

    const [openItemIndex, setOpenItemIndex] = useState(-1);

    const onToggle = (index) => {
        if (index === openItemIndex)
            setOpenItemIndex(-1);
        else
            setOpenItemIndex(index);
    }

    const annotateChildren = () => {
        let currentIndex = 0;
        return (
            <>
                {React.Children.map(children, child => {
                    if (React.isValidElement(child) && child.type === AccordionItem) {
                        return React.cloneElement(child, {
                            isOpen: currentIndex === openItemIndex,
                            index: currentIndex++,
                            onToggle: onToggle
                        });
                    }
                })}
            </>
        );
    }

    return (
        <div className={`d-flex flex-column ${className}`}>
            {annotateChildren()}
        </div>
    )
}

export default Accordion;
