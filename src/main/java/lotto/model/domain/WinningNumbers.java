package lotto.model.domain;

import java.util.List;
import lotto.exception.ErrorCode;

public class WinningNumbers {

    private final List<Integer> mainNumbers;
    private final int bonusNumber;

    public WinningNumbers(List<Integer> mainNumbers, int bonusNumber) {
        validateBonusNumber(mainNumbers, bonusNumber);
        this.mainNumbers = List.copyOf(mainNumbers);
        this.bonusNumber = bonusNumber;
    }

    private void validateBonusNumber(List<Integer> mainNumbers, int bonus) {
        if (bonus < 1 || bonus > 45) {
            throw ErrorCode.INVALID_BONUS_RANGE_SHORT.toIllegalArgumentException();
        }
        if (mainNumbers.contains(bonus)) {
            throw ErrorCode.DUPLICATE_BONUS_NUMBER.toIllegalArgumentException();
        }
    }

    public boolean containsMainNumber(int number) {
        return mainNumbers.contains(number);
    }

    public boolean isBonusNumber(int number) {
        return bonusNumber == number;
    }
}
