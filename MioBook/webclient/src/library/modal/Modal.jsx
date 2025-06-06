import styles from './Modal.module.css'

const Modal = ({ isOpen, onClose, children, ...props }) => {

    if (!isOpen) return <></>;
    return (
        <div className={styles.overlay} onClick={onClose}>
            <div onClick={(e) => e.stopPropagation()}
                {...props}
            >
                {children}
            </div>
        </div>
    );
}

export default Modal;
