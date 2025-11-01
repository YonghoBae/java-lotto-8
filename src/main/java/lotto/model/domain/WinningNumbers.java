package lotto.model.domain;

import java.util.List;

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
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1~45 사이여야 합니다.");
        }
        if (mainNumbers.contains(bonus)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    public boolean containsMainNumber(int number) {
        return mainNumbers.contains(number);
    }

    public boolean isBonusNumber(int number) {
        return bonusNumber == number;
    }
}
