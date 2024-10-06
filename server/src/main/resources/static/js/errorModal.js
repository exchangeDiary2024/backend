const errorModal = document.querySelector(".error-modal");

function showErrorModal(message) {
    const errorMessage = document.querySelector(".error-text");

    closeModal();

    errorMessage.innerText = message;
    errorModal.style.display = "block";
    setTimeout(() => {
        errorModal.style.display = "none";
    }, 2500);

    closeErrorModal();
}

function closeErrorModal() {
    window.onclick = function(event) {
        if (event.target !== errorModal) {
            errorModal.style.display = "none";
        }
    };
}

// document.addEventListener('click', function(event) {
//     const modal = document.getElementById('errorModal');
//     if (modal.style.display === 'block') {
//         modal.style.display = 'none';
//     }
// });
