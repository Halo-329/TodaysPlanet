document.addEventListener('DOMContentLoaded', function() {
    const cartItemsContainer = document.querySelector('.cart-items');
    const totalPriceElement = document.querySelector('.total-price');
    const orderButton = document.querySelector('.order-button');

    // 임시 데이터
    const cartItems = [
        {
            id: 1,
            name: '상품 1',
            price: 10000,
            quantity: 1,
            imageUrl: 'https://placehold.co/100x100?text=Item+1'
        },
        {
            id: 2,
            name: '상품 2',
            price: 15000,
            quantity: 2,
            imageUrl: 'https://placehold.co/100x100?text=Item+2'
        }
    ];

    function renderCartItems() {
        cartItemsContainer.innerHTML = '';
        let totalPrice = 0;

        cartItems.forEach(item => {
            const itemElement = document.createElement('div');
            itemElement.classList.add('cart-item');
            itemElement.innerHTML = `
                <img src="${item.imageUrl}" alt="${item.name}">
                <div class="item-details">
                    <h4>${item.name}</h4>
                    <p>${item.price.toLocaleString()}원</p>
                </div>
                <div>
                    <input type="number" value="${item.quantity}" min="1" data-item-id="${item.id}">
                    <button class="remove-item-button" data-item-id="${item.id}">삭제</button>
                </div>
            `;
            cartItemsContainer.appendChild(itemElement);
            totalPrice += item.price * item.quantity;
        });

        totalPriceElement.textContent = totalPrice.toLocaleString();
    }

    renderCartItems();

    orderButton.addEventListener('click', function() {
        // 주문 처리 로직
        alert('주문이 완료되었습니다.');
    });
});
