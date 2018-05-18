package fileAttributes;

import java.io.File;
import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

public class FileAmount {
    private int amount;
    private CopyOnWriteArrayList<Thread> listThread;

    public int calc (String path) {
        File[] catalog = new File(path).listFiles();

        return counter(catalog);
    }

    private int counter (File[] catalog) {
        listThread = new CopyOnWriteArrayList<Thread>();
        amount = 0;
        for (File file : catalog) {
            if (file.isDirectory() && file.listFiles() != null) {
                // amount += counter(file.listFiles());
                Thread thread = new Thread( () -> amount += counter(file.listFiles()));
                listThread.add(thread);
                thread.start();
            } else amount++;
        }

        boolean alive;
        System.out.println("ListSize: " + listThread.size());
        do {
            alive = false;
            for (Thread t : listThread) {
                if (t.isAlive()) alive = true;
            }
        } while (alive);

        return amount;
    }

    public static void main (String[] args) {
        long time = System.nanoTime();
        int calc = new FileAmount().calc("d:\\");
        System.err.println("time: " + (System.nanoTime() - time));
        System.err.println(calc);
    }
}
