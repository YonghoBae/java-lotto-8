package lotto.model.support.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lotto.model.support.InputParser;

public class DefaultInputParser implements InputParser {

    @Override
    public int parseIntStrict(String s) {
        try {
            return Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자여야 합니다.");
        }
    }

    @Override
    public List<Integer> parseCsvInts(String csv) {
        try {
            return Stream.of(csv.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 숫자만 입력해야 합니다.");
        }
    }
}
