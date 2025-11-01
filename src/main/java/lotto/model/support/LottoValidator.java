package lotto.model.support;

import java.util.List;
import lotto.model.domain.Lotto;

public interface LottoValidator {

    void validateMoney(int money);

    void validateNumbers(List<Integer> numbers);

    void validateBonus(Lotto main, int bonus);
}
