package space.anomaly.Framework;

import java.util.ArrayList;
import java.util.Iterator;

public class MessageHandler {

    public static ArrayList<Message> messages = new ArrayList<Message>();


    public static void sendToConsole() {
        Iterator<Message> iterator = messages.listIterator();
        clearScreen();
        while(iterator.hasNext()) {
            Message message = iterator.next();
            System.out.print("\r" + message.message);
        }
        messages.clear();
    }

    public static void sendToConsole(Message m) {
        System.out.print("\r" + m.message);
    }

    public static void runCommand() {}

    public static void add(Message m) {
        messages.add(m);
    }


    public static void clearScreen() {

        System.out.print("\033[H\033[2J");

        System.out.flush();

    }

}
