package lotto;

import lotto.controller.LottoGameController;
import lotto.service.LottoService;
import lotto.service.WinningService;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        InputAdapter inputAdapter = new InputAdapter();
        OutputView outputView = new OutputView();
        LottoStore lottoStore = new LottoStore();
        LottoService lottoService = new LottoService(lottoStore);
        WinningService winningService = new WinningService();

        LottoGameController lottoGameController = new LottoGameController(inputAdapter, outputView, lottoService, winningService);

        lottoGameController.run();
    }
}
