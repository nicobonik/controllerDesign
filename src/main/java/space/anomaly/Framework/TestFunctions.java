package space.anomaly.Framework;

public class TestFunctions {
    public static void main(String[] args) throws InterruptedException {


        MessageHandler.initialize();

        Message m = new Message();
        Message m1 = new Message("yeet");

        MessageHandler.addMessage(m);

        for (int i = 0; i < 1000; i++) {
            m.setMessage("hello!: " + i);
            if(i == 500) {
                MessageHandler.addMessage(m1);
            }

            MessageHandler.update();
            Thread.sleep(50);
        }

    }
}
