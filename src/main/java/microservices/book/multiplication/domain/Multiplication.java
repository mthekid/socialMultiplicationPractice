package microservices.book.multiplication.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@ToString
@EqualsAndHashCode
// JPA 등록
@Entity
public final class Multiplication {

    @Id
    @GeneratedValue
    @Column(name = "MULTIPLICATION_ID")
    private Long id;

    // 인수
    private int factorA;
    private int factorB;

    public Multiplication() {
        this(0, 0);
    }

    public Multiplication(int a, int b) {
        this.factorA = a;
        this.factorB = b;
    }

    public static void main(String[] args) {
        Multiplication mul = new Multiplication(1,2);
        System.out.println(mul.getFactorA());
        System.out.println(mul.getFactorB());
    }
}
