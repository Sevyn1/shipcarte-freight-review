# Review decisions

The public module deliberately contains only the density calculator and the unit conversion helpers it needs. It excludes the parent project's carrier adapters and service configuration. Tests execute locally without operational accounts.

The calculation API now throws `IllegalArgumentException` on invalid measurements or units. This is an intentional behavior change from the old zero-return/fallback behavior, documented for callers. A service that adopts the updated library must translate this exception into an appropriate validation response.

Intermediate rounding was removed. Centimetre conversion now uses a double-precision 2.54 constant, and kilogram conversions use the exact pound and ounce definitions. Floating-point tolerance is used in the tests; this is not a money calculation and makes no decimal-currency precision claim.

Standalone conversion helpers retain their historical non-positive-input behavior for compatibility. The density API performs stricter validation before invoking them. Density outside the supported finite positive double range is rejected.

The four accessible ShipCarte repositories had identical source trees at review time. They are one shared course project, not four independent projects. Original source years and contributor headers establish shared provenance, not Favour's contribution dates or sole authorship.
