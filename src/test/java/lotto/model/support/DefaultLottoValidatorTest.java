package lotto.model.support;

import lotto.model.domain.Lotto;
import lotto.model.support.impl.DefaultLottoValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultLottoValidatorTest {

    private final DefaultLottoValidator validator = new DefaultLottoValidator();

    @DisplayName("구입 금액이 1,000원 단위의 양수이면 통과한다.")
    @Test
    void validateMoney_acceptsValidAmount() {
        assertThatCode(() -> validator.validateMoney(3000))
                .doesNotThrowAnyException();
    }

    @DisplayName("구입 금액이 0이거나 음수이면 예외가 발생한다.")
    @Test
    void validateMoney_rejectsNonPositive() {
        assertThatThrownBy(() -> validator.validateMoney(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("구입 금액이 1,000원 단위가 아니면 예외가 발생한다.")
    @Test
    void validateMoney_requiresMultiplesOfThousand() {
        assertThatThrownBy(() -> validator.validateMoney(1500))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("로또 번호가 6개이며 범위 조건을 만족하면 통과한다.")
    @Test
    void validateNumbers_acceptsValidNumbers() {
        assertThatCode(() -> validator.validateNumbers(List.of(1, 2, 3, 4, 5, 6)))
                .doesNotThrowAnyException();
    }

    @DisplayName("로또 번호에 중복이 있으면 예외가 발생한다.")
    @Test
    void validateNumbers_rejectsDuplicates() {
        assertThatThrownBy(() -> validator.validateNumbers(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("로또 번호가 1~45 범위를 벗어나면 예외가 발생한다.")
    @Test
    void validateNumbers_rejectsOutOfRange() {
        assertThatThrownBy(() -> validator.validateNumbers(List.of(0, 2, 3, 4, 5, 6)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
    @Test
    void validateBonus_rejectsDuplicateNumber() {
        Lotto winning = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        assertThatThrownBy(() -> validator.validateBonus(winning, 6))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("보너스 번호가 1~45 범위를 벗어나면 예외가 발생한다.")
    @Test
    void validateBonus_rejectsOutOfRange() {
        Lotto winning = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        assertThatThrownBy(() -> validator.validateBonus(winning, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }
}
