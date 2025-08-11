document.addEventListener("DOMContentLoaded", () => {
    const username = document.getElementById("username");
    const password = document.getElementById("password");
    const loginBtn = document.getElementById("loginBtn");

    function toggleButton() {
        const isValid = username.value.trim() && password.value.trim();
        loginBtn.disabled = !isValid;
    }

    username.addEventListener("input", toggleButton);
    password.addEventListener("input", toggleButton);
});
