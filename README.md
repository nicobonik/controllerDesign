# controllerDesign Framework -- 2d Robot Simulator [![](https://jitpack.io/v/nicobonik/controllerDesign.svg)](https://jitpack.io/#nicobonik/controllerDesign)



this is another little project i made during quarantine to design controllers for my quarantine robot and only friend, [Finley](https://github.com/nicobonik/robotNode). 
I was frustrated with debugging arduino code where i didn't know if the problem was the code itself or the robot it was running on. 
So, I made a simple litte simulator that is designed to be a modular system that i can use for any future robotics projects. This tool helps with the process of designing accurate, redundant, and transportable control systems. 

## How It Works
The framework is split up into 3 parts -- The Model, the Controller, and the Framework.

### 1. Model
The `Model` is a kinematic model of a 2 dimensional system. Think of the `Model` as the physical robot, it has velocity and limits to how fast it can go etc. a `Model` class' `run()` method handles all of the kinematics based on any inputs you want, but the system must output a basic controller output -- <br>
<img src="https://i.imgur.com/AJWRBI8.gif" />
<br>
where the outputs are `model_x`, `model_y`, and `model_theta`.

### 2. Controller
The `Controller` is the heavy lifting of the equation. It takes the model values as stated above every loop, and then uses those inputs to generate a desired output. The `Controller` is where most of the design process goes. 
It is essentially the "function" part of a closed loop system.<br>
The `Controller` also handles all of the graphing. All you have to do to get that up and running is to look at the XChart Documentation and use an `XYSeries` that contains the model's `xList` and `yList`. 

### 3. Framework
The Framework of this project contains the Debugger code, aka the `MessageHandler`. to add a `Message` to the Debugger, set up a new `Message` Object in your constructor or initialize method:
```
Message m = new Message();
```

Then, to change the message content, use the `setMessage()` function of the message, and the framework will take care of updating the swing UI for you. 

## How To Use This library, effectively
This library works best when the experimentation is being done on the `Controller`, and not the `Model`. Therefore it is probably best to derive a kinematic model elsewhere, then plug it into a `Model` so you can start designing controllers for it. For more examples on the specifics of this library, go to the [wiki](https://github.com/nicobonik/controllerDesign/wiki) page of this repo.

## Building This Repository
To build this project from the source, clone this repository and put it into your IDE, everything _should_ work as expected.

## Libraries used
for the UI elements I used [XChart](https://github.com/knowm/XChart) because it was lightweight and fast and easy to integrate with my system. XChart is a graphing utility based on the Java Swing library, which is also used sparingly in this project.
