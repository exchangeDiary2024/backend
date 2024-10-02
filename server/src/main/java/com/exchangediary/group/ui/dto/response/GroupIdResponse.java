package com.exchangediary.group.ui.dto.response;

import com.exchangediary.group.domain.entity.Group;
import lombok.Builder;

@Builder
public record GroupIdResponse(
    Long groupId
) {
    public static GroupIdResponse from(Long groupId) {
        return GroupIdResponse.builder()
                .groupId(groupId)
                .build();
    }
}
