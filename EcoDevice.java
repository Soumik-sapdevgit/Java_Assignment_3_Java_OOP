// THE CONTRACT
// Interface that any eco-friendly device MUST honour.
// An interface is a guarantee — any class that implements EcoDevice
// promises to have all of these capabilities.
//
// enablePowerSaving()  and  disablePowerSaving()  are mandatory:
//   every implementing class must write its own version.
//
// emergencyOverride()  is a DEFAULT method:
//   it has a built-in body that works universally across all device brands.
//   A class can override it to add extra device-specific steps on top.

public interface EcoDevice {

    // MANDATORY — each device defines HOW it reduces power consumption
    void enablePowerSaving();

    // MANDATORY — each device defines HOW it restores full operational power
    void disablePowerSaving();

    // DEFAULT — universal safety override built into the contract itself.
    // Works out-of-the-box on any device; can be extended by the implementing class.
    default void emergencyOverride(String reason) {
        System.out.println("  [EMERGENCY OVERRIDE] Reason  : " + reason);
        System.out.println("  [EMERGENCY OVERRIDE] Suspending all eco-saving protocols.");
        System.out.println("  [EMERGENCY OVERRIDE] Forcing device to full operational capacity.");
    }
}