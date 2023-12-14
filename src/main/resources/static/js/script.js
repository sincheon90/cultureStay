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
