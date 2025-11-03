package lotto.model.support;

import lotto.model.support.impl.DefaultInputParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DefaultInputParserTest {

    private final DefaultInputParser parser = new DefaultInputParser();

    @DisplayName("숫자 문자열을 양쪽 공백을 제거하고 정수로 변환한다.")
    @Test
    void parseIntStrict_trimsWhitespace() {
        assertThat(parser.parseIntStrict(" 2000 ")).isEqualTo(2000);
    }

    @DisplayName("숫자가 아닌 값을 정수로 변환하려 할 때 예외가 발생하고 메시지를 확인한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1o00", "abc", "", " "})
    void parseIntStrict_rejectsNonNumeric(String input) {
        assertThatThrownBy(() -> parser.parseIntStrict(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }

    @DisplayName("쉼표로 구분된 숫자들을 정수 리스트로 변환한다.")
    @Test
    void parseNumbers_parsesNumbers() {
        assertThat(parser.parseNumbers("1, 2,3 , 4,5,6"))
                .containsExactly(1, 2, 3, 4, 5, 6);
    }

    @DisplayName("쉼표로 구분된 값에 숫자가 아닌 항목이 있으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1, two, 3, 4, 5, 6", "", "1, , 3, 4, 5, 6"})
    void parseNumbers_rejectsInvalidInputs(String csv) {
        assertThatThrownBy(() -> parser.parseNumbers(csv))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR]");
    }
}
