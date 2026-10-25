import java.math.BigDecimal;
import java.math.RoundingMode;

public final class Balance {
    private final long kopecks;
    private final Currency currency;

    public Balance(){
        this.currency = Currency.RUB;
        this.kopecks = 0;
    }
    public Balance(long kopecks){
        if (kopecks < 0){
            throw new IllegalArgumentException("Баланс не может быть отрицательным");
        }
        this.currency = Currency.RUB;
        this.kopecks = kopecks;
    }
    public Balance(long kopecks, Currency currency){
        if (kopecks < 0){
            throw new IllegalArgumentException("Баланс не может быть отрицательным");
        }
        if (currency == null){
            throw new NullPointerException("Введите валюту предложенную банком");
        }
        this.currency = currency;
        this.kopecks = kopecks;
    }
    public Balance add(long kopecks){
        if (kopecks <= 0) {
            throw new IllegalArgumentException("Сумма пополнения должна быть положительной");
        }
        return new Balance(Math.addExact(this.kopecks, kopecks), this.currency);
    }
    public Balance subtract(long kopecks){
        if (kopecks <= 0) {
            throw new IllegalArgumentException("Сумма списания должна быть положительной");
        }
        if (this.kopecks < kopecks) {
            throw new IllegalArgumentException("Недостаточно средств на счете");
        }
        return new Balance(Math.subtractExact(this.kopecks, kopecks), this.currency);
    }
    public Balance convertTo(Currency currency){
        if (currency == null) {
            throw new NullPointerException("Введите валюту предложенную банком");
        }
        return new Balance(this.kopecks, currency);
    }

    public static Balance of(long one, Currency currency){
        long kopecks = Math.multiplyExact(one, currency.getPriceInRublesKopecks());
        return new Balance(kopecks, currency);
    }


    public long getKopecks(){
        return this.kopecks;
    }
    public Currency getCurrency(){
        return this.currency;
    }
    public double getAmt(){
        return (double) this.kopecks /  this.currency.getPriceInRublesKopecks();
    }


    @Override
    public String toString() {
        BigDecimal amt = BigDecimal.valueOf(this.kopecks)
                .divide(BigDecimal.valueOf(currency.getPriceInRublesKopecks()), 10, RoundingMode.HALF_UP)
                .stripTrailingZeros();
        if (amt.scale() < 2) {
            amt = amt.setScale(2, RoundingMode.HALF_UP);
        }
        return String.format("%s%s (%s)", amt, this.currency.getSymbol(), this.currency.getCode());
    }


}
