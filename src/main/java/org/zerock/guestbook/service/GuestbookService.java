package org.zerock.guestbook.service;

import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.dto.PageResultDTO;
import org.zerock.guestbook.entity.Guestbook;

import java.util.Optional;

public interface GuestbookService {
    Long register(GuestbookDTO dto);

    void remove(Long gno);

    //제목, 내용 수정
    void modify(GuestbookDTO dto);


    //방명록 조회 처리
    GuestbookDTO read(Long gno);

    PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO);

    default Guestbook dtoToEntity(GuestbookDTO dto) {
        Guestbook entity = Guestbook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }


    default GuestbookDTO entityToDto(Guestbook entity) {
        GuestbookDTO dto = GuestbookDTO.builder()
                        .gno(entity.getGno())
                        .title(entity.getTitle())
                        .content(entity.getContent())
                        .writer(entity.getWriter())
                        .regDate(entity.getRegDate())
                        .modDate(entity.getModDate()).build();
        return dto;
    }
}
