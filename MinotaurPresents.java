import java.util.Random;

class Present {
    int tag;
    Present next;

    Present(int tag) {
        this.tag = tag;
    }
}

class MinotaurServant implements Runnable {
    private static Present head;
    private static final Object lock = new Object();
    private static int giftsThanked = 0;
    private static int giftsChained = 0;
    private final int id;
    private final Random random = new Random();

    MinotaurServant(int id) {
        this.id = id;
    }

    private void addGiftToChain(int tag) {
        synchronized (lock) {
            Present present = new Present(tag);
            if (head == null || head.tag > tag) {
                present.next = head;
                head = present;
                System.out.println("Servant " + id + " added a gift with tag #" + tag + " to the chain.");
            } else {
                Present current = head;
                while (current.next != null && current.next.tag < tag) {
                    current = current.next;
                }
                present.next = current.next;
                current.next = present;
                System.out.println("Servant " + id + " added a gift with tag #" + tag + " to the chain.");
            }
            giftsChained++;
        }
    }

    private void thankYouCard() {
        synchronized (lock) {
            if (head == null) {
                System.out.println("Servant " + id + " tried to remove a gift, but the chain is empty!");
                return;
            }

            int tag = head.tag;
            head = head.next;
            System.out.println("Servant " + id + " removed a gift with tag #" + tag + " and wrote a thank you card for it!");
            giftsThanked++;
        }
    }

    private boolean searchGiftWithTag(int tag) {
        synchronized (lock) {
            Present current = head;
            while (current != null) {
                if (current.tag == tag) {
                    System.out.println("Servant " + id + " searched and found a gift with tag #" + tag + " in the chain.");
                    return true;
                }
                current = current.next;
            }
            System.out.println("Servant " + id + " searched and did not find a gift with tag #" + tag + " in the chain.");
            return false;
        }
    }

    @Override
    public void run() {
        while (giftsThanked < 500000) {
            int task = random.nextInt(3);
            if (task == 0) {
                if (giftsChained < 500000)
                {
                  addGiftToChain(giftsChained + 1);
                }
                else
                {
                  System.out.println("Servant " + id + " tried adding gift to chain but there are no more to add!");
                }
            } else if (task == 1) {
                thankYouCard();
            } else {
                int randomTag = random.nextInt(500000) + 1;
                searchGiftWithTag(randomTag);
            }
        }
    }
}

public class MinotaurPresents {
    public static void main(String[] args) throws InterruptedException {
        MinotaurServant[] servants = new MinotaurServant[4];
        Thread[] threads = new Thread[4];

        for (int i = 0; i < 4; i++) {
            servants[i] = new MinotaurServant(i + 1);
            threads[i] = new Thread(servants[i]);
            threads[i].start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("All presents have been given a thank you card!");
    }
}
