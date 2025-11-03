package lotto.controller;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import lotto.model.domain.Lotto;
import lotto.model.domain.WinningCriteria;
import lotto.model.domain.WinningNumbers;
import lotto.model.service.LottoService;
import lotto.model.service.WinningService;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoController {

    private final InputView inputView;
    private final OutputView outputView;
    private final LottoService lottoService;
    private final WinningService winningService;

    public LottoController(InputView inputView, OutputView outputView, LottoService lottoService,
                           WinningService winningService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.lottoService = lottoService;
        this.winningService = winningService;
    }

    public void run() {
        try {
            int money = getValidPurchaseAmount();
            List<Lotto> lottos = purchaseLottos(money);
            WinningNumbers winningNumbers = collectWinningNumbers();
            presentResult(lottos, winningNumbers, money);
        } catch (IllegalStateException e) {
            outputView.printError(e.getMessage());
        }
    }

    private <T> T loop(Supplier<T> step) {
        while (true) {
            try {
                return step.get();
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private int getValidPurchaseAmount() {
        return loop(() -> {
            outputView.printPurchaseAmountPrompt();
            return lottoService.toValidMoney(inputView.readLine());
        });
    }

    private Lotto getValidWinningMainLotto() {
        return loop(() -> {
            outputView.printWinningNumbersPrompt();
            return lottoService.toValidLotto(inputView.readLine());
        });
    }

    private int getValidBonusNumber(Lotto winningMainLotto) {
        return loop(() -> {
            outputView.printBonusNumberPrompt();
            return lottoService.toValidBonus(winningMainLotto, inputView.readLine());
        });
    }

    private List<Lotto> purchaseLottos(int money) {
        List<Lotto> lottos = lottoService.createLotto(money);
        outputView.printLottos(lottos);
        return lottos;
    }

    private WinningNumbers collectWinningNumbers() {
        Lotto winningMainLotto = getValidWinningMainLotto();
        int bonusNumber = getValidBonusNumber(winningMainLotto);
        return winningService.createWinningNumbers(winningMainLotto, bonusNumber);
    }

    private void presentResult(List<Lotto> lottos, WinningNumbers winningNumbers, int money) {
        Map<WinningCriteria, Integer> statistics = winningService.calculateStatistics(lottos, winningNumbers);
        double profitRate = winningService.calculateProfitRate(statistics, money);
        outputView.printStatistics(statistics);
        outputView.printProfitRate(profitRate);
    }
}
