DataDirect Connection‑URL Wizard

“Your fastest path from database host → valid DataDirect connection string.”

⸻

Why this exists

New developers often spend their first 5–10 minutes with a DataDirect driver combing through doc tables and examples just to build a JDBC or ODBC connection string.
This tiny CLI turns that friction into a 20‑second Q&A so you can ship code (or debug) faster.

Unofficial demo – built by Benjamin Woods overnight for a Progress interview. Not built or endorsed by Progress Software Corp.

⸻

Features (MVP)
	•	Interactive CLI (pure Java, no external deps)
	•	Generates both JDBC and ODBC strings
	•	Supports SQL Server and PostgreSQL drivers out of the box
	•	Sensible defaults (e.g. localhost, default port)
	•	Unit‑tested with JUnit 5
	•	MIT‑licensed, single‑file source for easy copy‑paste into other projects

Planned
	•	Autonomous REST connector template
	•	Flag‑based non‑interactive mode (--driver=… --host=…)

⸻

Quick Start

# clone & enter
$ git clone https://github.com/ben‑woods/datadirect‑url‑wizard.git
$ cd datadirect‑url‑wizard

# compile & run (Java 11+)
$ javac URLWizard.java
$ java URLWizard

Example session:

=== DataDirect Connection‑URL Wizard ===
Driver (sqlserver/postgres): sqlserver
Hostname [localhost]: example.db.local
Port [default 1433]:
Database name: finance
Username: ben
Password: ********

---- Your connection strings ----
JDBC : jdbc:datadirect:sqlserver://example.db.local:1433;database=finance;user=ben;password=********
ODBC : Driver=DataDirect SQL Server Wire Protocol;Server=example.db.local;PortNumber=1433;DatabaseName=finance;UID=ben;PWD=********;
---------------------------------


⸻

Running the unit tests

# assuming junit‑platform‑console‑standalone‑1.10.2.jar is in the project root
$ javac -cp junit-platform-console-standalone-1.10.2.jar:. URLWizard.java URLWizardTest.java
$ java -jar junit-platform-console-standalone-1.10.2.jar -cp .

All tests should pass:

Test run finished after 42 ms
[         3 containers found      ]
[         0 containers skipped    ]
[         3 containers started    ]
[         0 containers aborted    ]
[         3 containers successful ]
[         0 containers failed     ]
[         3 tests found           ]
[         0 tests skipped         ]
[         3 tests started         ]
[         0 tests aborted         ]
[         3 tests successful      ]
[         0 tests failed          ]


⸻

Extending to new drivers

Add a Template entry in URLWizard.java:

templates.put("snowflake", new Template(
    "jdbc:datadirect:snowflake://%s:%d;warehouse=%s;user=%s;password=%s",
    "Driver=DataDirect Snowflake;Server=%s;PortNumber=%d;Warehouse=%s;UID=%s;PWD=%s;",
    443));

Re‑compile, run, enjoy.

⸻

Roadmap (if adopted)
	1.	Binary releases – pre‑built JAR + Homebrew/RPM packages.
	2.	CI template – GitHub Actions to publish on every push.
	3.	JSON‑config support – read answers from a file for headless use.

PRs and ideas welcome!

⸻

License

MIT
