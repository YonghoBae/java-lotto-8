package lotto;

import lotto.controller.LottoGameController;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        InputAdapter inputAdapter = new InputAdapter();
        OutputView outputView = new OutputView();
        LottoStore lottoStore = new LottoStore();

        LottoGameController lottoGameController = new LottoGameController(inputAdapter, outputView,lottoStore);

        lottoGameController.run();
    }
}
