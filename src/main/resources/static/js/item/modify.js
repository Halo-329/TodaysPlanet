document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("form");
    const priceInput = document.getElementById("price");

    form.addEventListener("submit", (e) => {
        const price = priceInput.value.trim();

        if (!/^\d+$/.test(price)) {
            alert("가격은 숫자만 입력해야 합니다.");
            e.preventDefault(); // 폼 제출 막기
        }
    });
});
