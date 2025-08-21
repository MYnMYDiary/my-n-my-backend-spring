package com.mynmy.springbackend.domain.diary.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public class Request {

    public record create(
            Long userId,

            @Schema(description = "카테고리 ID")
            @NotBlank(message = "categoryId는 비어 있을 수 없습니다.")
            Long categoryId,

            @Schema(description = "연(YYYY)")
            @NotBlank(message = "year는 비어 있을 수 없습니다.")
            @Pattern(regexp = "^[0-9]{4}$", message = "year는 4자리 숫자(YYYY)여야 합니다.")
            String year,

            @Schema(description = "월(1~12)")
            @NotBlank(message = "month는 비어 있을 수 없습니다.")
            @Pattern(regexp = "^(?:[1-9]|1[0-2])$", message = "month는 1~12 사이의 숫자여야 합니다.")
            String month,

            @Schema(description = "제목")
            @NotBlank(message = "title은 비어 있을 수 없습니다.")
            @Size(max = 100, message = "title은 최대 100자까지 가능합니다.")
            String title,

            @Schema(description = "내용")
            @NotBlank(message = "content는 비어 있을 수 없습니다.")
            String content,

            @Schema(description = "이미지 URL(옵션)")
            @Size(max = 500, message = "image URL은 최대 500자까지 가능합니다.")
            String image,

            @Schema(description = "태그(옵션)")
            List<@NotBlank(message = "tags의 각 항목은 비어 있을 수 없습니다.") String> tags
    ) {}
}
