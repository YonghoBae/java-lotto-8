package lotto.domain;

import java.util.List;

public class WinningNumbers {

    private final Lotto mainNumbers;
    private final int bonusNumber;

    public WinningNumbers(Lotto mainNumbers, int bonusNumber) {
        validateBonusNumber(mainNumbers, bonusNumber);
        this.mainNumbers = mainNumbers;
        this.bonusNumber = bonusNumber;
    }

    private void validateBonusNumber(Lotto mainLotto, int bonus) {
        if (bonus < 1 || bonus > 45) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1~45 사이여야 합니다.");
        }
        if (mainLotto.contains(bonus)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    public List<Integer> getMainNumbers() {
        return mainNumbers.getNumbers();
    }

    public int getBonusNumber() {
        return bonusNumber;
    }
}
