package com.example.builder;

public class Computer {
    private final String CPU;          // Required
    private final String RAM;          // Required
    private final String storage;      // Required
    private final String graphicsCard; // Optional
    private final boolean isBluetoothEnabled; // Optional
    private Computer(Builder builder) {
        this.CPU = builder.CPU;
        this.RAM = builder.RAM;
        this.storage = builder.storage;
        this.graphicsCard = builder.graphicsCard;
        this.isBluetoothEnabled = builder.isBluetoothEnabled;
    }

    public String getCPU() { return CPU; }
    public String getRAM() { return RAM; }
    public String getStorage() { return storage; }
    public String getGraphicsCard() { return graphicsCard; }
    public boolean isBluetoothEnabled() { return isBluetoothEnabled; }

    @Override
    public String toString() {
        return "Computer Configuration:\n" +
               " - CPU: " + CPU + "\n" +
               " - RAM: " + RAM + "\n" +
               " - Storage: " + storage + "\n" +
               " - Graphics Card: " + (graphicsCard != null ? graphicsCard : "Integrated Graphics") + "\n" +
               " - Bluetooth: " + (isBluetoothEnabled ? "Enabled" : "Disabled") + "\n";
    }

    public static class Builder {
        private final String CPU;
        private final String RAM;
        private final String storage;
        private String graphicsCard; 
        private boolean isBluetoothEnabled;

        public Builder(String CPU, String RAM, String storage) {
            this.CPU = CPU;
            this.RAM = RAM;
            this.storage = storage;
        }

        public Builder setGraphicsCard(String graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }

        public Builder setBluetoothEnabled(boolean isBluetoothEnabled) {
            this.isBluetoothEnabled = isBluetoothEnabled;
            return this;
        }
        public Computer build() {
            return new Computer(this);
        }
    }
}