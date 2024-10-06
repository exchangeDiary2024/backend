function showError(message) {
    const modal = document.getElementById('errorModal');
    const errorMessage = document.getElementById('errorMessage');
    errorMessage.textContent = message;

    modal.style.display = 'block';
    setTimeout(() => {
        modal.style.display = 'none';
    }, 2500);
}

function handleError(errorCode) {
    let message = '';
    switch (errorCode) {
        case 400:
            message = '잘못된 요청입니다.';
            break;
        case 404:
            message = '찾을 수 없는 페이지입니다.';
            break;
        case 500:
            message = '서버 오류가 발생했습니다.';
            break;
        default:
            message = '알 수 없는 오류입니다.';
    }

    showError(message);

}

document.addEventListener('click', function(event) {
    const modal = document.getElementById('errorModal');
    if (modal.style.display === 'block') {
        modal.style.display = 'none';
    }
});