package com.example;

/*
 * URLWizard.java
 *
 * A minimal interactive CLI that builds JDBC (and optional ODBC) connection strings
 * for DataDirect drivers. Supports Microsoft SQL Server and PostgreSQL in this MVP.
 * 
 * Usage (compile & run):
 *     javac URLWizard.java
 *     java URLWizard
 *
 * Example run:
 *     $ java URLWizard
 *     === DataDirect Connection‑URL Wizard ===
 *     Driver (sqlserver/postgres): sqlserver
 *     Hostname [localhost]: example.db.local
 *     Port [default 1433]: 1433
 *     Database name: finance
 *     Username: ben
 *     Password: ********
 *
 *     ---- Your connection strings ----
 *     JDBC : jdbc:datadirect:sqlserver://example.db.local:1433;database=finance;user=ben;password=********
 *     ODBC : Driver=DataDirect SQL Server Wire Protocol;Server=example.db.local;PortNumber=1433;DatabaseName=finance;UID=ben;PWD=********;
 * -------------------------------------
 *
 * LICENSE: MIT. Feel free to extend for additional drivers or add command‑line flags.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class URLWizard {
    private static final Scanner sc = new Scanner(System.in);

    private static class Template {
        String jdbc;
        String odbc;
        int defaultPort;

        Template(String jdbc, String odbc, int defaultPort) {
            this.jdbc = jdbc;
            this.odbc = odbc;
            this.defaultPort = defaultPort;
        }
    }

    private static final Map<String, Template> templates = new HashMap<>();
    static {
        templates.put("sqlserver", new Template(
                "jdbc:datadirect:sqlserver://%s:%d;database=%s;user=%s;password=%s",
                "Driver=DataDirect SQL Server Wire Protocol;Server=%s;PortNumber=%d;DatabaseName=%s;UID=%s;PWD=%s;",
                1433));
        templates.put("postgres", new Template(
                "jdbc:datadirect:postgresql://%s:%d/%s?user=%s&password=%s",
                "Driver=DataDirect PostgreSQL Wire Protocol;Server=%s;PortNumber=%d;DatabaseName=%s;UID=%s;PWD=%s;",
                5432));
        // Additional drivers (e.g., autonomousrest) can be added here
    }

    public static String[] buildStrings(
            String driver, String host, int port,
            String db, String user, String pwd) {

        Template t = templates.get(driver);
        return new String[] {
                String.format(t.jdbc, host, port, db, user, pwd),
                String.format(t.odbc, host, port, db, user, pwd)
        };
    }

    public static void main(String[] args) {
        System.out.println("=== DataDirect Connection‑URL Wizard ===");

        String driver;
        while (true) {
            System.out.print("Driver (sqlserver/postgres): ");
            driver = sc.nextLine().trim().toLowerCase();
            if (templates.containsKey(driver))
                break;
            System.out.println("Unsupported driver. Try again.");
        }

        Template t = templates.get(driver);

        System.out.print("Hostname [localhost]: ");
        String host = inputOrDefault("localhost");

        System.out.printf("Port [default %d]: ", t.defaultPort);
        int port = parsePort(inputOrDefault(String.valueOf(t.defaultPort)), t.defaultPort);

        System.out.print("Database name: ");
        String db = sc.nextLine().trim();

        System.out.print("Username: ");
        String user = sc.nextLine().trim();

        System.out.print("Password: ");
        String pwd = sc.nextLine().trim();

        String jdbc = String.format(t.jdbc, host, port, db, user, pwd);
        String odbc = String.format(t.odbc, host, port, db, user, pwd);

        System.out.println("\n---- Your connection strings ----");
        System.out.println("JDBC : " + jdbc);
        System.out.println("ODBC : " + odbc);
        System.out.println("-------------------------------------");
    }

    private static String inputOrDefault(String def) {
        String line = sc.nextLine().trim();
        return line.isEmpty() ? def : line;
    }

    private static int parsePort(String text, int fallback) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            System.out.println("Invalid port; using default " + fallback);
            return fallback;
        }
    }
}