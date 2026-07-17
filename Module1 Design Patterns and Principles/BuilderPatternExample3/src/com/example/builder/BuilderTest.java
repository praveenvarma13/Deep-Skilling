package com.example.builder;

public class BuilderTest {
    public static void main(String[] args) {
        System.out.println("--- Testing Builder Pattern ---\n");

        Computer officePC = new Computer.Builder("Intel i3", "8GB", "256GB SSD")
                                        .build();
        
        System.out.println("Office PC Spec:");
        System.out.println(officePC);

        Computer gamingPC = new Computer.Builder("AMD Ryzen 9", "32GB", "2TB NVMe SSD")
                                        .setGraphicsCard("NVIDIA RTX 4090")
                                        .setBluetoothEnabled(true)
                                        .build();

        System.out.println("Gaming PC Spec:");
        System.out.println(gamingPC);
    }
}