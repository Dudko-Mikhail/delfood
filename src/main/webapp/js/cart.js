$(document).ready(function () {
    const itemMap = new Map();
    const totalPriceElement = document.getElementById('total_price');
    const totalDiscountElement = document.getElementById('total_discount');
    const cartSizeElement = document.getElementById('cart_size');

    let orderItems = $('.cart__item');
    let cartSize = initCartSize();

    function initCartSize() {
        const cartSizeText = document.getElementById('cart_size').textContent;
        return cartSizeText === '' ? 0 : Number.parseInt(cartSizeText);
    }

    orderItems.each((index, item) => {
        const itemId = extractItemId(item);
        const itemQuantity = extractItemQuantity(item);
        itemMap.set(itemId, itemQuantity)
        addListenersToCartItem(item, itemId);
    });

    function extractItemId(itemElement) {
        return itemElement.dataset.item;
    }

    function extractItemQuantity(item) {
        const quantityElement = item.querySelector('.quantity');
        return Number.parseInt(quantityElement.textContent);
    }

    function addListenersToCartItem(item, itemId) {
        addListenerToIncreaseButton(item, itemId);
        addListenerToDecreaseButton(item, itemId);
        addListenerToRemoveButton(item, itemId);
    }

    function addListenerToIncreaseButton(item, itemId) {
        const increaseButton = item.querySelector('button[data-action=increase]');
        increaseButton.addEventListener('click', e => {
            sendChangeItemAmountRequest(itemId, 1, response => updateCartPage(item, 1, response),
                () => processError());
        });
    }

    function addListenerToDecreaseButton(item, itemId) {
        const decreaseButton = item.querySelector('button[data-action=decrease]');
        decreaseButton.addEventListener('click', e => {
            sendChangeItemAmountRequest(itemId, -1, response => {
                    updateCartPage(item, -1, response);
                },
                () => processError());
        })
    }

    function addListenerToRemoveButton(item, itemId) {
        const removeButton = item.querySelector('button[data-action=remove]');
        removeButton.addEventListener('click', e => {
            sendRemoveItemRequest(itemId, (response) => {
                const quantityDifference = -itemMap.get(itemId);
                updateOrderElements(quantityDifference, response.cartPrice, response.totalDiscount);
                removeOrderItem(item, response);
            }, () => processError());
        });
    }


    function sendChangeItemAmountRequest(itemId, value, success, error) {
        $.ajax({
            method: 'POST',
            url: 'action',
            dataType: 'json',
            data: {
                command: 'change_order_item_quantity',
                item_id: itemId,
                value: value
            },
            success: success,
            error: error
        });
    }

    function sendRemoveItemRequest(itemId, success, error) {
        $.ajax({
            method: 'POST',
            url: 'action',
            dataType: 'json',
            data: {
                command: 'remove_order_item',
                item_id: itemId,
            },
            success: success,
            error: error
        });
    }

    function updateCartPage(cartItem, value, response) {
        updateOrderElements(value, response.cartPrice, response.totalDiscount);
        updateCartItem(cartItem, value, response.itemPrice);
    }

    function updateOrderElements(value, totalPrice, totalDiscount) {
        cartSize += value;
        cartSizeElement.textContent = cartSize === 0 ? '' : cartSize;
        totalPriceElement.textContent = totalPrice;
        totalDiscountElement.textContent = totalDiscount;
    }

    function updateCartItem(item, value, totalPrice) {
        const itemId = extractItemId(item);
        const quantityElement = item.querySelector('.quantity');
        if (willBeRemoved(itemId, value)) {
            removeOrderItem(item);
        } else {
            const priceElement = item.querySelector('.total_item_price');
            priceElement.textContent = totalPrice;
            let newItemQuantity = itemMap.get(itemId) + value;
            itemMap.set(itemId, newItemQuantity);
        }
        quantityElement.textContent = itemMap.get(itemId);
    }

    function willBeRemoved(itemId, value) {
        let quantity = itemMap.get(itemId);
        return quantity + value === 0;
    }

    function removeOrderItem(item) {
        const itemId = extractItemId(item);
        itemMap.delete(itemId);
        item.remove();
    }

    function processError() {
        alert("Sorry, but our server is unavailable now");
    }
});

// TODO Корзина осталась пустой. Нужно добавить надпись о том, что в корзине нет элементов.