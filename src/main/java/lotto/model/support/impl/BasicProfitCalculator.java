package lotto.model.support.impl;

import java.util.Map;
import lotto.model.domain.WinningCriteria;
import lotto.model.support.ProfitCalculator;

public class BasicProfitCalculator implements ProfitCalculator {

    @Override
    public double calculate(Map<WinningCriteria, Integer> stats, int purchaseMoney) {
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
