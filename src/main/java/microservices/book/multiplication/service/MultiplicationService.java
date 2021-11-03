package microservices.book.multiplication.service;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;

import java.util.List;

public interface MultiplicationService {

    /**
     *
     * 두 개의 무작위 인수를 담은 {@link Multiplication} 객체를 생성한다.
     * 생성된 숫자의 범위는 11 ~ 99. 이다..
     *
     * @return 무작위 인수를 담은 {@link Multiplication} 객체
     */
    Multiplication createRandomMultiplication();

    /**
     * @return 곱셈 계싼 결과가 맞으면 true, 아니면 false 반환
     */
    boolean checkAttempt(final MultiplicationResultAttempt resultAttempt);
    List<MultiplicationResultAttempt> getStatsForUser(String userAlias);

}
