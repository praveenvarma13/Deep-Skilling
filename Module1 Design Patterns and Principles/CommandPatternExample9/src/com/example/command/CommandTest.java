package com.example.command;

public class CommandTest {
    public static void main(String[] args) {
        System.out.println("--- Testing Command Pattern ---\n");

        // 1. Create the receiver (The physical light fixture)
        Light livingRoomLight = new Light();

        // 2. Create the command objects loaded with the target receiver
        Command lightOn = new LightOnCommand(livingRoomLight);
        Command lightOff = new LightOffCommand(livingRoomLight);

        // 3. Create the invoker (The remote control handset)
        RemoteControl remote = new RemoteControl();

        // Turn the light ON
        System.out.println("Action: Turning Light On");
        remote.setCommand(lightOn);
        remote.pressButton();

        System.out.println("\n----------------------------------\n");

        // Turn the light OFF
        System.out.println("Action: Turning Light Off");
        remote.setCommand(lightOff);
        remote.pressButton();
    }
}