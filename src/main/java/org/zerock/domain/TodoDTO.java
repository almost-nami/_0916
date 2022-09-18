package org.zerock.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TodoDTO {
    private String title;

    // @DateTimeFormat : @InitBinder 없이 문자열을 java.util.Date 형태로 변환
    // -> 문자열의 형식이 "yyyy/MM/dd"라면 자동으로 Date타입으로 변환됨
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date dueDate;
}
