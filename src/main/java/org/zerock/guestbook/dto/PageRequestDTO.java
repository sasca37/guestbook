package org.zerock.guestbook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Data

public class PageRequestDTO {
    private int page;
    private int size;
    private String type;
    private String keyword;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }
    //Pageable 객체 생성 하는 것이 주 목적, JPA에서 페이지 번호는 0부터 시작
    public Pageable getPageable(Sort sort)
    {
        return PageRequest.of(page -1, size, sort);
    }
}
