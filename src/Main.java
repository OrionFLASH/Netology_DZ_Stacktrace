import java.util.Scanner; // Импортируем Scanner для чтения пользовательского ввода

public class Main { // Главный класс программы
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Создаём объект Scanner для работы с консолью
        PasswordChecker checker = new PasswordChecker(); // Создаём объект PasswordChecker для проверки паролей

        try {
            // Просим пользователя ввести минимальную длину пароля
            System.out.print("Введите мин. длину пароля: ");
            int minLength = Integer.parseInt(scanner.nextLine().trim()); // Считываем строку, удаляем пробелы, преобразуем в int
            checker.setMinLength(minLength); // Устанавливаем минимальную длину

            // Просим пользователя ввести максимальное число подряд идущих одинаковых символов
            System.out.print("Введите макс. допустимое количество повторений символа подряд: ");
            int maxRepeats = Integer.parseInt(scanner.nextLine().trim()); // Считываем, преобразуем в int
            checker.setMaxRepeats(maxRepeats); // Устанавливаем максимальное количество повторов
        } catch (IllegalArgumentException ex) { // Если пользователь ввёл некорректное значение (например, <0)
            System.out.println("Ошибка: " + ex.getMessage()); // Сообщаем пользователю причину
            System.out.println("Программа завершена"); // Завершаем программу
            return; // Выход из main
        }

        // Основной цикл проверки паролей
        while (true) {
            System.out.print("Введите пароль или end: "); // Просим пользователя ввести пароль или 'end'
            String password = scanner.nextLine(); // Считываем ввод

            // Если пользователь хочет завершить работу
            if (password.equalsIgnoreCase("end")) {
                System.out.println("Программа завершена"); // Сообщаем о завершении
                break; // Выходим из цикла
            }

            try {
                boolean ok = checker.verify(password); // Проверяем пароль
                if (ok) { // Если пароль соответствует условиям
                    System.out.println("Подходит!");
                } else { // Если не соответствует
                    System.out.println("Не подходит!");
                }
            } catch (IllegalStateException ex) { // Если параметры не были выставлены (по логике, здесь уже не случится)
                System.out.println("Ошибка: " + ex.getMessage());
                System.out.println("Программа завершена");
                break; // Завершаем программу
            }
        }
    }
}

// Класс проверки паролей согласно условиям задачи
class PasswordChecker {
    private Integer minLength = null; // Минимальная длина пароля. null — если не установлено
    private Integer maxRepeats = null; // Макс. число подряд идущих одинаковых символов. null — если не установлено

    // Сеттер минимальной длины пароля
    public void setMinLength(int minLength) {
        if (minLength < 0) { // Если значение некорректное
            throw new IllegalArgumentException("Минимальная длина не может быть отрицательной");
        }
        this.minLength = minLength; // Запоминаем
    }

    // Сеттер максимального количества подряд идущих одинаковых символов
    public void setMaxRepeats(int maxRepeats) {
        if (maxRepeats <= 0) { // Если значение некорректное
            throw new IllegalArgumentException("Максимальное число повторов должно быть больше нуля");
        }
        this.maxRepeats = maxRepeats; // Запоминаем
    }

    // Метод проверки пароля
    public boolean verify(String password) {
        // Если настройки не установлены, кидаем исключение
        if (minLength == null || maxRepeats == null) {
            throw new IllegalStateException("Настройки не выставлены");
        }
        // Проверяем на null и минимальную длину
        if (password == null) return false;
        if (password.length() < minLength) return false;

        int repeatCount = 1; // Счётчик подряд идущих символов
        char prevChar = 0;   // Для хранения предыдущего символа

        // Перебираем все символы пароля
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i); // Текущий символ
            if (i > 0 && ch == prevChar) { // Если не первый и совпадает с предыдущим
                repeatCount++; // Увеличиваем счётчик подряд идущих
                if (repeatCount > maxRepeats) { // Если превышено ограничение
                    return false; // Пароль не подходит
                }
            } else {
                repeatCount = 1; // Новый символ, сбрасываем счётчик
            }
            prevChar = ch; // Запоминаем текущий символ
        }
        return true; // Пароль соответствует всем условиям
    }
}
