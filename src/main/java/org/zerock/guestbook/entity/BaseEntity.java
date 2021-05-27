package org.zerock.guestbook.entity;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;
//실제 엔티티가생성되는 클래스가 아님을 명시. 부모클래스로만으로 사용
@MappedSuperclass
//객체감지를 위한 리스너 설정 + Application부분에도 EnableJpaAuditing 설정필요
@EntityListeners(value = {AuditingEntityListener.class})
@Getter
abstract class BaseEntity {

    @CreatedDate
    //해당 객체 regdate를 db에 반영할 때 컬럼값이 변경되지않도록 updatable = false
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name ="moddate" )
    private LocalDateTime modDate;
}
