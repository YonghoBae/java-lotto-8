package lotto.model.support.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lotto.common.LottoConstants;
import lotto.exception.ErrorCode;
import lotto.model.domain.Lotto;
import lotto.model.support.LottoValidator;

public class DefaultLottoValidator implements LottoValidator {

    @Override
    public void validateMoney(int money) {
        if (money <= 0 || money % LottoConstants.LOTTO_PRICE_UNIT != 0) {
            throw ErrorCode.INVALID_PURCHASE_UNIT.toIllegalArgumentException();
        }
    }

    @Override
    public void validateNumbers(List<Integer> numbers) {
        if (numbers.size() != LottoConstants.LOTTO_NUMBER_COUNT) {
            throw ErrorCode.INVALID_LOTTO_NUMBER_COUNT.toIllegalArgumentException();
        }
        if (hasInvalidRange(numbers)) {
            throw ErrorCode.INVALID_LOTTO_NUMBER_RANGE.toIllegalArgumentException();
        }
        if (hasDuplicates(numbers)) {
            throw ErrorCode.DUPLICATE_LOTTO_NUMBER.toIllegalArgumentException();
        }
    }

    @Override
    public void validateBonus(Lotto main, int bonus) {
        if (bonus < LottoConstants.BONUS_NUMBER_MIN || bonus > LottoConstants.BONUS_NUMBER_MAX) {
            throw ErrorCode.INVALID_BONUS_RANGE.toIllegalArgumentException();
        }
        if (main.contains(bonus)) {
            throw ErrorCode.DUPLICATE_BONUS_NUMBER.toIllegalArgumentException();
        }
    }

    private boolean hasInvalidRange(List<Integer> numbers) {
        return numbers.stream()
                .anyMatch(number -> number < LottoConstants.LOTTO_NUMBER_MIN || number > LottoConstants.LOTTO_NUMBER_MAX);
    }

    private boolean hasDuplicates(List<Integer> numbers) {
        Set<Integer> unique = new HashSet<>(numbers);
        return unique.size() != numbers.size();
    }
}
