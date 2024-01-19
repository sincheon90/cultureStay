--유저테이블 verified 디폴트값 넣어주기
drop table cultureStay_member;
CREATE TABLE cultureStay_member(
	userid	    	            varchar2(255)	    primary key,
	password		            varchar2(255)	    not null,
	name		                varchar2(255)	    null,
	phone	    	            varchar2(255)		null,
	address_postcode 		    varchar2(255)		NULL,
    address_address 		    varchar2(255)		NULL,
    address_detailAddress 		varchar2(255)		NULL,
    address_extraAddress 		varchar2(255)		NULL,
	email	    	            varchar2(100)		NULL,        --이메일
	birth	   	 	            date		        NULL,            --생년월일
    gender      	            varchar2(20)        null,        --성별
    ogProfileImage	            varchar(255),					 -- 프사original
    svProfileImage              varchar(255),					 -- 프사saved
    verified    	            varchar2(1) 		CHECK(verified IN  ('0','1')), --본인인증 여부
    enabled     	            NUMBER(1)       	DEFAULT 1 NOT NULL,             -- 계정 상태. 1:사용 가능, 0:사용 불가능
    rolename    	            VARCHAR2(20)   		DEFAULT 'ROLE_USER' NOT NULL,    -- 사용자 권한. 모두 'ROLE_USER'로 처리
    introduce     	            varchar2(1000)
);
select * from cultureStay_member;

--홈스테이 테이블
CREATE TABLE Program (
	programNum	number	            primary key,
	userid	    varchar2(255),
	title	    varchar2(255)	NOT NULL,
	content	    CLOB	NOT NULL,
	postcode    varchar2(255)   NOT NULL,
	address	    varchar2(255)	NOT NULL,
	detailed_address varchar2(255)	NOT NULL,
	price	    number		        NOT NULL,
	start_date	date		    ,
	end_date	date		    ,
	inputdate       date                default sysdate,
    hits            number              default 0 --인기홈스테이용 조회수
);
create sequence programNum_seq;


--좋아요한 홈스테이 테이블
CREATE TABLE Program_like(
	p_like_num 	number 				primary key,
	userid	    varchar2(255)	 ,
	programNum	number           ,
	inputdate       date            default sysdate     --작성일
);
create sequence p_like_num_seq;
select * from Program_like;
drop table Program_like;
--북마크한 홈스테이 테이블
CREATE TABLE Program_bookmark(
	bookmark_num 	number 				primary key,
	userid	    varchar2(255)	    ,
	programNum	number              ,
	inputdate       date            default sysdate     --작성일
);
create sequence bookmark_num_seq;
select * from Program_bookmark;
drop table Program_bookmark;

--최근 방문 홈스테이 테이블
CREATE TABLE recentClick(
	clickNum   	number		         primary key,
	userid	    varchar2(255)	    ,
	programNum	number              ,
	inputdate       date            default sysdate     --작성일
);
create sequence clickNum_seq;
select * from recentClick;
drop table recentClick;

--홈스테이 필터,태그
CREATE TABLE ProgramTag(
	programNum	    number		  ,
	--인원수
	--maxhito	    number,
	--건물 유형
	apartment      number     default 0, --아파트
    detached      number     default 0, --단독주택

	--접근성 및 편의시설:와이파이,세탁기,에어컨,주자창,개인욕실,대중교통, 운전지원
	separateBathroom      		number     default 0,
    bathtub     	number     default 0,
    wifi     		number     default 0,
    transformer    		number     default 0,
    pajamasProvided     	number     default 0,
	hairdryer    number     default 0,
    basicToiletriesProvided    		number     default 0,
    petFriendly   		number     default 0,
    roomAlone      	number     default 0,


	--호스트언어:한국어, 일본어, 영어, 중국어
	langJapanese      number     default 0,
    langEnglish     number     default 0,
    langKorean    	number     default 0,



	--추천,집계 위한 태그:
	active      	number     default 0, --활동적
    creative     	number     default 0, --창의적
    healing    		number     default 0, --힐링
    traditional     number     default 0, --전통
    cookingExperience      	number     default 0, --요리체험
    scenicView      		number     default 0, --뷰
    countryside    	number     default 0, --시골
    city    		number     default 0, --도시
    festival     	number     default 0, --축제
    drive      		number     default 0, --드라이브
	socializing     number     default 0, --친목
	tranquil      	number     default 0  --한적한
);
select * from ProgramTag;
drop table ProgramTag;
--인기태그용 태그클릭수
create table tagClick_cnt(
	active      	number     default 0, --활동적
    creative     	number     default 0, --창의적
    healing    		number     default 0, --힐링
    traditional     number     default 0, --전통
    cooking      	number     default 0, --요리체험
    viewtag	        number     default 0, --뷰
    countryside    	number     default 0, --시골
    city    		number     default 0, --도시
    festival     	number     default 0, --축제
    drive      		number     default 0, --드라이브
	socializing     number     default 0, --친목
	secluded      	number     default 0  --한적한
);
select * from tagclick_cnt;
drop table tagclick_cnt;

--홈스테이 리뷰 테이블
CREATE TABLE Review (
	reviewNum   	number		            primary key,
  programNum      number                ,
  customerID      varchar2(255)     ,
  hostID          varchar2(255)     ,
  reviewerID      varchar2(255)     ,
  reserNum    number                ,

	stars	        number	    	        default 5,
	content	        varchar2(2000)  		NULL,
	inputdate	    date		        	 default sysdate,
	who				varchar2(2000) --뭐에 대한 리뷰(p,h,g)
);
create sequence reviewNum_seq;
select * from Review;
drop table Review;

