package lotto.model.domain;

public enum WinningCriteria {
    FIRST(6, false, 2_000_000_000L),
    SECOND(5, true, 30_000_000L),
    THIRD(5, false, 1_500_000L),
    FOURTH(4, false, 50_000L),
    FIFTH(3, false, 5_000L),
    MISS(0, false, 0L);

    private final int matchCount;
    private final boolean bonusRequired;
    private final long prizeMoney;

    WinningCriteria(int matchCount, boolean bonusRequired, long prizeMoney) {
        if (matchCount < 0) {
            throw new IllegalArgumentException("[ERROR] 일치 개수는 음수일 수 없습니다.");
        }
        if (prizeMoney < 0) {
            throw new IllegalArgumentException("[ERROR] 상금은 음수일 수 없습니다.");
        }
        this.matchCount = matchCount;
        this.bonusRequired = bonusRequired;
        this.prizeMoney = prizeMoney;
    }

    public static WinningCriteria valueOf(int matchCount, boolean matchBonus) {
        if (matchCount == 5 && matchBonus) {
            return SECOND;
        }

        for (WinningCriteria criteria : values()) {
            if (criteria.matchCount == matchCount && !criteria.bonusRequired) {
                return criteria;
            }
        }

        return MISS;
    }

    public long prizeMoney() {
        return prizeMoney;
    }
}
