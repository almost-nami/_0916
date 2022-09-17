package org.zerock.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TodoDTO {
    private String title;

    @DateTimeFormat(pattern = "yyyy/MM/dd")     // @InitBinder 필요없음
    private Date dueDate;
}
