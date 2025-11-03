package lotto.view;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import lotto.common.LottoConstants;
import lotto.model.domain.Lotto;
import lotto.model.domain.WinningCriteria;

public class OutputView {
    private static final String UNKNOWN_ERROR_MESSAGE = "[ERROR] 알 수 없는 오류가 발생했습니다.";
    private static final String PURCHASE_COUNT_FORMAT = "%d개를 구매했습니다.";
    private static final String WINNING_NUMBERS_HEADER = "\n당첨 통계\n---";
    private static final String MATCH_THREE_FORMAT = "3개 일치 (%,d원) - %d개\n";
    private static final String MATCH_FOUR_FORMAT = "4개 일치 (%,d원) - %d개\n";
    private static final String MATCH_FIVE_FORMAT = "5개 일치 (%,d원) - %d개\n";
    private static final String MATCH_FIVE_WITH_BONUS_FORMAT = "5개 일치, 보너스 볼 일치 (%,d원) - %d개\n";
    private static final String MATCH_SIX_FORMAT = "6개 일치 (%,d원) - %d개\n";
    private static final String PROFIT_RATE_FORMAT = "총 수익률은 %s%%입니다.\n";
    private static final String RATE_PATTERN = "#,##0.0";
    private static final String ERROR_PREFIX_WITH_SPACE = LottoConstants.ERROR_PREFIX + " ";

    public void printError(String error) {
        if (error == null || error.isBlank()) {
            System.out.println(UNKNOWN_ERROR_MESSAGE);
            return;
        }

        if (error.startsWith(LottoConstants.ERROR_PREFIX)) {
            System.out.println(error);
            return;
        }

        System.out.println(ERROR_PREFIX_WITH_SPACE + error);
    }

    public void printLottos(List<Lotto> lottos) {
        System.out.println(String.format(PURCHASE_COUNT_FORMAT, lottos.size()));

        for (Lotto lotto : lottos) {
            System.out.println(lotto);
        }
    }

    public void printStatistics(Map<WinningCriteria, Integer> stats) {
        System.out.println(WINNING_NUMBERS_HEADER);

        System.out.printf(MATCH_THREE_FORMAT,
                WinningCriteria.FIFTH.prizeMoney(), stats.get(WinningCriteria.FIFTH));
        System.out.printf(MATCH_FOUR_FORMAT,
                WinningCriteria.FOURTH.prizeMoney(), stats.get(WinningCriteria.FOURTH));
        System.out.printf(MATCH_FIVE_FORMAT,
                WinningCriteria.THIRD.prizeMoney(), stats.get(WinningCriteria.THIRD));
        System.out.printf(MATCH_FIVE_WITH_BONUS_FORMAT,
                WinningCriteria.SECOND.prizeMoney(), stats.get(WinningCriteria.SECOND));
        System.out.printf(MATCH_SIX_FORMAT,
                WinningCriteria.FIRST.prizeMoney(), stats.get(WinningCriteria.FIRST));
    }

    public void printProfitRate(double rate) {
        DecimalFormat df = new DecimalFormat(RATE_PATTERN);
        System.out.printf(PROFIT_RATE_FORMAT, df.format(rate));
    }
}
