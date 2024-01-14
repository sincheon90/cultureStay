package com.abcde.cultureStay.messenger.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    private Long chatRoomId;
    private String chatRoomName;
    private Date createTimestamp;
    private String createdUserId;
}

