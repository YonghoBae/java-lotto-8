package lotto;

import lotto.controller.LottoGameController;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        LottoStore lottoStore = new LottoStore();

        LottoGameController lottoGameController = new LottoGameController(inputView, outputView,lottoStore);

        lottoGameController.run();
    }
}
