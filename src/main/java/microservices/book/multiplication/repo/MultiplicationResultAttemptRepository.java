package microservices.book.multiplication.repo;

import microservices.book.multiplication.domain.MultiplicationResultAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MultiplicationResultAttemptRepository extends JpaRepository<MultiplicationResultAttempt, Long> {

    List<MultiplicationResultAttempt> findTop5ByUserAliasOrderByIdDesc(String userAlias);
    MultiplicationResultAttempt findOne(Long resultId);
}
