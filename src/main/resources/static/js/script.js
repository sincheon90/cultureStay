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

    // 검색 컨테이너 태그 토글 버튼
    var toggleIcon = document.getElementById("toggleIcon");
    toggleIcon.addEventListener('click', function(){
        toggleTags();
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


// 검색 컨테이너 태그 토글 버튼
function toggleTags() {
    var tagSection = document.getElementById("tagSection");

    if (tagSection.style.display === "none") {
        tagSection.style.display = "block";
        toggleIcon.innerHTML = "&#9650; 접기"; // Change to up arrow
    } else {
        tagSection.style.display = "none";
        toggleIcon.innerHTML = "&#9660; 태그"; // Change to down arrow
    }
}


$(document).ready(function() {
	$('#dateinput').daterangepicker({
		opens: 'center',
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

	$('.dateinput').click(function() {
		$('#dateinput').data('daterangepicker').show();
	});

	$('#dateinput').on('apply.daterangepicker', function(ev, picker) {
		$('input[name="start_date"]').val(picker.startDate.format('YYYYMMDD'));
		$('input[name="end_date"]').val(picker.endDate.format('YYYYMMDD'));
		$('input[id="dateinput"]').val(picker.startDate.format('YYYYMMDD') + " ~ " + picker.endDate.format('YYYYMMDD'))
		const start_date = picker.startDate.format('YYYYMMDD');
		const end_date = picker.endDate.format('YYYYMMDD');
		const days = calculateDateDifference(start_date, end_date);

		// 일수에 따라 표시할 문자열 생성
		const nightsString = days > 1 ? ` (${days}박 ${days + 1}일)` : '1박 2일';
		const nightsString2 = days > 1 ? `${days}박` : '1박';

		// 일수를 표시할 요소에 반영
		$('.per-night').text(`${nightsString}`);
		$('.x-night').text(` x${nightsString2}`);

		var price = $('input[name="price"]').val();
		const totalPrice = price * days;
		// 숫자를 한국어 화폐 형식으로 변환
        const formattedTotalPrice = totalPrice.toLocaleString('ko-KR');
		$('.totalPrice').text(`${formattedTotalPrice}`);

	});

	//몇박인지 계산
	function calculateDateDifference(start_date, end_date) {
		// YYYYMMDD 형식의 문자열을 Date 객체로 변환
		const startDateObject = new Date(start_date.slice(0, 4), start_date.slice(4, 6) - 1, start_date.slice(6));
		const endDateObject = new Date(end_date.slice(0, 4), end_date.slice(4, 6) - 1, end_date.slice(6));

		// 1일 = 24시간 * 60분 * 60초 * 1000밀리초
		const oneDayMilliseconds = 24 * 60 * 60 * 1000;

		// 두 날짜 사이의 일수 차이 계산
		const differenceInDays = Math.round((endDateObject - startDateObject) / oneDayMilliseconds);

		return differenceInDays;
	}

    //좋아요 북마크 관련 코드
    var programNum = $('input[name="programNum"]').val();
    var like = $('input[name="like"]').val();
    var bookmark = $('input[name="bookmark"]').val();

    updateButtonImage('like', like);
    updateButtonImage('bookmark', bookmark);

    $('#like_img').click(function() {
        toggleState('like', programNum);
    });

    $('#bookmark_img').click(function() {
        toggleState('bookmark', programNum);
    });

    function toggleState(type, programNum) {
        $.ajax({
            url: type,
            type:'post',
            data: {'programNum' : programNum},
            success: function (value) {
                updateButtonImage(type, value);
            },
            error: function () {alert('error');}
        });
    }

    function updateButtonImage(type, value){
    console.log(type + value);
        var imgSrc = (type == 'bookmark') ?
            (value == 1 ? '/img/mark/bookmark.png' : '/img/mark/unbookmark.png' ) :
            (value == 1 ? '/img/mark/heart.png' : '/img/mark/unheart.png' );

        $('#' + type + '_img').attr('src', imgSrc);
    }

    // 게시글 추천(좋아요)
    var boardnum = $('input[name="boardnum"]').val();
    var recommend = $('input[name="recommend"]').val();

    updateButtonImage('recommend', recommend);

    $('#recommend_img').click(function() {
        console.log("recomned clicked");
        toggleStateBoard('recommend', boardnum);
    });

    function toggleStateBoard(type, boardnum) {
        $.ajax({
            url: type,
            type:'post',
            data: {'boardnum' : boardnum},
            success: function (value) {
                updateButtonImage(type, value);
            },
            error: function () {alert('error');}
        });
    }

});