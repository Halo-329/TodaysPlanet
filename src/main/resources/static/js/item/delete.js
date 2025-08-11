// static/js/item/detail.js
document.addEventListener("DOMContentLoaded", () => {
    // ───────── 삭제 버튼 기능 (기존 코드) ─────────
    document.querySelectorAll(".delete-btn").forEach(btn => {
        btn.addEventListener("click", async () => {
            const itemId = btn.getAttribute("data-id");
            if (!confirm("정말 삭제하시겠습니까?")) return;
            const res = await fetch(`/item/items/${itemId}`, { method: "DELETE" });
            if (res.ok) {
                btn.closest(".detail").remove();
            } else {
                alert("삭제에 실패했습니다.");
            }
        });
    });

    // ───────── 수량 조절 기능 ─────────
    const qc = document.querySelector(".quantity-control");
    if (qc) {
        const decBtn = qc.querySelector("button:first-of-type");
        const incBtn = qc.querySelector("button:last-of-type");
        const numEl  = qc.querySelector(".quantity-number");
        // 최소값 1
        const MIN = 1;
        // (옵션) 재고 기반 최대값을 사용하려면 HTML에 data-max="${data.stock}" 어트리뷰트 추가 후 다음 줄 사용
        // const MAX = parseInt(qc.getAttribute("data-max"), 10);
        // 현재는 무제한 증가
        decBtn.addEventListener("click", () => {
            let v = parseInt(numEl.textContent, 10);
            if (v > MIN) numEl.textContent = v - 1;
        });
        incBtn.addEventListener("click", () => {
            let v = parseInt(numEl.textContent, 10);
            numEl.textContent = v + 1;
        });
    }
});
