package microservices.book.multiplication.service;

public interface RandomGeneratorService {

    /**
     * @return 무작위로 만든 11이상 99이하의 인수
     */
    int generateRandomFactor();
}
