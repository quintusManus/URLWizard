package com.example;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;


public class URLWizardTest {

    @Test
    void sqlServerDefaultPort() {
        String[] out = URLWizard.buildStrings(
            "sqlserver", "example.db.local", 1433,
            "finance", "ben", "pwd");
        assertEquals(
            "jdbc:datadirect:sqlserver://example.db.local:1433;database=finance;user=ben;password=pwd",
            out[0]);
        assertTrue(out[1].startsWith(
            "Driver=DataDirect SQL Server Wire Protocol;Server=example.db.local;PortNumber=1433"));
    }

    @Test
    void postgresCustomPort() {
        String[] out = URLWizard.buildStrings(
            "postgres", "db‑host", 5555,
            "sales", "alice", "pw");
        assertEquals(
            "jdbc:datadirect:postgresql://db‑host:5555/sales?user=alice&password=pw",
            out[0]);
    }

    @Test
    void unsupportedDriverThrows() {
        assertThrows(NullPointerException.class, () -> {
            URLWizard.buildStrings("oracle", "h", 1, "d", "u", "p");
        });
    }
}