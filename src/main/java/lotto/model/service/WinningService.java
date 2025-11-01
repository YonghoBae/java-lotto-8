package lotto.model.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lotto.model.domain.Lotto;
import lotto.model.domain.WinningCriteria;
import lotto.model.domain.WinningNumbers;
import lotto.model.support.ProfitCalculator;

public class WinningService {
    private final ProfitCalculator profitCalculator;

    public WinningService(ProfitCalculator profitCalculator) {
        this.profitCalculator = Objects.requireNonNull(profitCalculator, "profitCalculator must not be null");
    }

    public WinningNumbers createWinningNumbers(Lotto winningMainLotto, int bonusNumber) {
        return new WinningNumbers(winningMainLotto.getNumbers(), bonusNumber);
    }

    public Map<WinningCriteria, Integer> calculateStatistics(List<Lotto> lottos, WinningNumbers winningNumbers) {
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
        return profitCalculator.calculate(stats, purchaseMoney);
    }
}
