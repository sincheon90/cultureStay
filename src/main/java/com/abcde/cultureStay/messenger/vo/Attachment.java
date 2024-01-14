package com.abcde.cultureStay.messenger.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    private Long attachmentId;
    private Long messageId;
    private String filePath;
    private String fileType;
}

