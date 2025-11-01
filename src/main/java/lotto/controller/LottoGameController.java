package lotto.controller;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import lotto.InputAdapter;
import lotto.Lotto;
import lotto.LottoStore;
import lotto.OutputView;
import lotto.WinningCriteria;
import lotto.WinningNumbers;
import lotto.service.LottoService;
import lotto.service.WinningService;

public class LottoGameController {

    private final InputAdapter inputAdapter;
    private final OutputView outputView;
    private final LottoService lottoService;
    private final WinningService winningService;

    public LottoGameController(InputAdapter inputAdapter, OutputView outputView, LottoService lottoService,
                               WinningService winningService) {
        this.inputAdapter = inputAdapter;
        this.outputView = outputView;
        this.lottoService = lottoService;
        this.winningService = winningService;
    }

    public void run() {
        int money = getValidPurchaseAmount();

        List<Lotto> lottos = lottoService.createLotto(money);

        outputView.printLottos(lottos);

        Lotto winningMainLotto = getValidWinningMainLotto();

        int bonusNumber = getValidBonusNumber(winningMainLotto);

        WinningNumbers winningNumbers = winningService.createWinningNumbers(winningMainLotto, bonusNumber);

        Map<WinningCriteria, Integer> statistics = winningService.calculateStatistics(lottos, winningNumbers);

        double profitRate = winningService.calculateProfitRate(statistics, money);

        outputView.printStatistics(statistics);
        outputView.printProfitRate(profitRate);
    }

    private int getValidPurchaseAmount() {
        while (true) {
            try {
                String inputMoney = inputAdapter.inputPurchaseAmount();
                int money = lottoService.parseMoney(inputMoney);
                lottoService.validateMoneyUnit(money);
                return money;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Lotto getValidWinningMainLotto() {
        while (true) {
            try {
                String inputWinningNumbers = inputAdapter.inputWinningNumbers();
                List<Integer> numbers = parseWinningNumbers(inputWinningNumbers);
                return new Lotto(numbers);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private List<Integer> parseWinningNumbers(String inputWinningNumbers) {
        try {
            return Arrays.stream(inputWinningNumbers.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 숫자만 입력해야 합니다.");
        }
    }

    private int getValidBonusNumber(Lotto winningMainLotto) {
        while (true) {
            try {
                String inputBonusNumber = inputAdapter.inputBonusNumber();
                int bonusNumber = parseBonusNumber(inputBonusNumber);
                validateBonusNumber(winningMainLotto, bonusNumber);
                return bonusNumber;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int parseBonusNumber(String inputBonusNumber) {
        try {
            return Integer.parseInt(inputBonusNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 숫자만 입력해야 합니다.");
        }
    }

    private void validateBonusNumber(Lotto winningMainLotto, int bonusNumber) {
        if (bonusNumber < 1 || bonusNumber > 45) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
        if (winningMainLotto.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }
}
