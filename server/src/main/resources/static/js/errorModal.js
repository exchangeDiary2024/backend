function showErrorModal(message) {
    const modal = document.getElementById('errorModal');
    const errorMessage = document.getElementById('errorMessage');
    errorMessage.textContent = message;

    modal.style.display = 'block';
    setTimeout(() => {
        modal.style.display = 'none';
    }, 2500);

    window.onclick = function(event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    };
}
// document.addEventListener('click', function(event) {
//     const modal = document.getElementById('errorModal');
//     if (modal.style.display === 'block') {
//         modal.style.display = 'none';
//     }
// });
