package org.zerock.guestbook.repository;

import com.fasterxml.jackson.databind.util.ArrayBuilders;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.entity.QGuestbook;

import javax.swing.text.StyledEditorKit;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {

    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies() {

        IntStream.rangeClosed(1, 300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder().title("Title..." +i).content("Content..."+i)
                    .content("Content..."+i).writer("user"+(i % 10)).build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    @Test
    public void updateTest() {
        //Optional : 해당 변수가 null일 가능성 표현, get 객체호출?
        Optional<Guestbook> result = guestbookRepository.findById(300L);
        if(result.isPresent()) {
            Guestbook guestbook = result.get();

            guestbook.changeTitle("Changed Title....");
            guestbook.changeContent("Changed Content...");

            guestbookRepository.save(guestbook);
        }
    }


    public void testQuery1() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        //1 : 동적 처리를 위한 Q도메인 클래스 불러옴. 사용시 Entity 필드의 값을 변수로 사용할 수 있음
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "1";
        //2 : 불린빌더는 where문에 들어가는 조건을 넣어준느 컨테이너
        BooleanBuilder builder = new BooleanBuilder();
        //3 : 원하는 조건은 필드 값과 같이 결합하여 생성. builder안엔 predicate 타입이어야한다.(자바 predicate아님)
        BooleanExpression expression = qGuestbook.title.contains(keyword);
        //4 and, or 등 키워드와 결합
        builder.and(expression);
        //5 QuerydslPredicateExcutor 인터페이스의 findAll 사용
        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    @Test
    public void testQuery2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent); //1===

        builder.and(exAll);

        builder.and(qGuestbook.gno.gt(0L));

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream().forEach(guestbook ->  {
            System.out.println(guestbook);
        });
    }
}
