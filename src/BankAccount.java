public class BankAccount {
    private Balance balance;
    private final String name;
    private long limit;
    private boolean active;
    private static final String promo = "Welcome";
    private static final Balance promoMoney = Balance.of(100, Currency.RUB);


    public BankAccount(String name, Currency currency, long limit, String promo) {
        if (name == null || name.isBlank()){
            throw new IllegalArgumentException("Имя владельца аккаунта не может быть пустым");
        }
        if (limit <= 0){
            throw new IllegalArgumentException("Лимит должен быть положительным");
        }

        this.balance = new Balance(0, currency);
        if (BankAccount.promo.equals(promo)){
            this.balance = this.balance.add(promoMoney.getKopecks());
        }
        this.name = name;
        this.limit = limit;
        this.active = true;
    }

    public void deposit(Balance amt){
        if (amt == null){
            throw new IllegalArgumentException("сумма пополнения не может быть null");
        }
        if (!active) {
            throw new IllegalArgumentException("Счет владельца " + this.name + " заблокирован");
        }
        if (this.balance.getCurrency() !=  amt.getCurrency()){
            throw new IllegalArgumentException("Нельзя пополнить на разные валюты");
        }
        this.balance = this.balance.add(amt.getKopecks());
    }
    public  void withdraw(Balance amt){
        if (amt == null){
            throw new IllegalArgumentException("сумма снятия не должна быть null");
        }
        if (!active) {
            throw new IllegalArgumentException("Счет владельца " + this.name + " заблокирован");
        }
        if (this.balance.getCurrency() !=  amt.getCurrency()){
            throw new IllegalArgumentException("Нельзя снять на разные валюты");
        }
        this.balance = this.balance.subtract(amt.getKopecks());
    }
    public void send(BankAccount bk, Balance amt){
        if (bk == null){
            throw new IllegalArgumentException("Счёт получателя не может быть null");
        }
        if (amt == null){
            throw new IllegalArgumentException("Сумма перевода не может быть null");
        }
        if (this == bk){
            throw new IllegalArgumentException("Нельзя переводить самому себе");
        }
        if (!this.active || !bk.active){
            throw new IllegalArgumentException("Перевод не удался");
        }
        if (amt.getKopecks() > this.limit){
            throw new IllegalArgumentException("Превышен лимит операции");
        }
        if (this.balance.getCurrency() !=  amt.getCurrency()){
            throw new IllegalArgumentException("Нельзя отправить на разные валюты");
        }
        if (this.balance.getCurrency() != bk.balance.getCurrency()){
            throw new IllegalArgumentException("Валюта отправителя отличается от получателя");
        }
        this.balance = this.balance.subtract(amt.getKopecks());
        bk.balance = bk.balance.add(amt.getKopecks());
    }
    public Balance getBalance() {
        return this.balance;
    }
    public String getName() {
        return this.name;
    }
    public long getLimit() {
        return this.limit;
    }
    public boolean getStatus() {
        return this.active;
    }
    public void unblock(){
        this.active = true;
    }
    public void block() {
        this.active = false;
    }

    @Override
    public String toString(){
        return "Банковский аккаунт - " + this.name + " на его счету " + this.balance;
    }
}
