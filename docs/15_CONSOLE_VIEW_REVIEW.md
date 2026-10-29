# Console View Review

## Scope

Reviewed all six implemented console View classes and their Controller navigation. Changes stay within the approved Guest, Fan, Seller, Gate and Administrator Use Cases. Simulator execution is still planned for Stage 10; its existing profile menu was checked.

Architecture remains Controller -> View for I/O, and Controller -> Service -> Repository for business operations. Views do not read CSV files or decide booking/payment/check-in eligibility.

## Findings And Fixes

- Match schedules and stadium filter options are shown before users must select IDs.
- A signed-in Fan can book directly from search/filter results; Guest cannot.
- Payment, ticket details, booking details and refund actions do not ask for IDs when their lists are empty.
- Booking receipts and tickets display match names and section/seat labels alongside technical IDs.
- Online checkout explicitly says it is simulated. The user can decline payment and pay a pending booking later.
- Seller selects a listed customer before creating bookings, collecting cash or issuing tickets. Customer booking history reuses the existing booking query.
- Cash is recorded only after RECEIVED; Seller can issue tickets immediately after successful payment.
- Gate validates and displays a usable ticket before asking for check-in confirmation.
- Forms support cancellation at their initial selection/name prompt. Confirmation defaults to No.
- Delete, deactivate, revoke-role and close-sales actions ask for confirmation before changing data.
- Account forms no longer ask Fan users for a staff role. Staff edits preserve the role; role changes have their own commands.
- Staff role/status selection is numeric. Admin menus have domain headings and one command per line.
- Invalid numbers and dates have readable messages. Recoverable errors keep the current submenu open.
- Simulator profile/update menu numbers are consecutive; no unimplemented simulation action was added.

## Verification

- Compiled all Java source with javac --release 17 -encoding UTF-8.
- Compared all 97 source classes against 196 visible UML class boxes; both draw.io copies match. XML references, member bounds and newly introduced box overlaps were checked with no errors.
- Passed 50 scripted CLI smoke scenarios using isolated temporary CSV folders.
- Verified saved CSV states for cancellation, online/offline payments, ticket issue, check-in and refund approval/rejection.
- Unknown or wrong-match tickets never reached the check-in confirmation prompt.
- Checked blank/zero cancellation, malformed/duplicate seat IDs, sold-out and closed sales, empty data, ownership and wrong login routing.
- Smoke scripts and transcripts were kept outside the repository under /private/tmp; project data/ was not changed.
- This is console workflow smoke coverage, not a complete concurrency, security or crash-recovery test suite.

### Smoke Scenarios

- PASS: 01 main invalid and cancel
- PASS: 02 guest search details map
- PASS: 03 guest filters and validation
- PASS: 04 guest empty search
- PASS: 05 wrong login route
- PASS: 06 register and profile
- PASS: 07 bad password
- PASS: 08 empty fan actions
- PASS: 09 closed sales no seat input
- PASS: 10 admin bad inputs stay in submenus
- PASS: 11 admin fan forms cancel and filter
- PASS: 12 admin empty refund queue
- PASS: 13 open sales
- PASS: 14 fan seat map and cancel form
- PASS: 15 fan seat input errors
- PASS: 16 fan create pending and details
- PASS: 17 cancel pending default no then yes
- PASS: 18 quick book from browse
- PASS: 19 fan ticket history details
- PASS: 20 fan ownership
- PASS: 21 gate unknown no confirmation
- PASS: 22 gate bad match no confirmation
- PASS: 23 gate validate and cancel checkin
- PASS: 24 gate checkin and repeat
- PASS: 25 gate input cancel errors
- PASS: 26 seller empty customer selection
- PASS: 27 seller search create and cancel
- PASS: 28 seller customer bookings
- PASS: 29 seller create booking
- PASS: 30 seller offline payment cancel
- PASS: 31 seller offline payment issue
- PASS: 32 fan refund and empty reason
- PASS: 33 admin refund review cancel then approve
- PASS: 34 admin summary audit
- PASS: 35 failed online payment
- PASS: 36 admin crud stadium sections seats
- PASS: 37 admin match price sales crud
- PASS: 38 admin account crud
- PASS: 39 pending pay from menu
- PASS: 40 refund request for rejection
- PASS: 41 admin refund invalid decision then reject
- PASS: 42 seller customer booking mismatch
- PASS: 43 seller numeric errors and back
- PASS: 44 admin cancel forms and confirmations
- PASS: 45 simulator profile navigation
- PASS: 46 no matches any browse or booking
- PASS: 47 prepare sold out
- PASS: 48 sold out no seat input
- PASS: 49 seller closed sales no seat input
- PASS: 50 confirmed payment not offered again

## Visible Class Diagram Changes

Both draw.io copies contain the same visible rows. Existing page order, class colors, columns and relationship styles are retained. Class boxes are expanded only where signatures need more space. No Use Case was removed or added on Page 00. The older Mermaid working copies are not the canonical controller-first design.

