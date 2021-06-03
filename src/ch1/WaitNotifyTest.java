package ch1;

public class WaitNotifyTest {
    static class TestTask implements Runnable {
        @Override
        public void run() {
            String taskId = String.valueOf(Math.random());
            System.out.printf("this is %s\n", taskId);
            System.out.printf("%s:before wait\n", taskId);
            synchronized (this) {
                try {
                    this.wait();
                    System.out.printf("%s:after wait\n", taskId);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TestTask testTask = new TestTask();
        Thread t1 = new Thread(testTask);
        Thread t2 = new Thread(testTask);
        t1.start();
        t2.start();

        Thread t3 = new Thread(() -> {
            synchronized (testTask) {
                try {
                    Thread.sleep(1000);
                    System.out.println("notify1");
                    testTask.notify();
                    Thread.sleep(3000);
                    System.out.println("notify2");
                    testTask.notify();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        t3.start();
    }
}
