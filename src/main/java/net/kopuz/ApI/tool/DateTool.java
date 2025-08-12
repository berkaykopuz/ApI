package net.kopuz.ApI.tool;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;

@Component
public class DateTool {
    public DateTool() {
    }

    @Tool(description = "It returns the date of today. You must answer depending on this.")
    public String getCurrentDate() {
        return LocalDate.now(Clock.systemDefaultZone()).toString();
    }
}