The new Match/Seat/Section repository fields in FanService and TicketService only resolve readable labels. Repository entities and CSV schemas are unchanged. Relationship arrows remain selective to preserve the existing diagram style; the fields and constructors list the complete dependencies.

### Page 01 :: BookingView

```diff
- +readRefundRequest(fanId: Long): RefundRequestDto
+ +displayMatches(matches: List<MatchResponseDto>): void
+ +readRefundRequest(fanId: Long, bookingId: Long): RefundRequestDto
```

### Page 01 :: FanService

```diff
- +FanService(fanRepository: FanRepository, staffRepository: StaffRepository, ticketRepository: TicketRepository)
+ +FanService(fanRepository: FanRepository, staffRepository: StaffRepository, ticketRepository: TicketRepository, matchRepository: MatchRepository, seatRepository: SeatRepository, sectionRepository: SectionRepository)
+ -matchRepository: MatchRepository
+ -seatRepository: SeatRepository
+ -sectionRepository: SectionRepository
```

### Page 01 :: MainView

```diff
- +showMatchActionMenu(): int
+ +displayStadiumOptions(matches: List<MatchResponseDto>): void
+ +showMatchActionMenu(canBook: boolean): int
```

### Page 01 :: TicketResponseDto

```diff
- +TicketResponseDto(ticketId: Long, bookingId: Long, matchId: Long, seatId: Long, ticketCode: String, status: TicketStatus, checkedInAt: LocalDateTime)
+ +TicketResponseDto(ticketId: Long, bookingId: Long, matchId: Long, seatId: Long, ticketCode: String, status: TicketStatus, checkedInAt: LocalDateTime, matchName: String, seatLabel: String)
+ +getMatchName(): String
+ +getSeatLabel(): String
+ -matchName: String
+ -seatLabel: String
```

### Page 02 :: AdminManagementView

```diff
- +readAccountRequest(): AccountRequestDto
+ +confirm(action: String): boolean
+ +readAccountRequest(role: UserRole, create: boolean): AccountRequestDto
+ +readRole(allowAll: boolean): UserRole
+ +readStatus(): UserStatus
```

### Page 03 :: BookingController

```diff
+ +bookMatch(matchId: Long): void
+ +getBookingsForFan(fanId: Long): List<BookingResponseDto>
```

### Page 03 :: BookingResponseDto

```diff
- +BookingResponseDto(bookingId: Long, fanId: Long, matchId: Long, seatIds: List<Long>, totalAmount: BigDecimal, status: BookingStatus)
+ +BookingResponseDto(bookingId: Long, fanId: Long, matchId: Long, seatIds: List<Long>, totalAmount: BigDecimal, status: BookingStatus, fanName: String, matchName: String, seatLabels: List<String>)
+ +getFanName(): String
+ +getMatchName(): String
+ +getSeatLabels(): List<String>
+ -fanName: String
+ -matchName: String
+ -seatLabels: List<String>
```

### Page 03 :: BookingView

```diff
- +readRefundRequest(fanId: Long): RefundRequestDto
+ +displayMatches(matches: List<MatchResponseDto>): void
+ +readRefundRequest(fanId: Long, bookingId: Long): RefundRequestDto
```

### Page 03 :: FanService

```diff
- +FanService(fanRepository: FanRepository, staffRepository: StaffRepository, ticketRepository: TicketRepository)
+ +FanService(fanRepository: FanRepository, staffRepository: StaffRepository, ticketRepository: TicketRepository, matchRepository: MatchRepository, seatRepository: SeatRepository, sectionRepository: SectionRepository)
+ -matchRepository: MatchRepository
+ -seatRepository: SeatRepository
+ -sectionRepository: SectionRepository
```

### Page 03 :: SellerView

```diff
- +readBookingRequest(matchId: Long): BookingRequestDto
+ +confirmIssueTickets(): boolean
+ +readBookingRequest(fanId: Long, matchId: Long): BookingRequestDto
+ +readFanId(): Long
```

### Page 03 :: TicketResponseDto

```diff
- +TicketResponseDto(ticketId: Long, bookingId: Long, matchId: Long, seatId: Long, ticketCode: String, status: TicketStatus, checkedInAt: LocalDateTime)
+ +TicketResponseDto(ticketId: Long, bookingId: Long, matchId: Long, seatId: Long, ticketCode: String, status: TicketStatus, checkedInAt: LocalDateTime, matchName: String, seatLabel: String)
+ +getMatchName(): String
+ +getSeatLabel(): String
+ -matchName: String
+ -seatLabel: String
```

### Page 03 :: TicketService

```diff
- +TicketService(ticketRepository: TicketRepository, bookingRepository: BookingRepository)
+ +TicketService(ticketRepository: TicketRepository, bookingRepository: BookingRepository, matchRepository: MatchRepository, seatRepository: SeatRepository, sectionRepository: SectionRepository)
+ -matchRepository: MatchRepository
+ -seatRepository: SeatRepository
+ -sectionRepository: SectionRepository
```
