document.querySelector('.dropbtn').addEventListener('click', function() {
  document.querySelector('.dropdown-content').classList.toggle('show');
});

// 메뉴 외부를 클릭할 경우 드롭다운 닫기
window.onclick = function(event) {
  if (!event.target.matches('.dropbtn')) {
    var dropdowns = document.getElementsByClassName("dropdown-content");
    for (var i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}

document.addEventListener('DOMContentLoaded', function() {
    // DOMContentLoaded: DOMContentLoaded 이벤트는 웹 페이지의 HTML이 완전히 로드되고 파싱되었을 때 발생
    // 모든 클릭 가능한 제목 요소를 선택
    var titles = document.querySelectorAll('.click-to-detail');

    // 제목 요소에 대한 클릭 이벤트 리스너 추가
    titles.forEach(function(title) {
        title.addEventListener('click', function() {
            var boardnum = this.getAttribute('data-boardnum');
            window.location.href = '/board/read?boardnum=' + boardnum;
        });
    });
});
