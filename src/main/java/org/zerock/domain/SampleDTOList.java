package org.zerock.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// SampleController ex02에서 SampleDTO 타입의 객체를 여러개 전달할 때 필요
@Data
public class SampleDTOList {
    private List<SampleDTO> list;

    public SampleDTOList() {
        list = new ArrayList<>();
    }
}
