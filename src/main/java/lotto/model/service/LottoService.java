package lotto.model.service;

import java.util.ArrayList;
import java.util.List;
import lotto.exception.ErrorCode;
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
        if (lottoNumberGenerator == null) {
            throw ErrorCode.MISSING_NUMBER_GENERATOR.toIllegalStateException();
        }
        if (inputParser == null) {
            throw ErrorCode.MISSING_INPUT_PARSER.toIllegalStateException();
        }
        if (lottoValidator == null) {
            throw ErrorCode.MISSING_VALIDATOR.toIllegalStateException();
        }
        this.lottoNumberGenerator = lottoNumberGenerator;
        this.inputParser = inputParser;
        this.lottoValidator = lottoValidator;
    }

    public List<Lotto> createLotto(int money) {
        lottoValidator.validateMoney(money);
        if (money < LOTTO_PRICE) {
            throw ErrorCode.INVALID_PURCHASE_MINIMUM.toIllegalArgumentException();
        }

        int count = money / LOTTO_PRICE;
        List<Lotto> lottos = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Lotto lotto = lottoNumberGenerator.create();
            if (lotto == null) {
                throw ErrorCode.LOTTO_CREATION_FAILURE.toIllegalStateException();
            }
            lottos.add(lotto);
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
        if (winningMainLotto == null) {
            throw ErrorCode.MISSING_WINNING_MAIN_LOTTO.toIllegalStateException();
        }
        int bonus = inputParser.parseIntStrict(bonusInput);
        lottoValidator.validateBonus(winningMainLotto, bonus);
        return bonus;
    }
}
