package cz.tul.shiftplanner;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

/**
 * Guards the modular-monolith boundaries (ADR-0001): a module may use only the public API of
 * another module, and there must be no cycles between modules.
 */
class ModularityTests {

    private final ApplicationModules modules = ApplicationModules.of(ShiftPlannerApplication.class);

    @Test
    void moduleBoundariesAreRespected() {
        modules.verify();
    }

    /** Writes module diagrams (PlantUML, C4) to target/spring-modulith-docs, e.g. for the thesis. */
    @Test
    void writesModuleDocumentation() {
        new Documenter(modules).writeDocumentation();
    }
}
