package lotto.service;

import java.util.List;
import lotto.Lotto;
import lotto.LottoStore;

public class LottoService {

    private final LottoStore lottoStore;

    public LottoService(LottoStore lottoStore) {
        this.lottoStore = lottoStore;
    }

    public List<Lotto> createLotto(int money) {
        return lottoStore.buyLottos(money);
    }
}
