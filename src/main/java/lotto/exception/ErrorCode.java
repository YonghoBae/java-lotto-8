package lotto.exception;

public enum ErrorCode {
    INVALID_NUMBER_FORMAT("[ERROR] 숫자여야 합니다.", ErrorType.USER_INPUT),
    INVALID_WINNING_NUMBER_FORMAT("[ERROR] 당첨 번호는 숫자만 입력해야 합니다.", ErrorType.USER_INPUT),
    EMPTY_VALUE("[ERROR] 값이 비어 있습니다.", ErrorType.USER_INPUT),
    INVALID_PURCHASE_UNIT("[ERROR] 구입 금액은 1,000원 단위의 양수여야 합니다.", ErrorType.USER_INPUT),
    INVALID_LOTTO_NUMBER_COUNT("[ERROR] 로또 번호는 6개여야 합니다.", ErrorType.USER_INPUT),
    INVALID_LOTTO_NUMBER_RANGE("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.", ErrorType.USER_INPUT),
    DUPLICATE_LOTTO_NUMBER("[ERROR] 로또 번호는 중복될 수 없습니다.", ErrorType.USER_INPUT),
    INVALID_BONUS_RANGE("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다.", ErrorType.USER_INPUT),
    DUPLICATE_BONUS_NUMBER("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.", ErrorType.USER_INPUT),
    INVALID_PURCHASE_MINIMUM("[ERROR] 최소 구입 금액은 1,000원입니다.", ErrorType.USER_INPUT),
    INVALID_BONUS_RANGE_SHORT("[ERROR] 보너스 번호는 1~45 사이여야 합니다.", ErrorType.USER_INPUT),
    NEGATIVE_MATCH_COUNT("[ERROR] 일치 개수는 음수일 수 없습니다.", ErrorType.SYSTEM_STATE),
    NEGATIVE_PRIZE("[ERROR] 상금은 음수일 수 없습니다.", ErrorType.SYSTEM_STATE),
    MISSING_NUMBER_GENERATOR("[ERROR] 로또 번호 생성기가 초기화되지 않았습니다.", ErrorType.SYSTEM_STATE),
    MISSING_INPUT_PARSER("[ERROR] 입력 파서가 초기화되지 않았습니다.", ErrorType.SYSTEM_STATE),
    MISSING_VALIDATOR("[ERROR] 로또 검증기가 초기화되지 않았습니다.", ErrorType.SYSTEM_STATE),
    LOTTO_CREATION_FAILURE("[ERROR] 로또 생성에 실패했습니다.", ErrorType.SYSTEM_STATE),
    MISSING_WINNING_MAIN_LOTTO("[ERROR] 당첨 번호 정보가 없습니다.", ErrorType.SYSTEM_STATE),
    MISSING_PROFIT_CALCULATOR("[ERROR] 수익률 계산기가 초기화되지 않았습니다.", ErrorType.SYSTEM_STATE),
    MISSING_WINNING_NUMBERS("[ERROR] 당첨 번호가 초기화되지 않았습니다.", ErrorType.SYSTEM_STATE),
    STATISTICS_CALCULATION_FAILURE("[ERROR] 당첨 결과를 계산할 수 없습니다.", ErrorType.SYSTEM_STATE),
    NULL_LOTTO_IN_LIST("[ERROR] 로또 목록에 null 항목이 포함되어 있습니다.", ErrorType.SYSTEM_STATE),
    MISSING_STATISTICS("[ERROR] 통계 정보가 초기화되지 않았습니다.", ErrorType.SYSTEM_STATE),
    INVALID_PURCHASE_TOTAL("[ERROR] 구입 금액이 올바르게 계산되지 않았습니다.", ErrorType.SYSTEM_STATE),
    INVALID_STATISTICS_CONTENT("[ERROR] 통계 정보가 올바르지 않습니다.", ErrorType.SYSTEM_STATE),
    EMPTY_STATISTICS("[ERROR] 계산할 통계 정보가 없습니다.", ErrorType.SYSTEM_STATE),
    NULL_STATISTICS_VALUE("[ERROR] 통계 정보에 null 값이 포함되어 있습니다.", ErrorType.SYSTEM_STATE),
    INVALID_PURCHASE_FOR_PROFIT("[ERROR] 수익률 계산을 위한 구입 금액이 올바르지 않습니다.", ErrorType.SYSTEM_STATE);

    private final String message;
    private final ErrorType type;

    ErrorCode(String message, ErrorType type) {
        this.message = message;
        this.type = type;
    }

    public String message() {
        return message;
    }

    public boolean isUserError() {
        return type == ErrorType.USER_INPUT;
    }

    public RuntimeException toException() {
        if (type == ErrorType.USER_INPUT) {
            return new IllegalArgumentException(message);
        }
        return new IllegalStateException(message);
    }

    public IllegalArgumentException toIllegalArgumentException() {
        if (type != ErrorType.USER_INPUT) {
            throw new IllegalStateException("ErrorCode " + name() + " is not a user input error.");
        }
        return new IllegalArgumentException(message);
    }

    public IllegalStateException toIllegalStateException() {
        if (type != ErrorType.SYSTEM_STATE) {
            throw new IllegalStateException("ErrorCode " + name() + " is not a system state error.");
        }
        return new IllegalStateException(message);
    }

    private enum ErrorType {
        USER_INPUT,
        SYSTEM_STATE
    }
}
