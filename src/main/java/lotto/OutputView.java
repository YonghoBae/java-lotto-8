package lotto;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class OutputView {
    public void printError(String error) {
        System.out.println(error);
    }

    public void printLottos(List<Lotto> lottos) {
        System.out.println(lottos.size() + "개를 구매했습니다.");

        for (Lotto lotto : lottos) {
            System.out.println(lotto);
        }
    }

    public void printStatistics(Map<WinningCriteria, Integer> stats) {
        System.out.println("\n당첨 통계\n---");

        System.out.printf("3개 일치 (%,d원) - %d개\n",
                WinningCriteria.FIFTH.getPrizeMoney(), stats.get(WinningCriteria.FIFTH));
        System.out.printf("4개 일치 (%,d원) - %d개\n",
                WinningCriteria.FOURTH.getPrizeMoney(), stats.get(WinningCriteria.FOURTH));
        System.out.printf("5개 일치 (%,d원) - %d개\n",
                WinningCriteria.THIRD.getPrizeMoney(), stats.get(WinningCriteria.THIRD));
        System.out.printf("5개 일치, 보너스 볼 일치 (%,d원) - %d개\n",
                WinningCriteria.SECOND.getPrizeMoney(), stats.get(WinningCriteria.SECOND));
        System.out.printf("6개 일치 (%,d원) - %d개\n",
                WinningCriteria.FIRST.getPrizeMoney(), stats.get(WinningCriteria.FIRST));
    }

    public void printProfitRate(double rate) {
        DecimalFormat df = new DecimalFormat("#,##0.0");
        System.out.printf("총 수익률은 %s%%입니다.\n", df.format(rate));
    }
}
