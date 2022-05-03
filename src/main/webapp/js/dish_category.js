$(document).ready(function () {
    let itemContainers = $('.item__container');
    const itemMap = new Map();
    let cartSize = initCartSize();

    function initCartSize() {
        const cartSizeText = document.getElementById('cart_size').textContent;
        return cartSizeText === '' ? 0 : Number.parseInt(cartSizeText);
    }

    itemContainers.each((index, itemContainer) => {
        const itemId = extractItemId(itemContainer);
        const itemQuantity = extractItemQuantity(itemContainer);
        const counterWrapper = itemContainer.querySelector(".counter__wrapper");
        itemMap.set(itemId, itemQuantity);
        addListenersToCounter(counterWrapper, itemId);
    });

    function extractItemId(dishContainer) {
        return dishContainer.dataset.item;
    }

    function extractItemQuantity(itemContainer) {
        const quantityElement = itemContainer.querySelector('.quantity');
        return Number.parseInt(quantityElement.textContent);
    }

    function addListenersToCounter(counterWrapper, itemId) {
        addListenerToIncreaseButton(counterWrapper, itemId);
        addListenerToDecreaseButton(counterWrapper, itemId);
        addListenerToAddToCartButton(counterWrapper, itemId);
    }

    function addListenerToIncreaseButton(counterWrapper, itemId) {
        const increaseButton = counterWrapper.querySelector('button.counter__button[data-action=increase]');
        increaseButton.addEventListener('click', e => {
            sendChangeItemAmountRequest(itemId, 1, () => updatePage(counterWrapper, 1, itemId),
                () => processError());
        });
    }

    function addListenerToDecreaseButton(counterWrapper, itemId) {
        const decreaseButton = counterWrapper.querySelector('button[data-action=decrease]');
        decreaseButton.addEventListener('click', e => {
            const itemQuantity = itemMap.get(itemId);
            if (itemQuantity !== 0) {
                sendChangeItemAmountRequest(itemId, -1, () => updatePage(counterWrapper, -1, itemId),
                    () => processError());
            }
        });
    }

    function addListenerToAddToCartButton(counterWrapper, itemId) {
        const addToCartButton = counterWrapper.querySelector('button.add__to__cart');
        addToCartButton.addEventListener('click', e => {
            hideAddToCartButtonAndShowCounter(counterWrapper);
            sendChangeItemAmountRequest(itemId, 1, () => updatePage(counterWrapper, 1, itemId),
                () => processError());
        });
    }

    function hideAddToCartButtonAndShowCounter(counterWrapper) {
        const amountCounter = counterWrapper.querySelector('.quantity__counter');
        const addToCartButton = counterWrapper.querySelector('button.add__to__cart');
        addToCartButton.classList.add('d-none');
        amountCounter.classList.remove('d-none');
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

    function updatePage(counterWrapper, value, itemId) {
        if (canBeUpdated(itemId, value)) {
            changeTotalQuantity(value);
            changeItemQuantity(counterWrapper, value, itemId);
        }
    }

    function canBeUpdated(itemId, value) {
        const itemAmount = itemMap.get(itemId);
        return !(itemAmount === 0 && value < 0);
    }

    function changeTotalQuantity(value) {
        let cartSizeElement = document.getElementById('cart_size');
        cartSize += value;
        cartSizeElement.textContent = cartSize === 0 ? '' : cartSize;
    }

    function changeItemQuantity(counterWrapper, value, itemId) {
        const quantityElement = counterWrapper.querySelector('.quantity');
        let newItemQuantity;
        if (willBeRemoved(itemId, value)) {
            hideCounterAndShowAddToCartButton(counterWrapper);
            newItemQuantity = 0;
        } else {
            newItemQuantity = itemMap.get(itemId) + value;
        }
        itemMap.set(itemId, newItemQuantity);
        quantityElement.textContent = newItemQuantity;
    }

    function willBeRemoved(itemId, value) {
        let quantity = itemMap.get(itemId);
        return quantity + value === 0;
    }

    function hideCounterAndShowAddToCartButton(counterWrapper) {
        const amountCounter = counterWrapper.querySelector('.quantity__counter');
        const addToCartButton = counterWrapper.querySelector('button.add__to__cart');
        addToCartButton.classList.remove('d-none');
        amountCounter.classList.add('d-none');
    }

    function processError() {
        alert("Sorry, but our server is unavailable now :(");
    }
});
