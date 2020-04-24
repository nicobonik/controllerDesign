package space.anomaly.Framework;

import space.anomaly.Controllers.*;

public class Frame {
    public static DifferentialGoToGoal controller = new DifferentialGoToGoal(4, 6);

    public static void main(String[] args) throws InterruptedException {

        controller.graph();

        System.out.println("initializing...");
        Thread.sleep(2000);
        System.out.println();

        runController();
        controller.x = 8;
        controller.y = 10;
        runController();
        controller.x = 2;
        controller.y = 4;
        runController();
        controller.x = -3;
        controller.y = -4;
        runController();

        System.out.println();
//        System.out.println("loop num: " + i);
    }

    public static void runController() throws InterruptedException {
        int i = 0;
        while(Math.abs((controller.model.model_x - controller.x)) + Math.abs((controller.model.model_y - controller.y)) > 0.1 && i < 9000){
            controller.run();

            i++;
        }
    }

}