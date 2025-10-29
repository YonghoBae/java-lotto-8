package lotto;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.List;

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
    }

    private int parseMoney(String inputMoney) throws IllegalArgumentException{
        try{
            return Integer.parseInt(inputMoney);
        }catch (NumberFormatException | NullPointerException e){
            throw new IllegalArgumentException("[ERROR] 구입 금액은 숫자여야 합니다.");
        }
    }
}
