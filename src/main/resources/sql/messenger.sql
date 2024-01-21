-- Messages 테이블 생성
drop table Messages;
CREATE TABLE Messages (
    messageId NUMBER PRIMARY KEY,
    chatRoomId NUMBER,
    senderId VARCHAR2(255),
    messageText VARCHAR2(4000),
    timestamp DATE DEFAULT SYSDATE,
    isRead NUMBER,
    messageType VARCHAR2(255) -- 텍스트, 이미지 등
);
CREATE SEQUENCE Messages_seq;

-- ChatRooms 테이블 생성
drop table ChatRooms;
CREATE TABLE ChatRooms (
    chatRoomId NUMBER PRIMARY KEY,
    chatRoomName VARCHAR2(255),
    createTimestamp DATE DEFAULT SYSDATE,
    createdUserId NUMBER
);
CREATE SEQUENCE ChatRooms_seq;

-- ChatRoomMembers 테이블 생성
drop table ChatRoomMembers;
CREATE TABLE ChatRoomMembers (
    chatRoomMemberId NUMBER PRIMARY KEY,
    chatRoomId NUMBER,
    userId VARCHAR2(255),
    joinedTimestamp DATE DEFAULT SYSDATE,
    memberRole VARCHAR2(255)
);
CREATE SEQUENCE ChatRoomMembers_seq;


-- Attachments 테이블 생성
drop table Attachments;
CREATE TABLE Attachments (
    attachmentId NUMBER PRIMARY KEY,
    messageId NUMBER,
    filePath VARCHAR2(1000),
    fileType VARCHAR2(255)
);
CREATE SEQUENCE Attachments_seq;


select * from Messages;
update  Messages set isRead = 2;
commit;

SELECT * FROM ChatRoomMembers CM;

select * from cultureStay_member;
select * from cultureStay_member where userid LIKE '%' || '222' || '%';