package lotto.model.support;

import java.util.Map;
import lotto.model.domain.WinningCriteria;

public interface ProfitCalculator {
    double calculate(Map<WinningCriteria, Integer> stats, int purchaseMoney);
}
