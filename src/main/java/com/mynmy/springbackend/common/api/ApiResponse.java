package com.mynmy.springbackend.common.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private final boolean success;
    private final int status;
    private final String message;
    private final T data;     // 단건 or List<T> 둘 다 가능
    private final Meta meta;  // 목록일 경우만 세팅

    // 무한스크롤(Cursor) 기반 메타정보
    @Getter
    @Builder
    public static class Meta {
        private final Long nextCursor; // 다음 요청 커서
        private final boolean hasNext; // 다음 데이터 여부
    }

    // 단건 성공
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .success(true)
                .status(200)
                .message(message)
                .data(data)
                .build();
    }

    // 목록 성공
    public static <T> ApiResponse<List<T>> success(List<T> dataList, String message, Long nextCursor, boolean hasNext) {
        return ApiResponse.<List<T>>builder()
                .success(true)
                .status(200)
                .message(message)
                .data(dataList)
                .meta(Meta.builder()
                        .nextCursor(nextCursor)
                        .hasNext(hasNext)
                        .build())
                .build();
    }


}
