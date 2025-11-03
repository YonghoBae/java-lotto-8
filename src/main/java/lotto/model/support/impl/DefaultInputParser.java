package lotto.model.support.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lotto.exception.ErrorCode;
import lotto.model.support.InputParser;

public class DefaultInputParser implements InputParser {

    @Override
    public int parseIntStrict(String s) {
        try {
            String trimmed = requireNonBlank(s);
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException e) {
            throw ErrorCode.INVALID_NUMBER_FORMAT.toIllegalArgumentException();
        }
    }

    @Override
    public List<Integer> parseNumbers(String input) {
        try {
            String sanitized = requireNonBlank(input);
            return Stream.of(sanitized.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw ErrorCode.INVALID_WINNING_NUMBER_FORMAT.toIllegalArgumentException();
        }
    }

    private String requireNonBlank(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw ErrorCode.EMPTY_VALUE.toIllegalArgumentException();
        }
        return value.trim();
    }
}
