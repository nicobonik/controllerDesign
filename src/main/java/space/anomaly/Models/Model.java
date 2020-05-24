package space.anomaly.Models;

import space.anomaly.Framework.Message;
import space.anomaly.Framework.MessageHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class with the essentials for both making a model and sending the data to the graphing utility.
 */
public abstract class Model {
    /**
     * world x position, in arbitrary units
     */
    public double model_x;
    /**
     * world y position, in arbitrary units
     */
    public double model_y;
    /**
     * world angle, in radians
     */
    public double model_theta;
    /**
     * the time between loops in milliseconds to mimic the response of a real system
     */
    public long loopTime;

    /**
     * list of all x values
     */
    public List<Double> xList = new ArrayList<Double>();
    /**
     * list of all y values
     */
    public List<Double> yList = new ArrayList<Double>();
    /**
     * list of all angle values
     */
    public List<Double> thetaList = new ArrayList<Double>();

    /**
     * Model constructor, adds a dummy value to all lists to avoid {@link NullPointerException} from being thrown
     */
    public Model(){
        xList.add(model_x);
        yList.add(model_y);
    }


    public String toString() {
        return "x: " + model_x + ", y: " + model_y + ", theta: " + model_theta;
    }

    /**
     *
     * @throws InterruptedException  if the thread is interrupted during a sleep defined in ms by {@code loopTime}.
     */
    public void run() throws InterruptedException {
        xList.add(model_x);
        yList.add(model_y);
        thetaList.add(model_theta);
        MessageHandler.sendToConsole(new Message(this.toString()));
        Thread.sleep(loopTime);
    }

}
