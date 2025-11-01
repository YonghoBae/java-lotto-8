package lotto.controller;

import java.util.List;
import java.util.Map;
import lotto.model.domain.Lotto;
import lotto.model.domain.WinningCriteria;
import lotto.model.domain.WinningNumbers;
import lotto.model.service.LottoService;
import lotto.model.service.WinningService;
import lotto.view.InputView;
import lotto.view.OutputView;

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
                return lottoService.toValidMoney(inputMoney);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Lotto getValidWinningMainLotto() {
        while (true) {
            try {
                String inputWinningNumbers = inputView.inputWinningNumbers();
                return lottoService.toValidLotto(inputWinningNumbers);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int getValidBonusNumber(Lotto winningMainLotto) {
        while (true) {
            try {
                String inputBonusNumber = inputView.inputBonusNumber();
                return lottoService.toValidBonus(winningMainLotto, inputBonusNumber);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }


}
