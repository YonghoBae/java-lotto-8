package lotto;

import java.util.List;

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

    public void printWinningNumbers(List<Integer> winningNumbers) {
        System.out.println(winningNumbers);
    }

    public void printBonusNumber(int bonusNumber) {
        System.out.println(bonusNumber);
    }
}
