package space.anomaly.Framework;

public class Message {
     public String message;

    public Message() {

    }

    public Message(String message) {
        this.message = message;
    }

    public Message(int message) {
        this.message = Integer.toString(message);
    }

    public Message(double message) {
        this.message = Double.toString(message);
    }

    public Message(float message) {
        this.message = Float.toString(message);
    }

    public Message(byte message) {
        this.message = Byte.toString(message);
    }

    public Message(long message) {
        this.message = Long.toString(message);
    }

    public Message(String caption, String val) {

        message = caption +
                ": " +
                val;
    }

    public Message(String caption, int val) {

        message = caption +
                ": " +
                val;
    }

    public Message(String caption, double val) {

        message = caption +
                ": " +
                val;
    }


}
