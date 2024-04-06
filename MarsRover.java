import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class MarsRover {
    private AtomicIntegerArray highestTemperatures = new AtomicIntegerArray(5);
    private AtomicIntegerArray lowestTemperatures = new AtomicIntegerArray(5);

    public void runSimulation() {
        Thread[] sensors = new Thread[8];
        for (int i = 0; i < 8; i++) {
            sensors[i] = new Thread(() -> {
                Random random = new Random();
                while (!Thread.currentThread().isInterrupted()) {
                    int temperature = random.nextInt(171) - 100;
                    updateTemperatures(temperature);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
            sensors[i].start();
        }

        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        printHourlyReport();

        highestTemperatures = new AtomicIntegerArray(5);
        lowestTemperatures = new AtomicIntegerArray(5);

        runSimulation();
    }

    private void updateTemperatures(int temperature) {
        int lowestHighest = highestTemperatures.get(0);
        int lowestHighestIndex = 0;
        for (int i = 1; i < highestTemperatures.length(); i++)
        {
          if (lowestHighest > highestTemperatures.get(i))
          {
            lowestHighest = highestTemperatures.get(i);
            lowestHighestIndex = i;
          }
        }

        if (temperature > lowestHighest)
        {
          highestTemperatures.set(lowestHighestIndex, temperature);
        }



        int highestLowest = lowestTemperatures.get(0);
        int highestLowestIndex = 0;
        for (int i = 1; i < lowestTemperatures.length(); i++)
        {
          if (highestLowest < lowestTemperatures.get(i))
          {
            highestLowest = lowestTemperatures.get(i);
            highestLowestIndex = i;
          }
        }

        if (temperature < lowestHighest)
        {
          lowestTemperatures.set(highestLowestIndex, temperature);
        }
    }

    private void printHourlyReport() {
        System.out.println("Hourly Report:");

        System.out.println("5 highest temperatures captured:");
        for (int i = 0; i < highestTemperatures.length(); i++) {
            System.out.println(highestTemperatures.get(i) + "F");
        }

        System.out.println("5 lowest temperatures captured:");
        for (int i = 0; i < lowestTemperatures.length(); i++) {
            System.out.println(lowestTemperatures.get(i) + "F");
        }
    }

    public static void main(String[] args) {
        MarsRover module = new MarsRover();
        module.runSimulation();
    }
}
