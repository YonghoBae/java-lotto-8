package lotto.model.support;

import java.util.List;

public interface InputParser {
    int parseIntStrict(String s);

    List<Integer> parseNumbers(String input);
}
