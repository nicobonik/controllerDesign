package space.anomaly.Framework;

import space.anomaly.Controllers.GoToGoal;
import space.anomaly.Controllers.PID;

public class Frame {

    public static void main(String[] args) throws InterruptedException {
        PID controller = new PID(4, 6);

        int i = 0;
        while(Math.abs((controller.model.model_x - controller.x) + (controller.model.model_y - controller.y)) > 0.1){
            controller.run();

            i++;
        }

        controller.graph();

        System.out.println();
        System.out.println("loop num: " + i);
    }

}