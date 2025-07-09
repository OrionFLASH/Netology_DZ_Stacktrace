import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PasswordChecker checker = new PasswordChecker();

        try {
            // Ввод минимальной длины пароля
            System.out.print("Введите мин. длину пароля: ");
            int minLength = Integer.parseInt(scanner.nextLine().trim());
            checker.setMinLength(minLength);

            // Ввод максимального количества повторов подряд
            System.out.print("Введите макс. допустимое количество повторений символа подряд: ");
            int maxRepeats = Integer.parseInt(scanner.nextLine().trim());
            checker.setMaxRepeats(maxRepeats);
        } catch (IllegalArgumentException ex) {
            System.out.println("Ошибка: " + ex.getMessage());
            System.out.println("Программа завершена");
            return;
        }

        // Проверка паролей в цикле
        while (true) {
            System.out.print("Введите пароль или end: ");
            String password = scanner.nextLine();
            if (password.equalsIgnoreCase("end")) {
                System.out.println("Программа завершена");
                break;
            }
            try {
                boolean ok = checker.verify(password);
                if (ok) {
                    System.out.println("Подходит!");
                } else {
                    System.out.println("Не подходит!");
                }
            } catch (IllegalStateException ex) {
                System.out.println("Ошибка: " + ex.getMessage());
                System.out.println("Программа завершена");
                break;
            }
        }
    }
}

class PasswordChecker {
    private Integer minLength = null;
    private Integer maxRepeats = null;

    // Сеттер минимальной длины
    public void setMinLength(int minLength) {
        if (minLength < 0) {
            throw new IllegalArgumentException("Минимальная длина не может быть отрицательной");
        }
        this.minLength = minLength;
    }

    // Сеттер максимального числа подряд идущих символов
    public void setMaxRepeats(int maxRepeats) {
        if (maxRepeats <= 0) {
            throw new IllegalArgumentException("Максимальное число повторов должно быть больше нуля");
        }
        this.maxRepeats = maxRepeats;
    }

    // Метод проверки пароля
    public boolean verify(String password) {
        if (minLength == null || maxRepeats == null) {
            throw new IllegalStateException("Настройки не выставлены");
        }
        if (password == null) return false;
        if (password.length() < minLength) return false;

        int repeatCount = 1;
        char prevChar = 0;
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);
            if (i > 0 && ch == prevChar) {
                repeatCount++;
                if (repeatCount > maxRepeats) return false;
            } else {
                repeatCount = 1;
            }
            prevChar = ch;
        }
        return true;
    }
}
