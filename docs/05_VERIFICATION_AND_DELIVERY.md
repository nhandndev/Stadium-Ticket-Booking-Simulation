# Verification and Delivery

Recorded 2026-11-12 after the scope update. Runtime checks below were executed
during that review. The subsequent documentation cleanup rechecked links and
artifact preservation.

## Executed checks

- Compiled Java 17 source and retained assert-based checks.
- Replayed 139 Console flows with 1469 captured input/output steps using
  copied CSV fixtures. The 88 feature files contain actual Console responses.
- BookingConcurrencySmoke passed simultaneous ordinary checkout of one seat,
  one successful booking, one ticket, completed-payment retry and transaction CSV
  roundtrip including legacy booking-only input.
- All 14 source CSV files loaded through current Java repositories with 11191 rows.
- The former operator account is rejected as UNASSIGNED without source CSV writes.
- SeedDataSmoke passed physical-seat parent links, unique sample Fan names and
  the supplied fixture expectations.
- Before documentation consolidation, source/UML checks matched 97 class/enum contracts against 190 expanded boxes
  across six pages and five Mermaid files. The duplicate draw.io copy was subsequently removed.
- 82 Use Case nodes map to current feature walkthroughs.

## Data changes

- Staff ID 5 has role UNASSIGNED and name Unassigned Staff. Its existing ID,
  email and credentials remain. Administrator can assign an eligible role.
- transactions.csv uses 11 columns with bookingId as owner. The removed extra
  column was empty in all supplied booking records.
- The 1007 Fans, 10100 physical seats and existing booking/payment/ticket history
  remain. CSV total remains 11191 nonblank rows.

## Verification limits

Full rendered diagram wire routing has not been visually reviewed.
Synchronization coordinates one JVM. CSV persistence is per-file and multi-file
checkout is vulnerable to interruption. Passwords are demo plaintext, payment and
refund are mocked, and audit logging currently covers refund review.

## Reproducing the checks

### Java checks

```bash
javac --release 17 -encoding UTF-8 -d /tmp/stadium-check $(find src -name '*.java') tests/BookingConcurrencySmoke.java tests/SeedDataSmoke.java
java -ea -cp /tmp/stadium-check BookingConcurrencySmoke
java -ea -cp /tmp/stadium-check SeedDataSmoke data
```

### Console acceptance

Follow [preparation](Demo_Tung_Feature/00_CHUAN_BI.md) and
[ordered flows](Demo_Tung_Feature/01_THU_TU_DEMO.md) on a copied dataset.
The current set contains 88 feature guides and 139 executed flows.

- Guest search/filter, details and seat map.
- Registration, both login routes, invalid credentials and account states.
- Fan booking/payment/history/cancellation/refund and ticket access.
- Seller customer creation/search, customer booking, cash confirmation and issue.
- Gate validation, confirmation, wrong-match and repeat check-in rejection.
- Administrator venue/match/pricing/sales/account/role CRUD and constraints.
- Refund approval/rejection, summary, audit, invalid menu input and exit.
- Ordinary checkout concurrency, payment retry and transaction persistence.

Compare every entered value with its captured Console output. Time-dependent
lists require a future eligible Match on the day of presentation.

## Submission checklist

- [x] Ordinary Guest, Fan, Seller, Gate Staff and Administrator workflows.
- [x] Default synchronized checkout protects seat commits within one JVM.
- [x] Current transaction CSV and legacy booking-only parser.
- [x] 97 source contracts match 190 expanded UML boxes and five Mermaid files.
- [x] Canonical six-page draw.io and five Mermaid files retained.
- [x] All 82 Use Case nodes map to feature walkthroughs.
- [x] 88 feature guides and 139 Console flows refreshed.
- [x] Retained Java booking/seed checks pass on isolated data.
- [x] README and current scope documents describe the delivered feature set.
- [ ] Student rehearses the walkthrough with a future Match and correct demo CSV.
- [ ] Student visually reviews draw.io routing and export before submission.
- [ ] Student prepares the final submission package required by the instructor.

See [verification report](05_VERIFICATION_AND_DELIVERY.md) for operating limits.
