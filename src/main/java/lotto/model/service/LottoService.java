package lotto.model.service;

import java.util.ArrayList;
import java.util.List;
import lotto.model.domain.Lotto;
import java.util.Objects;
import lotto.model.support.InputParser;
import lotto.model.support.LottoNumberGenerator;

public class LottoService {
    private static final int LOTTO_PRICE = 1000;

    private final LottoNumberGenerator lottoNumberGenerator;
    private final InputParser inputParser;

    public LottoService(LottoNumberGenerator lottoNumberGenerator, InputParser inputParser) {
        this.lottoNumberGenerator = lottoNumberGenerator;
        this.inputParser = Objects.requireNonNull(inputParser, "inputParser must not be null");
    }

    public void validateMoneyUnit(int money) throws IllegalArgumentException {
        if (money <= 0 || money % 1000 != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 1,000원 단위의 양수여야 합니다.");
        }
    }

    public List<Lotto> createLotto(int money) {
        if (money < LOTTO_PRICE) {
            throw new IllegalArgumentException("[ERROR] 최소 구입 금액은 1,000원입니다.");
        }
        if (money % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 1,000원 단위여야 합니다.");
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
        validateMoneyUnit(money);
        return money;
    }

    public Lotto toValidLotto(String csvNumbers) {
        List<Integer> numbers = inputParser.parseCsvInts(csvNumbers);
        return new Lotto(numbers);
    }

    public int toValidBonus(Lotto winningMainLotto, String bonusInput) {
        int bonus = inputParser.parseIntStrict(bonusInput);
        validateBonusNumber(winningMainLotto, bonus);
        return bonus;
    }

    private void validateBonusNumber(Lotto winningMainLotto, int bonusNumber) {
        if (bonusNumber < 1 || bonusNumber > 45) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
        if (winningMainLotto.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }
}
