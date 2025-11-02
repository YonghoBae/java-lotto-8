package lotto.model.service;

import lotto.model.domain.Lotto;
import lotto.model.domain.WinningCriteria;
import lotto.model.domain.WinningNumbers;
import lotto.model.support.impl.BasicProfitCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class WinningServiceTest {

    private final WinningService winningService = new WinningService(new BasicProfitCalculator());

    @DisplayName("발행된 로또들의 등수를 집계하여 통계를 구한다.")
    @Test
    void calculateStatistics_countsRanks() {
        Lotto winningMain = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        WinningNumbers winningNumbers = winningService.createWinningNumbers(winningMain, 7);
        List<Lotto> lottos = List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),   // 6개 일치
                new Lotto(List.of(1, 2, 3, 4, 5, 7)),   // 5개 + 보너스
                new Lotto(List.of(1, 2, 3, 4, 5, 8)),   // 5개
                new Lotto(List.of(1, 2, 3, 4, 9, 10)),  // 4개
                new Lotto(List.of(1, 2, 3, 11, 12, 13)),// 3개
                new Lotto(List.of(10, 11, 12, 13, 14, 15)) // 낙첨
        );

        Map<WinningCriteria, Integer> stats = winningService.calculateStatistics(lottos, winningNumbers);

        assertThat(stats.get(WinningCriteria.FIRST)).isEqualTo(1);
        assertThat(stats.get(WinningCriteria.SECOND)).isEqualTo(1);
        assertThat(stats.get(WinningCriteria.THIRD)).isEqualTo(1);
        assertThat(stats.get(WinningCriteria.FOURTH)).isEqualTo(1);
        assertThat(stats.get(WinningCriteria.FIFTH)).isEqualTo(1);
        assertThat(stats.get(WinningCriteria.MISS)).isEqualTo(1);
    }

    @DisplayName("집계된 통계와 구입 금액을 이용해 수익률을 계산한다.")
    @Test
    void calculateProfitRate_returnsExpectedValue() {
        Lotto winningMain = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        WinningNumbers winningNumbers = winningService.createWinningNumbers(winningMain, 7);
        List<Lotto> lottos = List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                new Lotto(List.of(1, 2, 3, 4, 5, 7))
        );
        Map<WinningCriteria, Integer> stats = winningService.calculateStatistics(lottos, winningNumbers);

        int purchaseMoney = 2 * 1000;
        long totalPrize = WinningCriteria.FIRST.prizeMoney() + WinningCriteria.SECOND.prizeMoney();
        double expected = ((double) totalPrize / purchaseMoney) * 100.0;

        assertThat(winningService.calculateProfitRate(stats, purchaseMoney)).isEqualTo(expected);
    }
}
