package combination;

public class CombinationInt {
    
    private int[] array;
    private static String delimeter = "";
    
    public static void main (String[] args) {
        CombinationInt comb = new CombinationInt();
        int[] mass = {6, 8, 4, 3, 1, 9, 2, 0, 5};
        comb.combinateIt(mass);
    }

    public void combinateIt (int[] mass, String del) {
        delimeter = del;
        combinateIt(mass);
    }
    
    public void combinateIt (int[] mass) {
        array = mass;
        // сортировка элементов
        sort(0);
        // вывод массива
        print();
        // перебор всех комбинаций
        pick(0);
    }

    private void pick (int i) {
        // если метод получил последнее число массива - выйти
        if (i == array.length - 1) { return; }
        // комбинации первого числа
        for (; i < array.length; i++) {
            // со всеми последующими
            for (int j = i + 1; j < array.length; j++) {
                // передать следующее число "ниже", для вывод всех его
                // комбинаций
                pick(i + 1);
                // сортировка дальше изменённого числа
                sort(i);
                // смена мест
                swap(i, j);
                sort(i + 1);
                print();
            }
        }
    }

    private void sort (int k) {
        for (int i = k + 1; i < array.length; i++) {
            int j = i - 1;
            int buff = array[i];
            while (j >= k && buff < array[j]) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = buff;
        }
    }

    private void print () {
        for (int k = 0; k < array.length; k++) {
            System.out.print(array[k] + delimeter);
        }
        System.out.println();
    }

    private void swap (int i, int j) {
        int change = array[i];
        array[i] = array[j];
        array[j] = change;
    }
}
