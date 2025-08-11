document.addEventListener('DOMContentLoaded', function () {
    const cartItemsContainer = document.querySelector('.cart-items');
    const totalPriceElement = document.querySelector('.total-price');
    const orderButton = document.querySelector('.order-button');

    let cartItems = [
        { id: 1, name: '멋진 신발', price: 75000, quantity: 1, imageUrl: 'https://placehold.co/120x120?text=Shoe' },
        { id: 2, name: '스타일리시한 셔츠', price: 45000, quantity: 1, imageUrl: 'https://placehold.co/120x120?text=Shirt' },
        { id: 3, name: '편안한 바지', price: 60000, quantity: 1, imageUrl: 'https://placehold.co/120x120?text=Pants' }
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
                <div class="item-controls">
                    <div class="quantity-control">
                        <button class="decrease-btn" data-item-id="${item.id}">-</button>
                        <span class="quantity">${item.quantity}</span>
                        <button class="increase-btn" data-item-id="${item.id}">+</button>
                    </div>
                    <button class="remove-item-button" data-item-id="${item.id}">삭제</button>
                </div>
            `;
            cartItemsContainer.appendChild(itemElement);
            totalPrice += item.price * item.quantity;
        });

        totalPriceElement.textContent = totalPrice.toLocaleString() + '원';
    }

    function handleCartActions(event) {
        const target = event.target;

        if (target.classList.contains('remove-item-button')) {
            const itemId = parseInt(target.dataset.itemId);
            cartItems = cartItems.filter(item => item.id !== itemId);
            renderCartItems();
        }

        if (target.classList.contains('increase-btn')) {
            const itemId = parseInt(target.dataset.itemId);
            const item = cartItems.find(i => i.id === itemId);
            item.quantity++;
            renderCartItems();
        }

        if (target.classList.contains('decrease-btn')) {
            const itemId = parseInt(target.dataset.itemId);
            const item = cartItems.find(i => i.id === itemId);
            if (item.quantity > 1) {
                item.quantity--;
                renderCartItems();
            }
        }
    }

    cartItemsContainer.addEventListener('click', handleCartActions);

    orderButton.addEventListener('click', function () {
        const totalPrice = cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);
        if (totalPrice === 0) {
            alert('총 결제 금액이 0원입니다. 장바구니를 확인해주세요.');
        } else {
            alert('주문이 완료되었습니다.');
        }
    });

    renderCartItems();
});
