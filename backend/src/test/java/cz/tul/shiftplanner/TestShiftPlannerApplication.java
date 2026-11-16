package cz.tul.shiftplanner;

import org.springframework.boot.SpringApplication;

public class TestShiftPlannerApplication {

    public static void main(String[] args) {
        SpringApplication.from(ShiftPlannerApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