--예약테이블
CREATE TABLE Reservation (
	reserNum	number		            primary key,
	programNum	number		            ,
    hostid 		varchar2(255),
	userid	    varchar2(255)		   ,
	start_date	date		    		NOT NULL,
	end_date	date		    		NOT NULL,
    request	    varchar2(255)			NULL, --요청사항
	payment 	number					NULL,   --결제수단(0,1,2,3)
	status	    number					default 0,       --예약상태(0,1,2,3)
    inputdate   date    default sysdate
);

create sequence reserNum_seq;
select * from Reservation;
drop table Reservation;
---------------------------------------------------------------------
--게시판 보드
drop table cultureStay_board;
CREATE TABLE cultureStay_board(
    boardnum	    number		        primary key,
	userid          varchar2(255)		,
    title           varchar2(300)       not null,                               --제목
    contents        varchar2(4000)      not null,                               --글내용
    inputdate       date                default sysdate,
    hits            number              default 0,
    originalfile    varchar2(300),      --첨부파일의 원래이름 사진.jpg -> 20220727.jpg
    savedfile       varchar2(100),
	recommend       number              default 0
);
create sequence cultureStay_boardnum_seq;
insert into cultureStay_board    (      boardnum      , userid      , title      , contents    ) values (      cultureStay_boardnum_seq.nextval      , 'userid'      , 'title'      , 'contents'    );
commit;
select * from cultureStay_board;
drop table cultureStay_board;
--게시판 댓글
CREATE TABLE cultureStay_reply (
	replynum        number		        primary key,
	boardnum        number		        ,
	userid	        varchar2(255)		,
	content	        varchar2(4000)      not null,
	inputdate       date                default sysdate,
	recommend       number
);
create sequence cultureStay_replynum_seq;
select * from cultureStay_reply;
drop table cultureStay_reply;

--게시판 좋아요
CREATE TABLE Board_like(
	b_like_num 	number 				primary key,
	userid	    varchar2(255)	    ,
	boardnum	   number,
	inputdate       date            default sysdate     --작성일
);
create sequence b_like_num_seq;
insert into Board_like values ('test', 1);
commit;
select * from Board_like;
drop table Board_like;

-- 첨부 이미지 관리 테이블
CREATE TABLE image(
  imgID   number        primary key, -- 첨부파일 ID (PK)
  programNum  number              ,
  boardnum     number          ,
  reviewNum     number                 ,
  fileName  varchar2(255),  -- 파일명 또는 경로
  filetype  varchar2(255),  -- 파일 타입
  inputdate       date            default sysdate     -- 업로드 일자
);
create sequence imgID_seq;
select * from image;
drop table image;

-- 고객용 체크리스트 테이블
CREATE TABLE Checklist(
	checklistID 	number 				primary key,
	programNum		number		        ,
	reserNum		number,
	userid          varchar2(255)		,

	petFriendly 				number	default 0,
	allergyFriendly 			number	default 0,
	chronicIllness 				number	default 0,
	foodPreference 				number	default 0,
	privateTime 				number	default 0,
	preferredProgramType 		number	default 0,
	languageSupport 			number	default 0,
	smoking 					number	default 0,

	inputdate       date            default sysdate     -- 업로드 일자
);
create sequence checklistID_seq;
select * from Checklist;
drop table Checklist;
--https://velog.io/@hiy7030/TIL-%EC%B1%84%ED%8C%85-%EA%B8%B0%EB%8A%A5-ERD-%EC%84%A4%EA%B3%84
--CREATE TABLE Chat (
--	chatNum         number		            primary key,
--	programNum	    number		            references Program(programNum),
--	userid	        varchar2(255)		references User(userid),
--	hostid	        varchar2(255)		references User(userid)
--);
--create sequence chatNum_seq;





-- CULTURESTAY_BOARD 테이블 컬럼 변경
-- 데이터 있는 경우 컬럼을 추가하여 데이터 이동후 컬럼명 변경
ALTER TABLE CULTURESTAY_BOARD ADD (CONTENTS_CLOB CLOB);
UPDATE CULTURESTAY_BOARD SET CONTENTS_CLOB = TO_CLOB(CONTENTS);
ALTER TABLE CULTURESTAY_BOARD DROP COLUMN CONTENTS;
ALTER TABLE CULTURESTAY_BOARD RENAME COLUMN CONTENTS_CLOB TO CONTENTS;

-- Program 테이블 컬럼 추가 및, 컬럼 변경
--컬럼 추가
ALTER TABLE Program ADD (content CLOB);
-- 데이터 있는 경우 컬럼을 추가하여 데이터 이동후 컬럼명 변경
ALTER TABLE Program ADD (CONTENTS_CLOB CLOB);
UPDATE Program SET CONTENTS_CLOB = TO_CLOB(content);
ALTER TABLE Program DROP COLUMN content;
ALTER TABLE Program RENAME COLUMN CONTENTS_CLOB TO content;


commit;

--게시판 좋아요
drop TABLE Board_like;
CREATE TABLE Board_like(
	b_like_num 	number 				primary key,
	userid	    varchar2(255)	    ,
	boardnum	   number,
	inputdate       date            default sysdate     --작성일
);

-- cultureStay_board recommend 기본값 null 수정
ALTER TABLE cultureStay_board
MODIFY recommend INT DEFAULT 0;

UPDATE cultureStay_board
SET recommend = 0;

commit;