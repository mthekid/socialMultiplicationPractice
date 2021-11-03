package microservices.book.multiplication.repo;

import microservices.book.multiplication.domain.Multiplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultiplicationRepository extends JpaRepository<Multiplication, Long> {
}
