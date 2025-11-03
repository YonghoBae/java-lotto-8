package lotto.model.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lotto.common.LottoConstants;
import lotto.exception.ErrorCode;

public class Lotto {
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = List.copyOf(numbers);
    }

    private void validate(List<Integer> numbers) {
        if (numbers.size() != LottoConstants.LOTTO_NUMBER_COUNT) {
            throw ErrorCode.INVALID_LOTTO_NUMBER_COUNT.toIllegalArgumentException();
        }
        Set<Integer> uniqueNumbers = new HashSet<>(numbers);
        if (uniqueNumbers.size() != LottoConstants.LOTTO_NUMBER_COUNT) {
            throw ErrorCode.DUPLICATE_LOTTO_NUMBER.toIllegalArgumentException();
        }
        // 3. 범위 검사
        if (hasInvalidRange(numbers)) {
            throw ErrorCode.INVALID_LOTTO_NUMBER_RANGE.toIllegalArgumentException();
        }
    }

    private boolean hasInvalidRange(List<Integer> numbers) {
        return numbers.stream()
                .anyMatch(number -> number < LottoConstants.LOTTO_NUMBER_MIN || number > LottoConstants.LOTTO_NUMBER_MAX);
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
