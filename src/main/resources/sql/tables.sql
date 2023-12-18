--유저테이블
CREATE TABLE cultureStay_member(
	userid	    varchar2(255)	    primary key,   
	password	varchar2(255)	    not null,  
	username	varchar2(255)	    not null,  
	phone	    varchar2(255)		not null,  
	address 	varchar2(255)		NULL,           
	email	    varchar2(100)		not null,        --이메일
	birth	    date		        NULL,            --생년월일
    gender      varchar2(20)        not null,        --성별
    profileImagePath   varchar(255)   NULL, --프사
    verified    varchar2(1) CHECK(verified IN  ('0','1'))--본인인증 여부
    enabled     NUMBER(1)       DEFAULT 1 NOT NULL,             -- 계정 상태. 1:사용 가능, 0:사용 불가능
    rolename    VARCHAR2(20)    DEFAULT 'ROLE_USER' NOT NULL    -- 사용자 권한. 모두 'ROLE_USER'로 처리
);
   --변경
--verified 디폴트값 넣어주기
select * from Users;

--유저 좋아요,북마크, 최근방문 프로그램 :  추천시 사용
CREATE TABLE Interests(     
    	userid	    varchar2(255)	    primary key references cultureStay_member(userid), 
        favorite     number                 null,          --좋아요한 프로그램 아이디(어레이리스트)
        bookmark    number                 null,         --북마크한 프로그램 아이디(어레이리스트)
        recentClick number                 null         --최근 클릭한 프로그램
);

CREATE TABLE Program (
	programNum	number	            primary key,
	userid	    varchar2(255)	references cultureStay_member(userid), 
	title	    varchar2(255)	NOT NULL,
	content	    varchar2(4000)	NOT NULL,
	address	    varchar2(255)	NOT NULL,
	price	    number		        NOT NULL,
    tag         number             NOT NULL, --수정할지도 모름
	start_date	date		    NOT NULL,
	end_date	date		    NOT NULL,
    hits            number              default 0 --인기프로그램용 조회수
);
create sequence programNum_seq;
select * from Program;

--인기태그용 태그클릭수
create table tagClick_cnt(
    one     number     default 0,
    two      number     default 0,
    three    number     default 0,
    four     number     default 0,
    five     number     default 0,
    six      number     default 0,
    seven    number     default 0,
    eight    number     default 0,
    nine    number     default 0,
    ten      number     default 0
);



CREATE TABLE Review (
	reviewNum   	number		            primary key,
	programNum	    number		            references Program(programNum), 
	customerID	    varchar2(255)		references cultureStay_member(userid), 
	hostID	        varchar2(255)		references cultureStay_member(userid), 
	reviewerID	    varchar2(255)		references cultureStay_member(userid), 
	stars	        number	    	        NOT NULL,
	content	        varchar2(2000)  	NOT NULL,
	reviewPic	    varchar2(255)	    NULL,
	start_date	    date		        NOT NULL
);
create sequence reviewNum_seq;



CREATE TABLE Reservation (
	reserNum	number		            primary key,
	programNum	number		            references Program(programNum), 
	userid	    varchar2(255)		references cultureStay_member(userid),
	start_date	date		    NOT NULL,
	end_date	date		    NOT NULL,
    request	    varchar2(255)		NULL, --요청사항
	payment 	number		NULL,   --결제수단(0,1,2,3)
	status	    number		default 0       --예약상태(0,1,2,3)
);
create sequence reserNum_seq;

CREATE TABLE Board(
    boardnum	    number		            primary key,
	userid          varchar2(255)		references cultureStay_member(userid),
    title           varchar2(300)       not null,                               --제목
    contents        varchar2(4000)      not null,                               --글내용
    inputdate       date                default sysdate,     
    hits            number              default 0,   
    originalfile    varchar2(300),      --첨부파일의 원래이름 사진.jpg -> 20220727.jpg
    savedfile       varchar2(100)       --첨부파일이 서버에 저장된 이름
);
create sequence boardnum_seq;

CREATE TABLE Reply (
	replynum      number		            primary key,
	boardnum    number		references Board(boardnum),
	userid	 varchar2(255)		references cultureStay_member(userid),
	content	varchar2(4000)      not null,     
	inputdate date                default sysdate
);
create sequence replynum_seq;



--https://velog.io/@hiy7030/TIL-%EC%B1%84%ED%8C%85-%EA%B8%B0%EB%8A%A5-ERD-%EC%84%A4%EA%B3%84
--CREATE TABLE Chat (
--	chatNum         number		            primary key,
--	programNum	    number		            references Program(programNum), 
--	userid	        varchar2(255)		references User(userid), 
--	hostid	        varchar2(255)		references User(userid)
--);
--create sequence chatNum_seq;



