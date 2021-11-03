package microservices.book.multiplication.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class User {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    private final String alias;

    public User(String alias) {
        this.alias = alias;
    }

    // JSON/JPA를 위한 빈 생성자.
    protected User() {
        alias = null;
    }
}
