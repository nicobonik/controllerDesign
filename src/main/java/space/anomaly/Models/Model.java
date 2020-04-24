package space.anomaly.Models;

import space.anomaly.Framework.Message;
import space.anomaly.Framework.MessageHandler;

import java.util.ArrayList;
import java.util.List;

public abstract class Model {
    public double model_x;
    public double model_y;
    public double model_theta;
    public long loopTime;

    public List<Double> xList = new ArrayList<Double>();
    public List<Double> yList = new ArrayList<Double>();
    public List<Double> thetaList = new ArrayList<Double>();

    public Model(){
        xList.add(model_x);
        yList.add(model_y);
    }

    public String toString() {
        return "x: " + model_x + ", y: " + model_y + ", theta: " + model_theta;
    }

    public void run() throws InterruptedException {
        xList.add(model_x);
        yList.add(model_y);
        thetaList.add(model_theta);
        MessageHandler.sendToConsole(new Message(this.toString()));
        Thread.sleep(loopTime);
    }

}
