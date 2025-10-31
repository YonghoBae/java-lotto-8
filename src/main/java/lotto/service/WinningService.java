package lotto.service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lotto.Lotto;
import lotto.WinningCriteria;
import lotto.WinningNumbers;

public class WinningService {
    public WinningNumbers createWinningNumbers(Lotto mainLotto, int bonusNumber) {
        return new WinningNumbers(mainLotto, bonusNumber);
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
        long totalPrize = 0;
        for (Map.Entry<WinningCriteria, Integer> entry : stats.entrySet()) {
            totalPrize += entry.getKey().getPrizeMoney() * entry.getValue();
        }

        if (purchaseMoney == 0) {
            return 0.0;
        }

        return ((double) totalPrize / purchaseMoney) * 100.0;
    }
}
