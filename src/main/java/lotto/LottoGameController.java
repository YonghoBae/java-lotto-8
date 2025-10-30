package lotto;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LottoGameController {

    private InputView inputView;
    private OutputView outputView;
    private LottoStore lottoStore;

    LottoGameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.lottoStore = new LottoStore();
    }

    public void run() {
        String inputMoney = inputView.inputPurchaseAmount();

        int money = parseMoney(inputMoney);

        List<Lotto> lottos = lottoStore.buyLottos(money);

        outputView.printLottos(lottos);

        String inputWinningNumbers = inputView.inputWinningNumbers();

        List<Integer> winningNumbers = parseWinningNumbers(inputWinningNumbers);

        outputView.printWinningNumbers(winningNumbers);
    }

    private int parseMoney(String inputMoney) throws IllegalArgumentException {
        try {
            return Integer.parseInt(inputMoney);
        } catch (NumberFormatException | NullPointerException e) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 숫자여야 합니다.");
        }
    }

    private List<Integer> parseWinningNumbers(String inputWinningNumbers) {
        try {
            return Arrays.stream(inputWinningNumbers.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();

        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("당첨 번호는 숫자만 입력해야 합니다.");
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("당첨 번호가 입력되지 않았습니다.");
        }
    }
}
