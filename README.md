# ShipCarte — Freight Calculation Review

[![Verify](https://github.com/Sevyn1/shipcarte-freight-review/actions/workflows/verify.yml/badge.svg)](https://github.com/Sevyn1/shipcarte-freight-review/actions/workflows/verify.yml)

A focused maintenance review of the freight-density module from a shared Java course project. The calculation converts shipment dimensions and mass into **pounds per cubic foot**.

**Java 17 · Maven · JUnit · input validation · unit conversions · regression testing**

## The problem and the fix

The original conversion helpers rounded each intermediate value to two decimal places. Very small measurements could become zero, and fractional values lost precision before density was calculated. The calculator also treated unrecognized units as if they were inches or pounds, returned zero for invalid dimensions, and did not explicitly reject non-finite input.

The reviewed implementation retains conversion precision, validates supported units, rejects non-positive/non-finite measurements and unsupported numeric ranges, and provides a local command-line example. The original helper names and course source credits are preserved; exact baseline files are included in [docs/baseline](docs/baseline).

## Run

Requires Java 17. The Maven wrapper downloads Maven and dependencies on its first run.

```sh
./mvnw verify
java -cp target/classes com.shipcarte.techrubiks.Main 30.48 30.48 30.48 4.5359237 cm kg
```

Expected result:

```text
Density: 10.000000 lbs/ft³
```

Use `mvnw.cmd` on Windows. Supported units are `inch`, `feet`, `cm`, `lbs` and `kg`; names are trimmed and case-insensitive. The library returns the full double-precision result; the CLI rounds only for display.

## Verification

**13 test cases pass**: equivalent imperial/metric shipments, fractional and very small values, ounce conversions, normalized units, invalid dimensions and mass, unknown/null units, overflow and underflow. The Maven build packages the module successfully. No carrier account, database, cloud credential or network service is required by the calculator.

See [review decisions](docs/REVIEW.md) and [interview preparation](docs/INTERVIEW_REVIEW.md).

## Course context and scope

Favour Ojo identifies ShipCarte as a Pragra course project and confirms permission to use the shared course source. The accessible project versions have identical trees. This repository showcases a small reviewed module from that project, not sole authorship of the larger platform or a production logistics deployment.

The larger recovered platform includes legacy Spring Boot/MySQL and carrier integrations. Its saved integration credentials, runtime configurations, bundled carrier binaries and Git history are **not included** here. Its overall build and live integrations have not been verified in this review.

The source retains original contributor credits. October 2026 maintenance and tests were developed with Codex assistance, directed and reviewed by Favour Ojo; these new changes are not backdated to the course year. No new license is imposed on the shared course source. Maven wrapper notices are documented separately.
