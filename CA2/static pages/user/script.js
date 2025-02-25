document.addEventListener("DOMContentLoaded", function() {
    const cartContent = document.getElementById('cart-content');
    const historyContent = document.getElementById('history-content');

    const cartItems = [];
    if (cartItems.length === 0) {
        cartContent.innerHTML = '<div class="card"><div class="card-body"><p class="text-center">Your cart is empty.</p></div></div>';
    }

    const historyItems = [];
    if (historyItems.length === 0) {
        historyContent.innerHTML = '<div class="card"><div class="card-body"><p class="text-center">Your history is empty.</p></div></div>';
    }
});
