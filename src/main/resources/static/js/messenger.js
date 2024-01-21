var scrollToBottomButton = document.getElementById('scrollToBottom');

var stompClient = null;
var chatRoomId = null;
var userId = document.getElementById('userId').value;    
var chatRoomDiv = document.getElementById('chatroom');
var currentChatRoomSubscription = null;
var currentSendMessageSubscription = null;

// 웹소켓 관련 function
function connect() {
    var socket = new SockJS('/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({},
        function (frame) {
            console.log('Connected: ' + frame);
            // 채팅방 리스트 가져오기 구독
            stompClient.subscribe('/topic/chatRoomList/' + userId, function (chatRoomList) {
                showChatRoomList(JSON.parse(chatRoomList.body)); // 결과 값을 받으면 function 실행
            });
            initChatRoomList();
        }
    );
}

function initChatRoomList(){
    var userId = document.getElementById('userId').value;
    stompClient.send("/app/getChatRoomList", {}, JSON.stringify({
            'userId': userId
        })
    );
}


function showChatRoomList(chatRoomData) {
    var chatRoomsDiv = document.getElementById('chatRoomList');
    chatRoomsDiv.innerHTML = ''; // 기존 리스트를 초기화

    chatRoomData.forEach(function(chatRoom) {
        var chatRoomElement = document.createElement('li');
        var chatRoomLink = document.createElement('a');
        chatRoomLink.href = '#'; // 페이지 이동을 방지하기 위해 '#' 사용
        chatRoomLink.textContent = chatRoom.chatRoomName;
        chatRoomLink.setAttribute('data-chatRoomId', chatRoom.chatRoomId);

        // 클릭 이벤트 리스너 추가
        chatRoomLink.addEventListener('click', function(event) {
            event.preventDefault(); // 링크의 기본 동작(페이지 이동)을 방지
            var chatRoomId = this.getAttribute('data-chatRoomId');
            getChatRoom(chatRoomId);
        });

        chatRoomElement.appendChild(chatRoomLink);
        chatRoomsDiv.appendChild(chatRoomElement);
    });
}

function getChatRoom(clickedId) {
    chatRoomId = clickedId;
    // 기존에 구독된 채팅방 구독 해제 및 신규 구독
    if (currentChatRoomSubscription) {
        currentChatRoomSubscription.unsubscribe();
    }
    currentChatRoomSubscription = stompClient.subscribe('/topic/chatRoom/' + chatRoomId, function (messages) {
        showChatRoom(JSON.parse(messages.body));
    });

    // 채팅 보내기 구독 해제 및 신규 구독
    if (currentSendMessageSubscription) {
        currentSendMessageSubscription.unsubscribe();
    }
    currentSendMessageSubscription = stompClient.subscribe('/topic/sendMessage/' + chatRoomId, function (message) {
        addChatElement(JSON.parse(message.body)); 
    });

    // 메시지 목록 요청
    stompClient.send("/app/getMessages", {}, JSON.stringify({
            'chatRoomId': chatRoomId,
            'userId': userId
        })
    );
}

function showChatRoom(messages) {
    chatRoomDiv.innerHTML = ''; // 기존 리스트를 초기화

    messages.forEach(function(message) {
        addChatElement(message)
    });
}

// isRead 확인을 위한 새로고침
var chatContainer = document.querySelector('.chatroom-container');
chatContainer.addEventListener('focus', function() {
    if (chatRoomId) {
        getChatRoom(chatRoomId);
    }
});

function addChatElement(message) {
    var chatDiv = document.createElement('div');
    chatDiv.classList.add('chat-message');

    // 메시지가 사용자 본인의 것인지 여부에 따라 클래스 적용
    if (message.senderId === userId) {
        chatDiv.classList.add('my-message');
    } else {
        chatDiv.classList.add('other-message');
    }
    var senderIdElement = document.createElement('div');
    senderIdElement.classList.add('message-sender');
    senderIdElement.textContent = message.senderId;

    var messageTextElement = document.createElement('span');
    messageTextElement.classList.add('message-text');
    messageTextElement.textContent = message.messageText;
    
    chatDiv.appendChild(senderIdElement);
    chatDiv.appendChild(messageTextElement);
    if (message.isRead > 0) { // isRead가 0보다 클때만 안 읽은 수 표시
        var isReadElement = document.createElement('span');
        isReadElement.classList.add('message-status');
        isReadElement.textContent = message.isRead;
        chatDiv.appendChild(isReadElement);
    }

    chatRoomDiv.appendChild(chatDiv);

    // Check if the 'Scroll to Bottom' button is currently hidden
    if (!scrollToBottomButton.classList.contains('show')) {
        // If hidden, scroll to the bottom
        chatRoomDiv.scrollTop = chatRoomDiv.scrollHeight;
    }
}

