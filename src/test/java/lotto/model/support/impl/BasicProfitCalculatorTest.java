package lotto.model.support.impl;

import lotto.model.domain.WinningCriteria;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BasicProfitCalculatorTest {

    private final BasicProfitCalculator calculator = new BasicProfitCalculator();

    @DisplayName("구입 금액이 0이면 수익률은 0이다.")
    @Test
    void calculate_returnsZeroWhenPurchaseIsZero() {
        Map<WinningCriteria, Integer> stats = new EnumMap<>(WinningCriteria.class);
        for (WinningCriteria criteria : WinningCriteria.values()) {
            stats.put(criteria, 0);
        }

        assertThat(calculator.calculate(stats, 0)).isZero();
    }

    @DisplayName("당첨 금액 합계를 구입 금액으로 나누어 수익률을 계산한다.")
    @Test
    void calculate_returnsProfitRate() {
        Map<WinningCriteria, Integer> stats = new EnumMap<>(WinningCriteria.class);
        for (WinningCriteria criteria : WinningCriteria.values()) {
            stats.put(criteria, 0);
        }
        stats.put(WinningCriteria.FIRST, 1);
        stats.put(WinningCriteria.FIFTH, 2);

        long totalPrize = WinningCriteria.FIRST.prizeMoney() + WinningCriteria.FIFTH.prizeMoney() * 2;
        int purchaseMoney = 100_000;

        double expected = ((double) totalPrize / purchaseMoney) * 100.0;

        assertThat(calculator.calculate(stats, purchaseMoney)).isEqualTo(expected);
    }
}
