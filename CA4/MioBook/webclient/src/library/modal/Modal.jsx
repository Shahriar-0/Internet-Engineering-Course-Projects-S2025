import styles from './Modal.module.css'

const Modal = ({isOpen, onClose, className, children, ...props}) => {

    if (!isOpen) return <></>;
    return (
        <div className={styles.overlay} onClick={onClose}>
            <div onClick={(e) => e.stopPropagation()}
                 className={className}
                 {...props}
            >
                {children}
            </div>
        </div>
    );
}

export default Modal;