function sendMessage() {
    var messageText = document.getElementById('message').value;

    stompClient.send("/app/sendMessage", {}, JSON.stringify({
            'messageText': messageText,
            'chatRoomId': chatRoomId,
            'senderId': userId
        })
    );
    document.getElementById('message').value = '';
}

document.getElementById('messageForm').addEventListener('submit', function(event) {
    event.preventDefault();
    sendMessage();
});

connect();


// 스크롤 관련 function
scrollToBottomButton.addEventListener('click', function() {
    chatRoomDiv.scrollTop = chatRoomDiv.scrollHeight;
    scrollToBottomButton.classList.remove('show');
});

// Update the logic for the scroll event listener
chatRoomDiv.addEventListener('scroll', function() {
    // If the user scrolls up, disable auto-scroll to bottom
    if (chatRoomDiv.scrollTop < chatRoomDiv.scrollHeight - chatRoomDiv.clientHeight) {
        scrollToBottomButton.classList.add('show');
    } else {
        // User scrolled to the bottom, re-enable auto-scroll
        scrollToBottomButton.classList.remove('show');
    }
});



// modal 관련
var searchUserModal = document.getElementById('searchUserModal');

document.addEventListener('DOMContentLoaded', function() {
    var searchUserButton = document.getElementById('searchUserButton');
    var closeButton = document.querySelector('.close');
    var searchUserSubmit = document.getElementById('searchUserSubmit')

    searchUserButton.addEventListener('click', function() {
        searchUserModal.style.display = 'block';
    });

    closeButton.addEventListener('click', function() {
        searchUserModal.style.display = 'none';
    });

    // 모달 창 바깥 영역 클릭 시 닫기 (선택적)
    window.addEventListener('click', function(event) {
        if (event.target == searchUserModal) {
            searchUserModal.style.display = 'none';
        }
    });

    searchUserSubmit.addEventListener('click', function() {
        var searchUser = document.getElementById('searchUserInput');
        var searchResult = document.getElementById('searchResult');
        
        fetch('/api/searchUser?searchUser=' + encodeURIComponent(searchUser.value))
        .then(function(response) {
            return response.json();
        })
        .then(function(users) {
            searchResult.innerHTML = '';
            users.forEach(function(user) {
                var userElement = document.createElement('div');
                userElement.textContent = user.userid; // 예시: 사용자 이름을 표시
                searchResult.appendChild(userElement);
                addClickEventToUserId(userElement, user.userid)
            });
        })
        .catch(function(error) {
            console.error('Error:', error);
        });
    })
});


function addClickEventToUserId(userIdElement, userId) {
    userIdElement.addEventListener('click', function() {
        console.log('id clicked');
        showUserActionMenu(this, userId);
    });
}

function showUserActionMenu(targetElement, userId) {
    var menu = document.createElement('div');
    menu.className = 'user-action-menu';
    menu.innerHTML = '<button class="openChatRoomButton" onclick="openChatRoom(\'' + userId + '\')">메시지 보내기</button>';
    // 메뉴 위치 설정 및 표시
    menu.style.position = 'absolute';
    menu.style.left = targetElement.getBoundingClientRect().left + 'px';
    menu.style.top = targetElement.getBoundingClientRect().bottom + 'px';
    menu.style.zIndex = 1000; // 페이지 최상단에 표시

    document.body.appendChild(menu);

    // 다른 곳을 클릭하면 메뉴 닫기
    // menu가 생성 되고 listner가 바로 실행하지 않도록 setTimeout 사용
    setTimeout(function() {
        window.addEventListener('click', function(event) {
            if (!menu.contains(event.target)) {
                menu.remove();
                searchUserModal.style.display = 'none'; // 모달 안보이기
                window.removeEventListener('click', this);
            }
        });
    }, 0);
}

function openChatRoom(userId) {
    fetch('/api/messenger/createChatRoom?chatPartner=' + encodeURIComponent(userId))
    .then(function(response) {
        return response.json(); // JSON으로 파싱
    })
    .then(function(chatRoomId) {
        getChatRoom(chatRoomId); // JSON으로 파싱된 chatRoomId를 getChatRoom 함수에 전달
    })
    .catch(function(error) {
        console.error('Error:', error);
    });

    // 메뉴 제거
    var menu = document.querySelector('.user-action-menu');
    if (menu) {
        menu.remove();
        searchUserModal.style.display = 'none'; // 모달 안보이기
    }
}