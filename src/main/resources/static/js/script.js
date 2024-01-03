//document.querySelector('.dropbtn').addEventListener('click', function() {
//  document.querySelector('.dropdown-content').classList.toggle('show');
//});

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
    var programClicks = document.querySelectorAll('.program-to-detail');
    var boardClicks = document.querySelectorAll('.board-to-detail');

    // 제목 요소에 대한 클릭 이벤트 리스너 추가
    programClicks.forEach(function(element) {
        element.addEventListener('click', function() {
            var programNum = this.getAttribute('data-programnum');
            window.location.href = '/program/detail?programNum=' + programNum;
        });
    });

    boardClicks.forEach(function(element) {
            element.addEventListener('click', function() {
                var boardnum = this.getAttribute('data-boardnum');
                window.location.href = '/board/read?boardnum=' + boardnum;
            });
        });
});


function truncateContent() {
    var maxLength = 250;
    var contents = document.querySelectorAll('.truncate-contents');
    contents.forEach(function(content) {
        if (content.textContent.length > maxLength) {
            content.textContent = content.textContent.substr(0, maxLength) + '...';
        }
    });
}


$(document).ready(function() {
  $('#dateinput').daterangepicker({
    autoUpdateInput: false,
    timePicker: false,
    startDate: moment().startOf('hour'),
    endDate: moment().startOf('hour').add(48, 'hour'),
    locale: {
            cancelLabel: 'Clear',
            separator: " ~ ",
            applyLabel: "적용",
            cancelLabel: "취소",
            fromLabel: "From",
            toLabel: "To",
            customRangeLabel: "Custom",
            daysOfWeek: ["일", "월", "화", "수", "목", "금", "토"],
            monthNames: ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"],
            format: 'YYYY/MM/DD'
        }
  });

    $('#dateinput').on('apply.daterangepicker', function(ev, picker) {
      $('input[name="start_date"]').val(picker.startDate.format('YYYYMMDD'));
      $('input[name="end_date"]').val(picker.endDate.format('YYYYMMDD'));
    });

    $('.dateinput').click(function() {
        $('#dateinput').data('daterangepicker').show();
    });

});