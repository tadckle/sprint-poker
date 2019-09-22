package org.zhxie.sprintpoker.entity.dto;

import lombok.Data;

@Data
public class PagebleDTO {
    private int curPage;
    private int totalPage;
    private String roomName;
}
