package lotto.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lotto.model.domain.Lotto;
import lotto.model.support.InputParser;
import lotto.model.support.LottoValidator;
import lotto.model.support.LottoNumberGenerator;

public class LottoService {
    private static final int LOTTO_PRICE = 1000;

    private final LottoNumberGenerator lottoNumberGenerator;
    private final InputParser inputParser;
    private final LottoValidator lottoValidator;

    public LottoService(LottoNumberGenerator lottoNumberGenerator, InputParser inputParser, LottoValidator lottoValidator) {
        this.lottoNumberGenerator = lottoNumberGenerator;
        this.inputParser = Objects.requireNonNull(inputParser, "inputParser must not be null");
        this.lottoValidator = Objects.requireNonNull(lottoValidator, "lottoValidator must not be null");
    }

    public List<Lotto> createLotto(int money) {
        lottoValidator.validateMoney(money);
        if (money < LOTTO_PRICE) {
            throw new IllegalArgumentException("[ERROR] 최소 구입 금액은 1,000원입니다.");
        }

        int count = money / LOTTO_PRICE;
        List<Lotto> lottos = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            lottos.add(lottoNumberGenerator.create());
        }
        return lottos;
    }

    public int toValidMoney(String inputMoney) {
        int money = inputParser.parseIntStrict(inputMoney);
        lottoValidator.validateMoney(money);
        return money;
    }

    public Lotto toValidLotto(String csvNumbers) {
        List<Integer> numbers = inputParser.parseCsvInts(csvNumbers);
        lottoValidator.validateNumbers(numbers);
        return new Lotto(numbers);
    }

    public int toValidBonus(Lotto winningMainLotto, String bonusInput) {
        int bonus = inputParser.parseIntStrict(bonusInput);
        lottoValidator.validateBonus(winningMainLotto, bonus);
        return bonus;
    }
}
