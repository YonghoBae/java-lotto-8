package lotto;

import lotto.controller.LottoGameController;
import lotto.service.LottoService;
import lotto.service.WinningService;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        LottoNumberGenerator lottoNumberGenerator = new RandomLottoGenerator();
        LottoService lottoService = new LottoService(lottoNumberGenerator);
        WinningService winningService = new WinningService();

        LottoGameController lottoGameController = new LottoGameController(inputView, outputView, lottoService, winningService);

        lottoGameController.run();
    }
}
