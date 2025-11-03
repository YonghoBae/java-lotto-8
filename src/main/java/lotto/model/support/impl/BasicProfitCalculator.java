package lotto.model.support.impl;

import java.util.Map;
import java.util.Objects;
import lotto.exception.ErrorCode;
import lotto.model.domain.WinningCriteria;
import lotto.model.support.ProfitCalculator;

public class BasicProfitCalculator implements ProfitCalculator {

    @Override
    public double calculate(Map<WinningCriteria, Integer> stats, int purchaseMoney) {
        if (stats == null || stats.isEmpty()) {
            throw ErrorCode.EMPTY_STATISTICS.toIllegalStateException();
        }
        if (stats.values().stream().anyMatch(Objects::isNull)) {
            throw ErrorCode.NULL_STATISTICS_VALUE.toIllegalStateException();
        }
        if (purchaseMoney < 0) {
            throw ErrorCode.INVALID_PURCHASE_FOR_PROFIT.toIllegalStateException();
        }
        if (purchaseMoney == 0) {
            return 0.0;
        }
        long totalPrize = stats.entrySet().stream()
                .mapToLong(entry -> entry.getKey().prizeMoney() * entry.getValue())
                .sum();

        return ((double) totalPrize / purchaseMoney) * 100.0;
    }
}
