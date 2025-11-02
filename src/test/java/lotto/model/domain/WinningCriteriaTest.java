package lotto.model.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WinningCriteriaTest {

    @DisplayName("5개 번호와 보너스 번호가 일치하면 2등이다.")
    @Test
    void valueOf_returnsSecondPlaceWhenMatchBonus() {
        WinningCriteria criteria = WinningCriteria.valueOf(5, true);

        assertThat(criteria).isEqualTo(WinningCriteria.SECOND);
    }

    @DisplayName("5개 번호만 일치하면 3등이다.")
    @Test
    void valueOf_returnsThirdPlaceWhenNoBonus() {
        WinningCriteria criteria = WinningCriteria.valueOf(5, false);

        assertThat(criteria).isEqualTo(WinningCriteria.THIRD);
    }

    @DisplayName("일치 개수가 조건을 만족하지 않으면 낙첨이다.")
    @Test
    void valueOf_returnsMissWhenNoCriteria() {
        WinningCriteria criteria = WinningCriteria.valueOf(2, false);

        assertThat(criteria).isEqualTo(WinningCriteria.MISS);
    }
}
