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
        return loop(() -> lottoService.toValidMoney(inputView.readMoney()));
    }

    private Lotto getValidWinningMainLotto() {
        return loop(() -> lottoService.toValidLotto(inputView.readWinningNumbers()));
    }

    private int getValidBonusNumber(Lotto winningMainLotto) {
        return loop(() -> lottoService.toValidBonus(winningMainLotto, inputView.readBonus()));
    }
}
