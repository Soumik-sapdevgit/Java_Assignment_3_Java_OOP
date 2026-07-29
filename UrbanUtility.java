// THE BLUEPRINT
// Abstract base class for every public infrastructure device in the city.
// Cannot be instantiated on its own — must be extended by a real device class.
// Stores the two core data points every utility must have: serial number and power metrics.
// Forces every child class to implement performCoreAction() — their own unique operation.

public abstract class UrbanUtility {

    private String serialNumber;    // unique device ID — set once at construction, never changed
    private double powerRating;     // maximum power capacity in kW (factory specification)
    protected double currentPower;  // live power draw in kW — child classes update this directly
    protected String status;        // current state: ONLINE / ECO / EMERGENCY

    // Constructor called by every child class via super()
    public UrbanUtility(String serialNumber, double powerRating) {
        this.serialNumber = serialNumber;
        this.powerRating  = powerRating;
        this.currentPower = powerRating;  // on boot, draw equals full rated power
        this.status       = "ONLINE";
    }

    // ABSTRACT METHOD — declared here but has no body.
    // Every subclass MUST override this with its own specific operation.
    // This is abstraction: we know a core action exists, but only the child knows what it does.
    public abstract void performCoreAction();

    // Concrete shared behaviour — any utility can print its base information
    public void displayInfo() {
        System.out.println("  Serial       : " + serialNumber);
        System.out.println("  Status       : " + status);
        System.out.println("  Max Capacity : " + powerRating + " kW");
        System.out.println("  Current Draw : " + String.format("%.2f", currentPower) + " kW");
    }

    // Protected helper — child classes call this to update power, capped at rated limit
    protected void setCurrentPower(double kw) {
        this.currentPower = Math.min(kw, powerRating);
    }

    public String getSerialNumber() { return serialNumber; }
    public double getPowerRating()  { return powerRating; }
    public double getCurrentPower() { return currentPower; }
    public String getStatus()       { return status; }
}