package com.abcde.cultureStay.messenger.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomMember {
    private Long chatRoomMemberId;
    private Long chatRoomId;
    private String userId;
    private Date joinedTimestamp;
    private String memberRole;
}

