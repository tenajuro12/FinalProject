package org.example.src;

public class LegacyFlightAdapter implements FlightAdapter {
    private LegacyFlightSystem legacyFlightSystem;

    public LegacyFlightAdapter(LegacyFlightSystem legacyFlightSystem) {
        this.legacyFlightSystem = legacyFlightSystem;
    }

    @Override
    public void bookTicket(int flightId, String passengerName) {
        // Assume mapping between flightId and legacy flightCode
        int legacyFlightCode = mapFlightIdToLegacyCode(flightId);

        // Delegate to the legacy system
        legacyFlightSystem.book(legacyFlightCode, passengerName);
    }

    private int mapFlightIdToLegacyCode(int flightId) {
        // Your mapping logic here
        return flightId + 1000; // Just an example, replace with actual logic
    }
}
