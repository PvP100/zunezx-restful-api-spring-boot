package net.codejava.store.embbed_system;


public class Gas {
    private double tempurature;
    private double gas;

    public Gas() {
    }


    public Gas(double tempurature, double gas) {

        this.tempurature = tempurature;
        this.gas = gas;
    }

    public double getTempurature() {
        return tempurature;
    }

    public void setTempurature(double tempurature) {
        this.tempurature = tempurature;
    }

    public double getGas() {
        return gas;
    }

    public void setGas(double gas) {
        this.gas = gas;
    }
}
