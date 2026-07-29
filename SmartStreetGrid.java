// THE DEVICE — Smart Street Lighting Grid for one city zone.
//
// extends UrbanUtility  → inherits serialNumber, powerRating, currentPower,
//                         status, displayInfo(), setCurrentPower()
// implements EcoDevice  → must provide enablePowerSaving(), disablePowerSaving()
//                         and may override emergencyOverride()

public class SmartStreetGrid extends UrbanUtility implements EcoDevice {

    private String  gridZone;           // city zone label, e.g. "North"
    private int     totalNodes;         // number of streetlight poles in this grid
    private int     lightIntensity;     // current brightness level: 0–100 %
    private boolean isPowerSavingMode;  // tracks whether eco mode is active

    // ----------------------------------------------------------------
    // CONSTRUCTOR CHAINING
    // Three entry points — each calls the next with this(...)
    // until the full 4-arg constructor is reached, which calls super().
    //
    //  new SmartStreetGrid("North")
    //      → this("North", 50)
    //          → this("SSG-NORTH-050", "North", 50, 7.5)
    //              → super("SSG-NORTH-050", 7.5)
    // ----------------------------------------------------------------

    // (1) Zone only — factory defaults: 50 nodes, 0.15 kW per node
    public SmartStreetGrid(String zone) {
        this(zone, 50);
    }

    // (2) Zone + node count — auto-generates serial number and rated power
    public SmartStreetGrid(String zone, int nodes) {
        this("SSG-" + zone.toUpperCase() + "-" + String.format("%03d", nodes),
             zone, nodes, nodes * 0.15);
    }

    // (3) Full constructor — all values explicit; calls super() to set base fields
    public SmartStreetGrid(String serialNumber, String zone, int nodes, double powerRating) {
        super(serialNumber, powerRating);
        this.gridZone          = zone;
        this.totalNodes        = nodes;
        this.lightIntensity    = 100;   // factory default: full brightness
        this.isPowerSavingMode = false;
    }

    // ----------------------------------------------------------------
    // ABSTRACT METHOD IMPLEMENTATION
    // UrbanUtility declared this as abstract — we must provide the body here.
    // This is this grid's unique core operation: regulate city lighting.
    // ----------------------------------------------------------------
    @Override
    public void performCoreAction() {
        System.out.println("  [" + getSerialNumber() + "]"
            + "  Zone: " + gridZone
            + "  |  Nodes: "     + totalNodes
            + "  |  Intensity: " + lightIntensity + "%"
            + "  |  Draw: "      + String.format("%.2f", getCurrentPower()) + " kW"
            + "  |  Status: "    + status);
    }

    // ----------------------------------------------------------------
    // INTERFACE IMPLEMENTATIONS — honouring the EcoDevice contract
    // ----------------------------------------------------------------

    @Override
    public void enablePowerSaving() {
        isPowerSavingMode = true;
        lightIntensity    = 40;                        // dim lights to 40 %
        setCurrentPower(getPowerRating() * 0.40);      // draw drops to 40 % of rated
        status = "ECO";
        System.out.println("  [" + gridZone + "] ECO ON  "
            + "→ Intensity: " + lightIntensity + "%"
            + "  |  Draw: " + String.format("%.2f", getCurrentPower()) + " kW");
    }

    @Override
    public void disablePowerSaving() {
        isPowerSavingMode = false;
        lightIntensity    = 100;
        setCurrentPower(getPowerRating());
        status = "ONLINE";
        System.out.println("  [" + gridZone + "] ECO OFF "
            + "→ Intensity: " + lightIntensity + "%"
            + "  |  Draw: " + String.format("%.2f", getCurrentPower()) + " kW");
    }

    // Override the interface default to run universal steps first,
    // then apply grid-specific emergency behaviour on top.
    @Override
    public void emergencyOverride(String reason) {
        EcoDevice.super.emergencyOverride(reason);     // run the shared default first
        isPowerSavingMode = false;
        lightIntensity    = 100;
        setCurrentPower(getPowerRating());
        status = "EMERGENCY";
        System.out.println("  [" + gridZone + "] All " + totalNodes
            + " nodes forced to full power → Status: " + status);
    }

    // ----------------------------------------------------------------
    // DYNAMIC POWER SCALING
    // Scale brightness and power draw to any level between 10 %–100 %.
    // ----------------------------------------------------------------
    public void scalePower(double factor) {
        if (factor < 0.10 || factor > 1.00) {
            System.out.println("  [" + gridZone + "] Invalid factor — use 0.10 to 1.00.");
            return;
        }
        lightIntensity = (int) (factor * 100);
        setCurrentPower(getPowerRating() * factor);
        System.out.println("  [" + gridZone + "] Scaled"
            + " → Intensity: " + lightIntensity + "%"
            + "  |  Draw: " + String.format("%.2f", getCurrentPower()) + " kW");
    }

    // Extend displayInfo() — prints base fields from UrbanUtility then adds grid fields
    @Override
    public void displayInfo() {
        System.out.println("  +------ Smart Street Grid ------+");
        super.displayInfo();
        System.out.println("  Zone         : " + gridZone);
        System.out.println("  Total Nodes  : " + totalNodes);
        System.out.println("  Intensity    : " + lightIntensity + "%");
        System.out.println("  Eco Mode     : " + (isPowerSavingMode ? "ON" : "OFF"));
        System.out.println("  +--------------------------------+");
    }
}