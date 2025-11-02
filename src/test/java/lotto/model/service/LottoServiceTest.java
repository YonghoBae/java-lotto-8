package lotto.model.service;

import lotto.model.domain.Lotto;
import lotto.model.support.InputParser;
import lotto.model.support.LottoNumberGenerator;
import lotto.model.support.LottoValidator;
import lotto.model.support.impl.DefaultInputParser;
import lotto.model.support.impl.DefaultLottoValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoServiceTest {

    private final InputParser inputParser = new DefaultInputParser();
    private final LottoValidator lottoValidator = new DefaultLottoValidator();

    @DisplayName("구입 금액 만큼 로또를 발행하고 생성기가 호출된 횟수를 검증한다.")
    @Test
    void createLotto_generatesTickets() {
        FakeLottoNumberGenerator generator = new FakeLottoNumberGenerator();
        LottoService lottoService = new LottoService(generator, inputParser, lottoValidator);

        List<Lotto> lottos = lottoService.createLotto(5000);

        assertThat(lottos).hasSize(5);
        assertThat(generator.callCount()).isEqualTo(5);
    }

    @DisplayName("구입 금액이 최소 금액보다 적으면 예외가 발생한다.")
    @Test
    void createLotto_rejectsBelowMinimum() {
        FakeLottoNumberGenerator generator = new FakeLottoNumberGenerator();
        LottoService lottoService = new LottoService(generator, inputParser, lottoValidator);

        assertThatThrownBy(() -> lottoService.createLotto(500))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("유효한 문자열 금액을 검증해 정수로 변환한다.")
    @Test
    void toValidMoney_parsesAndValidates() {
        LottoService lottoService = new LottoService(new FakeLottoNumberGenerator(), inputParser, lottoValidator);

        assertThat(lottoService.toValidMoney(" 2000 ")).isEqualTo(2000);
    }

    @DisplayName("숫자가 아닌 금액 입력은 예외를 발생시킨다.")
    @Test
    void toValidMoney_rejectsNonNumeric() {
        LottoService lottoService = new LottoService(new FakeLottoNumberGenerator(), inputParser, lottoValidator);

        assertThatThrownBy(() -> lottoService.toValidMoney("1000j"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("로또 번호 입력이 조건을 만족하지 않으면 예외가 발생한다.")
    @Test
    void toValidLotto_rejectsInvalidNumbers() {
        LottoService lottoService = new LottoService(new FakeLottoNumberGenerator(), inputParser, lottoValidator);

        assertThatThrownBy(() -> lottoService.toValidLotto("1,2,3,4,5"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
    @Test
    void toValidBonus_rejectsDuplicateNumber() {
        LottoService lottoService = new LottoService(new FakeLottoNumberGenerator(), inputParser, lottoValidator);
        Lotto winningMain = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        assertThatThrownBy(() -> lottoService.toValidBonus(winningMain, "6"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("보너스 번호가 숫자가 아니면 예외가 발생한다.")
    @Test
    void toValidBonus_rejectsNonNumeric() {
        LottoService lottoService = new LottoService(new FakeLottoNumberGenerator(), inputParser, lottoValidator);
        Lotto winningMain = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        assertThatThrownBy(() -> lottoService.toValidBonus(winningMain, "seven"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("보너스 번호가 범위를 벗어나면 예외가 발생한다.")
    @Test
    void toValidBonus_rejectsOutOfRange() {
        LottoService lottoService = new LottoService(new FakeLottoNumberGenerator(), inputParser, lottoValidator);
        Lotto winningMain = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        assertThatThrownBy(() -> lottoService.toValidBonus(winningMain, "0"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("보너스 번호 입력을 검증하여 정수로 변환한다.")
    @Test
    void toValidBonus_returnsParsedBonus() {
        LottoService lottoService = new LottoService(new FakeLottoNumberGenerator(), inputParser, lottoValidator);
        Lotto winningMain = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        assertThat(lottoService.toValidBonus(winningMain, "7")).isEqualTo(7);
    }

    private static class FakeLottoNumberGenerator implements LottoNumberGenerator {
        private final AtomicInteger counter = new AtomicInteger();

        @Override
        public Lotto create() {
            counter.incrementAndGet();
            return new Lotto(List.of(1, 2, 3, 4, 5, 6));
        }

        int callCount() {
            return counter.get();
        }
    }
}
