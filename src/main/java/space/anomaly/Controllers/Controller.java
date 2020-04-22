package space.anomaly.Controllers;

import space.anomaly.Models.Model;

public abstract class Controller {
    public Model model;

    public Controller() {
    }

    public void run() throws InterruptedException {
        model.run();
    }

}
