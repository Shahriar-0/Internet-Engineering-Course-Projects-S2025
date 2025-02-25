document.addEventListener("DOMContentLoaded", () => {
    const passwordField = document.getElementById("passwordField");
    const toggleIcon = document.querySelector(".toggle-password");

    toggleIcon.addEventListener("click", () => {
        if (passwordField.type === "password") {
            passwordField.type = "text";
            toggleIcon.classList.replace("fa-eye-slash", "fa-eye");
        } else {
            passwordField.type = "password";
            toggleIcon.classList.replace("fa-eye", "fa-eye-slash");
        }
    });
});
