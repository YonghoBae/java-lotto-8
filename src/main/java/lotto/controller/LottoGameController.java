package lotto.controller;

import java.util.List;
import java.util.Map;
import lotto.InputView;
import lotto.Lotto;
import lotto.OutputView;
import lotto.WinningCriteria;
import lotto.WinningNumbers;
import lotto.service.LottoService;
import lotto.service.WinningService;

public class LottoGameController {

    private final InputView inputView;
    private final OutputView outputView;
    private final LottoService lottoService;
    private final WinningService winningService;

    public LottoGameController(InputView inputView, OutputView outputView, LottoService lottoService,
                               WinningService winningService) {
        this.inputView = inputView;
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
                String inputMoney = inputView.inputPurchaseAmount();
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
                String inputWinningNumbers = inputView.inputWinningNumbers();
                List<Integer> numbers = winningService.parseWinningNumbers(inputWinningNumbers);
                return new Lotto(numbers);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int getValidBonusNumber(Lotto winningMainLotto) {
        while (true) {
            try {
                String inputBonusNumber = inputView.inputBonusNumber();
                int bonusNumber = winningService.parseBonusNumber(inputBonusNumber);
                winningService.validateBonusNumber(winningMainLotto, bonusNumber);
                return bonusNumber;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }


}
