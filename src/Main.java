public class Main {
    final static long limitWithOneTransaction = Balance.of(50000, Currency.RUB).getKopecks();
    public static void main(String[] args) {
        BankAccount user1 = new BankAccount("User-1", Currency.RUB, limitWithOneTransaction, "");
        BankAccount user2 = new BankAccount("User-2", Currency.RUB, limitWithOneTransaction, "Welcome");

        System.out.println(user1);
        System.out.println(user2);

        System.out.println("Проводим операции пополнения");

        user1.deposit(Balance.of(80000, Currency.RUB));
        user2.deposit(Balance.of(50000, Currency.RUB));

        System.out.println(user1);
        System.out.println(user2);

        System.out.println("Проводим операции перевода");
        user1.send(user2, Balance.of(50000, Currency.RUB));
        System.out.println(user1);
        System.out.println(user2);



        System.out.println("Корнер кейсы");
        try {
            user1.withdraw(Balance.of(1_000_000, Currency.RUB));
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        try {
            user1.send(user1, Balance.of(10, Currency.RUB));
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        try {
            user1.send(null, Balance.of(10, Currency.RUB));
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        try {
            user1.block();
            user1.withdraw(Balance.of(100, Currency.RUB));
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
            user1.unblock();
        }

        try {
            user1.send(user2, Balance.of(100, Currency.USD));
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }

        System.out.println("И много других корнер кейсов все подробности написаны в README.md");
    }
}
