package lotto;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LottoGameController {

    private final InputView inputView;
    private final OutputView outputView;
    private final LottoStore lottoStore;

    LottoGameController(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.lottoStore = new LottoStore();
    }

    public void run() {
        int money = getValidPurchaseAmount();

        List<Lotto> lottos = lottoStore.buyLottos(money);
        outputView.printLottos(lottos);

        Lotto winningMainLotto = getValidWinningMainLotto();

        int bonusNumber = getValidBonusNumber(winningMainLotto);

        WinningLotto winningLotto = new WinningLotto(winningMainLotto, bonusNumber);

        Map<WinningCriteria, Integer> statistics = calculateStatistics(lottos, winningLotto);

        double profitRate = calculateProfitRate(statistics, money);
    }

    private int getValidPurchaseAmount() {
        while (true) {
            try {
                String inputMoney = inputView.inputPurchaseAmount();
                int money = parseMoney(inputMoney);
                validateMoneyUnit(money);
                return money;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int parseMoney(String inputMoney) throws IllegalArgumentException {
        try {
            return Integer.parseInt(inputMoney);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 숫자여야 합니다.");
        }
    }

    private void validateMoneyUnit(int money) throws IllegalArgumentException {
        if (money <= 0 || money % 1000 != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 1,000원 단위의 양수여야 합니다.");
        }
    }

    private Lotto getValidWinningMainLotto() {
        while (true) {
            try {
                String inputWinningNumbers = inputView.inputWinningNumbers();
                List<Integer> numbers = parseWinningNumbers(inputWinningNumbers);
                return new Lotto(numbers);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private List<Integer> parseWinningNumbers(String inputWinningNumbers) {
        try {
            return Arrays.stream(inputWinningNumbers.split(","))
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .toList();
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 당첨 번호는 숫자만 입력해야 합니다.");
        }
    }

    private int getValidBonusNumber(Lotto winningMainLotto) {
        while (true) {
            try {
                String inputBonusNumber = inputView.inputBonusNumber();
                int bonusNumber = parseBonusNumber(inputBonusNumber);
                validateBonusNumber(winningMainLotto, bonusNumber);
                return bonusNumber;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private int parseBonusNumber(String inputBonusNumber) {
        try {
            return Integer.parseInt(inputBonusNumber);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 숫자만 입력해야 합니다.");
        }
    }

    private void validateBonusNumber(Lotto winningMainLotto, int bonusNumber) {
        if (bonusNumber < 1 || bonusNumber > 45) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
        if (winningMainLotto.contains(bonusNumber)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    private Map<WinningCriteria, Integer> calculateStatistics(List<Lotto> lottos, WinningLotto winningLotto) {
        Map<WinningCriteria, Integer> stats = new EnumMap<>(WinningCriteria.class);
        for (WinningCriteria criteria : WinningCriteria.values()) {
            stats.put(criteria, 0);
        }

        for (Lotto lotto : lottos) {
            WinningCriteria rank = lotto.calculateRank(winningLotto);
            stats.put(rank, stats.get(rank) + 1);
        }
        return stats;
    }

    private double calculateProfitRate(Map<WinningCriteria, Integer> stats, int purchaseMoney) {
        long totalPrize = 0;
        for (Map.Entry<WinningCriteria, Integer> entry : stats.entrySet()) {
            totalPrize += entry.getKey().getPrizeMoney() * entry.getValue();
        }

        if (purchaseMoney == 0) {
            return 0.0;
        }

        return ((double) totalPrize / purchaseMoney) * 100.0;
    }
}
