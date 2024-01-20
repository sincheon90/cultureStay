package com.abcde.cultureStay.messenger.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private Long messageId;
    private Long chatRoomId;
    private String senderId;
    private String messageText;
    private Date timestamp;
    private Long isRead;
    private String messageType;
}

