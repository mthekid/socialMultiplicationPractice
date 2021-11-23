package microservices.book.multiplication.service.impl;

import microservices.book.multiplication.domain.Multiplication;
import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import microservices.book.multiplication.domain.User;
import microservices.book.multiplication.event.EventDispatcher;
import microservices.book.multiplication.event.MultiplicationSolvedEvent;
import microservices.book.multiplication.repo.MultiplicationResultAttemptRepository;
import microservices.book.multiplication.repo.UserRepository;
import microservices.book.multiplication.service.RandomGeneratorService;
import microservices.book.multiplication.service.impl.MutiplicationServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class MutiplicationServiceImplTest {

    private MutiplicationServiceImpl mutiplicationServiceImpl;

    @Mock
    private RandomGeneratorService randomGeneratorService;

    @Mock
    private MultiplicationResultAttemptRepository attemptRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventDispatcher eventDispatcher;

    @BeforeEach
    public void setUp() {
        // mock 객체 초기화
        MockitoAnnotations.openMocks(this);
        mutiplicationServiceImpl = new MutiplicationServiceImpl(randomGeneratorService, attemptRepository, userRepository, eventDispatcher);
    }

    @Test
    public void createRandomMultiplicationTest() {

        // given (목 객체가 처음에 50, 나중에 30 반환하게 설정)
        given(randomGeneratorService.generateRandomFactor()).willReturn(50, 30);

        // when
        Multiplication multiplication = mutiplicationServiceImpl.createRandomMultiplication();

        //assert
        assertThat(multiplication.getFactorA()).isEqualTo(50);
        assertThat(multiplication.getFactorB()).isEqualTo(30);
//        assertThat(multiplication.getResult()).isEqualTo(1500);
    }

    @Test
    public void checkCorrectAttemptTest() {
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("Jone_doe");

        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3000, false);
        MultiplicationResultAttempt verifiedAttempt = new MultiplicationResultAttempt(user, multiplication, 3000, true);
        MultiplicationSolvedEvent event = new MultiplicationSolvedEvent(attempt.getId(), attempt.getUser().getId(), true);
        given(userRepository.findByAlias("jone_doe")).willReturn(Optional.empty());

        //when
        boolean attemptResult = mutiplicationServiceImpl.checkAttempt(attempt);

        //then
        assertThat(attemptResult).isTrue();
        verify(attemptRepository).save(verifiedAttempt);
        verify(eventDispatcher).send(eq(event));
    }

    @Test
    public void checkWrongAttemptTest() {
        // given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("john_doe");
        MultiplicationResultAttempt attempt = new MultiplicationResultAttempt(user, multiplication, 3010, false);
        MultiplicationSolvedEvent event = new MultiplicationSolvedEvent(attempt.getId(), attempt.getUser().getId(), false);

        given(userRepository.findByAlias("john_doe")).willReturn(Optional.empty());

        // when
        boolean attemptResult = mutiplicationServiceImpl.checkAttempt(attempt);

        // then
        assertThat(attemptResult).isFalse();
        verify(attemptRepository).save(attempt);
        verify(eventDispatcher).send(eq(event));
    }

    @Test
    public void retrieveStatsTest() {
        //given
        Multiplication multiplication = new Multiplication(50, 60);
        User user = new User("John_doe");

        MultiplicationResultAttempt attempt1 = new MultiplicationResultAttempt(user, multiplication, 3010, false);
        MultiplicationResultAttempt attempt2 = new MultiplicationResultAttempt(user, multiplication, 3051, false);

        List<MultiplicationResultAttempt> lastsAttempts = Lists.newArrayList(attempt1, attempt2);

        given(userRepository.findByAlias("John_doe")).willReturn(Optional.empty());
        given(attemptRepository.findTop5ByUserAliasOrderByIdDesc("John_doe")).willReturn(lastsAttempts);

        //when
        List<MultiplicationResultAttempt> lastestAttemptResult = mutiplicationServiceImpl.getStatsForUser("John_doe");

        //then
        assertThat(lastestAttemptResult).isEqualTo(lastsAttempts);
    }

}