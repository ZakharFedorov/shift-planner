/**
 * Shared kernel: cross-cutting technical building blocks (time handling, error format).
 * Contains no business rules. Declared OPEN so other modules may use its sub-packages.
 */
@ApplicationModule(displayName = "Shared kernel", type = ApplicationModule.Type.OPEN)
package cz.tul.shiftplanner.shared;

import org.springframework.modulith.ApplicationModule;
