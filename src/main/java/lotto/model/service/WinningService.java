package lotto.model.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lotto.exception.ErrorCode;
import lotto.model.domain.Lotto;
import lotto.model.domain.WinningCriteria;
import lotto.model.domain.WinningNumbers;
import lotto.model.support.ProfitCalculator;

public class WinningService {
    private final ProfitCalculator profitCalculator;

    public WinningService(ProfitCalculator profitCalculator) {
        if (profitCalculator == null) {
            throw ErrorCode.MISSING_PROFIT_CALCULATOR.toIllegalStateException();
        }
        this.profitCalculator = profitCalculator;
    }

    public WinningNumbers createWinningNumbers(Lotto winningMainLotto, int bonusNumber) {
        if (winningMainLotto == null) {
            throw ErrorCode.MISSING_WINNING_NUMBERS.toIllegalStateException();
        }
        return winningMainLotto.toWinningNumbers(bonusNumber);
    }

    public Map<WinningCriteria, Integer> calculateStatistics(List<Lotto> lottos, WinningNumbers winningNumbers) {
        if (lottos == null || winningNumbers == null) {
            throw ErrorCode.STATISTICS_CALCULATION_FAILURE.toIllegalStateException();
        }
        if (lottos.stream().anyMatch(Objects::isNull)) {
            throw ErrorCode.NULL_LOTTO_IN_LIST.toIllegalStateException();
        }
        Map<WinningCriteria, Integer> stats = new EnumMap<>(WinningCriteria.class);
        for (WinningCriteria criteria : WinningCriteria.values()) {
            stats.put(criteria, 0);
        }

        for (Lotto lotto : lottos) {
            WinningCriteria rank = lotto.calculateRank(winningNumbers);
            stats.put(rank, stats.get(rank) + 1);
        }
        return stats;
    }

    public double calculateProfitRate(Map<WinningCriteria, Integer> stats, int purchaseMoney) {
        if (stats == null) {
            throw ErrorCode.MISSING_STATISTICS.toIllegalStateException();
        }
        if (purchaseMoney < 0) {
            throw ErrorCode.INVALID_PURCHASE_TOTAL.toIllegalStateException();
        }
        for (WinningCriteria criteria : WinningCriteria.values()) {
            if (!stats.containsKey(criteria)) {
                throw ErrorCode.INVALID_STATISTICS_CONTENT.toIllegalStateException();
            }
        }
        return profitCalculator.calculate(stats, purchaseMoney);
    }
}
