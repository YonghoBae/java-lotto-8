package lotto.model.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = List.copyOf(numbers);
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 6개여야 합니다.");
        }
        Set<Integer> uniqueNumbers = new HashSet<>(numbers);
        if (uniqueNumbers.size() != 6) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 중복될 수 없습니다.");
        }
        // 3. 범위 검사
        if (hasInvalidRange(numbers)) {
            throw new IllegalArgumentException("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    private boolean hasInvalidRange(List<Integer> numbers) {
        return numbers.stream().anyMatch(n -> n < 1 || n > 45);
    }

    @Override
    public String toString() {
        return numbers.toString();
    }

    public WinningCriteria calculateRank(WinningNumbers winningNumbers) {
        int matchCount = countMatchingNumbers(winningNumbers);
        boolean matchBonus = numbers.stream().anyMatch(winningNumbers::isBonusNumber);

        return WinningCriteria.valueOf(matchCount, matchBonus);
    }

    private int countMatchingNumbers(WinningNumbers winningNumbers) {
        return (int) this.numbers.stream()
                .filter(winningNumbers::containsMainNumber)
                .count();
    }

    public boolean contains(int number) {
        return this.numbers.contains(number);
    }

    public WinningNumbers toWinningNumbers(int bonusNumber) {
        return new WinningNumbers(numbers, bonusNumber);
    }
}
