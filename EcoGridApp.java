// MAIN RUNNER — boots the Smart City Eco-Grid and demonstrates every OOP concept
public class EcoGridApp {

    public static void main(String[] args) {

        banner("SMART CITY ECO-GRID  —  BOOT SEQUENCE");

        // ============================================================
        // CONSTRUCTOR CHAINING
        // Three ways to create a SmartStreetGrid, each delegating
        // to the next constructor until the full one calls super().
        // ============================================================
        section("1 — Constructor Chaining");

        SmartStreetGrid northGrid = new SmartStreetGrid("North");
        SmartStreetGrid eastGrid  = new SmartStreetGrid("East", 80);
        SmartStreetGrid westGrid  = new SmartStreetGrid("SSG-WEST-100", "West", 100, 15.0);

        System.out.println("  1-arg  → " + northGrid.getSerialNumber()
                           + "  (defaults: 50 nodes, 7.50 kW)");
        System.out.println("  2-arg  → " + eastGrid.getSerialNumber()
                           + "  (80 nodes, 12.00 kW auto-computed)");
        System.out.println("  4-arg  → " + westGrid.getSerialNumber()
                           + "  (100 nodes, 15.00 kW explicit)");

        // ============================================================
        // ABSTRACTION
        // displayInfo() skeleton lives in UrbanUtility; SmartStreetGrid
        // calls super.displayInfo() then appends its own extra fields.
        // ============================================================
        section("2 — Abstraction  (displayInfo via UrbanUtility + SmartStreetGrid)");
        eastGrid.displayInfo();

        // ============================================================
        // ABSTRACT METHOD
        // performCoreAction() was abstract in UrbanUtility — no body.
        // SmartStreetGrid provides the actual implementation.
        // ============================================================
        section("3 — Abstract Method  (performCoreAction)");
        eastGrid.performCoreAction();

        // ============================================================
        // INTERFACE — EcoDevice contract
        // enablePowerSaving / disablePowerSaving must exist on every
        // eco device — the interface enforces that at compile time.
        // ============================================================
        section("4 — Interface Contract  (EcoDevice eco-mode cycle)");
        eastGrid.enablePowerSaving();
        eastGrid.performCoreAction();
        eastGrid.disablePowerSaving();
        eastGrid.performCoreAction();

        // ============================================================
        // DYNAMIC POWER SCALING
        // scalePower() accepts a factor 0.10–1.00 and adjusts both
        // light intensity and power draw proportionally at runtime.
        // ============================================================
        section("5 — Dynamic Power Scaling");
        eastGrid.scalePower(0.75);
        eastGrid.scalePower(0.50);
        eastGrid.scalePower(0.25);
        eastGrid.scalePower(1.00);   // restore to 100 %

        // ============================================================
        // EMERGENCY OVERRIDE
        // Interface default method runs first (universal behaviour),
        // then the overridden version adds grid-specific steps.
        // ============================================================
        section("6 — Emergency Override  (interface default + device extension)");
        eastGrid.enablePowerSaving();   // put grid into eco mode first
        eastGrid.emergencyOverride("Major accident reported on East corridor");

        // ============================================================
        // POLYMORPHISM
        // Array typed as UrbanUtility holds SmartStreetGrid objects.
        // Java resolves the correct performCoreAction() at runtime
        // for each object — this is runtime polymorphism.
        // ============================================================
        section("7 — Polymorphism  (UrbanUtility reference → SmartStreetGrid objects)");
        UrbanUtility[] cityGrid = {
            new SmartStreetGrid("Alpha"),
            new SmartStreetGrid("Beta",  30),
            new SmartStreetGrid("SSG-GAMMA-060", "Gamma", 60, 9.0)
        };
        for (UrbanUtility unit : cityGrid) {
            unit.performCoreAction();   // each object runs its own version
        }

        banner("ALL SYSTEMS OPERATIONAL");
    }

    // ── small formatting helpers ──────────────────────────────────
    private static void banner(String title) {
        String line = "=".repeat(52);
        System.out.println("\n" + line);
        System.out.println("  " + title);
        System.out.println(line + "\n");
    }

    private static void section(String title) {
        System.out.println("\n--- " + title + " ---");
    }
}