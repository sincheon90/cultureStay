CREATE TABLE "User" (
	"userid"	NOT NULL		NOT NULL,
	"password"	NOT NULL		NULL,
	"username"	NOT NULL		NULL,
	"phone"	not null		NULL,
	"address"	NOT NULL		NULL,
	"email"	NOT NULL		NULL,
	"birth"	NOT NULL		NULL,
	"profile_picture"	NULL		NULL,
	"Field2"	일반		NULL,
	"Field"	NOT NULL		NULL
);

CREATE TABLE "Program" (
	"programNum"	VARCHAR(255)		NOT NULL,
	"userid"	NOT NULL		NOT NULL,
	"title"	NOT NULL		NULL,
	"content"	NOT NULL		NULL,
	"address"	NOT NULL		NULL,
	"price"	not null		NULL,
	"start_date"	NOT NULL		NULL,
	"end_date"	NOT NULL		NULL,
	"Field"	NOT NULL		NULL,
	"Field2"	NULL		NULL
);

CREATE TABLE "Review" (
	"Key"	NOT NULL		NOT NULL,
	"programNum"	NOT NULL		NOT NULL,
	"Key2"	NOT NULL		NOT NULL,
	"Field"	NOT NULL		NULL,
	"Field2"	NOT NULL		NULL,
	"Field3"	NOT NULL		NULL,
	"Field4"	NOT NULL		NULL,
	"Field5"	NULL		NULL,
	"Field6"	NOT NULL		NULL
);

CREATE TABLE "Chat" (
	"Key"	NOT NULL		NOT NULL,
	"programNum"	NOT NULL		NOT NULL,
	"userid"	NOT NULL		NOT NULL,
	"hostid"	NOT NULL		NULL,
	"Field4"	NOT NULL		NULL,
	"Field5"	NOT NULL		NULL,
	"Field"	NOT NULL		NULL
);

CREATE TABLE "Reservation" (
	"Key"	NOT NULL		NOT NULL,
	"programNum"	NOT NULL		NOT NULL,
	"userid"	NOT NULL		NOT NULL,
	"Field3"	NOT NULL		NULL,
	"Field4"	NOT NULL		NULL,
	"Field5"	NOT NULL		NULL,
	"Field6"	NOT NULL		NULL,
	"Field7"	NULL		NULL
);

CREATE TABLE "Board" (
	"boardnum"	NOT NULL		NOT NULL,
	"userid"	NOT NULL		NOT NULL,
	"title"	NOT NULL		NULL,
	"content"	NOT NULL		NULL,
	"inputdate"	NOT NULL		NULL,
	"hits"	0		NULL,
	"files"	NULL		NULL,
	"Field"	NOT NULL		NULL
);

CREATE TABLE "Reply" (
	"replynum"	NOT NULL		NOT NULL,
	"boardnum"	NOT NULL		NOT NULL,
	"userid"	NOT NULL		NOT NULL,
	"content"	NOT NULL		NULL,
	"inputdate"	NOT NULL		NULL
);

CREATE TABLE "Wish" (
	"userid"	NOT NULL		NOT NULL,
	"programNum"	NOT NULL		NOT NULL
);

ALTER TABLE "User" ADD CONSTRAINT "PK_USER" PRIMARY KEY (
	"userid"
);

ALTER TABLE "Program" ADD CONSTRAINT "PK_PROGRAM" PRIMARY KEY (
	"programNum"
);

ALTER TABLE "Review" ADD CONSTRAINT "PK_REVIEW" PRIMARY KEY (
	"Key"
);

ALTER TABLE "Chat" ADD CONSTRAINT "PK_CHAT" PRIMARY KEY (
	"Key"
);

ALTER TABLE "Reservation" ADD CONSTRAINT "PK_RESERVATION" PRIMARY KEY (
	"Key"
);

ALTER TABLE "Board" ADD CONSTRAINT "PK_BOARD" PRIMARY KEY (
	"boardnum"
);

ALTER TABLE "Reply" ADD CONSTRAINT "PK_REPLY" PRIMARY KEY (
	"replynum"
);

