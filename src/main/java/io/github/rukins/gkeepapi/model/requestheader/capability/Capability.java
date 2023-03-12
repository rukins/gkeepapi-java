package io.github.rukins.gkeepapi.model.requestheader.capability;

import java.util.Arrays;
import java.util.List;

public class Capability {
    public static final List<Capability> DEFAULT_CAPABILITIES = Arrays.asList(
            new Capability(CapabilityType.TR), new Capability(CapabilityType.EC),
            new Capability(CapabilityType.SH), new Capability(CapabilityType.RB),
            new Capability(CapabilityType.LB), new Capability(CapabilityType.DR),
            new Capability(CapabilityType.AN), new Capability(CapabilityType.PI),
            new Capability(CapabilityType.EX), new Capability(CapabilityType.CO),
            new Capability(CapabilityType.MI), new Capability(CapabilityType.SNB),
            new Capability(CapabilityType.IN), new Capability(CapabilityType.PS),
            new Capability(CapabilityType.NC)
    );

    private CapabilityType type;

    public Capability(CapabilityType type) {
        this.type = type;
    }

    public CapabilityType getType() {
        return type;
    }

    public void setType(CapabilityType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Capability{" +
                "type=" + type +
                '}';
    }
}
