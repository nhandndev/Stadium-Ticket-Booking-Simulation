# Stage 08-09 Class Diagram Changes

The two `ClassDiagram.drawio` copies are synchronized with the implemented Java classes. Rows below describe visible changes; class boxes retain their colors, columns and relationship style.

- Page 00: existing actors and booking/cancellation/refund/Seller/Gate Use Cases are retained.
- Page 04: Simulation stays planned for Stage 10. Only shared booking contracts were synchronized.
- `MatchSeatRepository.updateWithoutLock`, `updateSynchronized`, `updateWithFileLock` and `updateIfVersionMatches` remain planned Stage 10 methods; no placeholder code was added.
- Enum Java files live in each module's `enums` package.

## Implemented Flow

`MainController -> domain Controller -> View (read/print) and Service -> Repository -> CSV`

`PENDING -> successful payment and seat commit -> CONFIRMED -> issued tickets -> CHECKED_IN`

Unpaid PENDING bookings can be cancelled directly. Paid, unused future-match bookings need Admin refund approval; approval cancels tickets/booking and releases the seats. Payment and refund are simulated. Checkout repositories replace each CSV atomically, but do not form an ACID transaction across files; interrupted checkout reports reconciliation rather than silently charging again.

## Verification

- All 97 Java source files compile with JDK 17-compatible syntax.
- Syntax-tree inspection matches fields and public methods against 196 visible class boxes. No implemented source class is missing from the diagram.
- Both draw.io copies parse as seven-page XML documents, with valid IDs/parents/edge endpoints. Added rows fit their class bounds; the original multiple-column layouts have no new box overlaps.
- Guest search and filter each keep the result action menu open; an ID outside the current result list is rejected.
- Console runs on temporary CSV data covered Fan checkout/history/details, unpaid cancellation, own refund status, Admin approval, Seller registration/search/cash/issuance, and Gate validation/check-in.
- Negative paths covered another Fan's booking/ticket access, a conflicting two-seat payment without a partial seat sale, mock payment failure, blank refund review notes, used-ticket refunds, wrong-match tickets and repeat check-in.
- Issuing the same Seller booking twice returned the same ticket. Restarted application processes read the stored states correctly.
- The final temporary dataset contained six bookings, five payments with five distinct booking IDs, and five tickets with five distinct codes. Its transactions recorded three successes, one conflict and one mock failure. The refunded seats were released, while the conflicting/failed checkout left the other seats available.
- Fan/match deletion with booking history, deactivating a booked seat, and revoking the last active Admin were rejected through the console.

No test suite or demo fixture was added to the project source tree. Simulation remains outside this completion scope. Normal checkout synchronization applies within one JVM; multi-process checkout is not claimed.

## Pages 02 :: AccountAdminService

```diff
- +AccountAdminService(fanRepository: FanRepository, staffRepository: StaffRepository)
+ +AccountAdminService(fanRepository: FanRepository, staffRepository: StaffRepository, bookingRepository: BookingRepository)
+ -bookingRepository: BookingRepository
```

## Pages 02 :: AdminController

```diff
- +AdminController(adminService: AdminService, adminManagementView: AdminManagementView, stadiumController: StadiumController, matchController: MatchController, accountAdminController: AccountAdminController, refundController: RefundController , auditLogRepository : AuditLogRepository)
- +generateCsvDataset(recordCount: int): void
+ +AdminController(adminService: AdminService, adminManagementView: AdminManagementView, stadiumController: StadiumController, matchController: MatchController, accountAdminController: AccountAdminController, refundController: RefundController, authController: AuthController, auditLogRepository: AuditLogRepository)
+ -authController: AuthController
```

## Pages 02 :: AdminManagementView

```diff
- +readRefundReviewRequest(): RefundReviewRequestDto
- +showDataGenerationMenu(): int
+ +readRefundReviewRequest(adminId: Long): RefundReviewRequestDto
```

## Pages 02 :: AdminService

```diff
- +AdminService(dataGenerator: DataGenerator, fanRepository: FanRepository, staffRepository: StaffRepository, stadiumRepository: StadiumRepository, matchRepository: MatchRepository, bookingRepository: BookingRepository, ticketRepository: TicketRepository, auditLogRepository: AuditLogRepository)
- +generateCsvDataset(recordCount: int): void
- -dataGenerator: DataGenerator
+ +AdminService(fanRepository: FanRepository, staffRepository: StaffRepository, stadiumRepository: StadiumRepository, matchRepository: MatchRepository, auditLogRepository: AuditLogRepository, bookingRepository: BookingRepository, ticketRepository: TicketRepository)
```

## Pages 02, 05 :: AuditLogRepository

```diff
- +formatLine(entity: AuditLog): String
+ +formatLine(log: AuditLog): String
```

## Pages 05 :: BaseEntity

```diff
- +setId(id : Long): void
+ +setId(id: Long): void
```

## Pages 03 :: BookingController

```diff
- +BookingController(bookingService: BookingService, bookingView: BookingView, sellerView: SellerView, seatMapView: SeatMapView, browseController: BrowseController, fanController: FanController, refundController: RefundController, ticketController: TicketController)
+ +BookingController(bookingService: BookingService, authController: AuthController, bookingView: BookingView, sellerView: SellerView, seatMapView: SeatMapView, browseController: BrowseController, fanController: FanController, refundController: RefundController, ticketController: TicketController)
+ +cancelBooking(bookingId: Long): BookingResponseDto
+ -authController: AuthController
```

## Pages 03 :: BookingService

```diff
- +BookingService(bookingRepository: BookingRepository, ticketRepository: TicketRepository, paymentRepository: PaymentRepository, transactionRepository: TransactionRepository, seatRepository: SeatRepository, ticketPriceRepository: TicketPriceRepository, strategyFactory: StrategyFactory, mockPaymentService: MockPaymentService)
+ +BookingService(bookingRepository: BookingRepository, ticketRepository: TicketRepository, paymentRepository: PaymentRepository, transactionRepository: TransactionRepository, seatRepository: SeatRepository, ticketPriceRepository: TicketPriceRepository, strategyFactory: StrategyFactory, mockPaymentService: MockPaymentService, fanRepository: FanRepository, matchRepository: MatchRepository, matchSeatRepository: MatchSeatRepository, sectionRepository: SectionRepository)
+ +cancelBooking(bookingId: Long): BookingResponseDto
+ -fanRepository: FanRepository
+ -matchRepository: MatchRepository
+ -matchSeatRepository: MatchSeatRepository
+ -sectionRepository: SectionRepository
```

## Pages 01, 03 :: BookingView

```diff
- +readBookingRequest(): BookingRequestDto
- +readPaymentRequest(): PaymentRequestDto
- +readRefundRequest(): RefundRequestDto
+ +confirmCancellation(): boolean
+ +displayMatchDetails(match: MatchResponseDto): void
+ +displayMessage(message: String): void
+ +displayPaymentResponse(response: PaymentResponseDto): void
+ +displayRefunds(refunds: List<RefundResponseDto>): void
+ +readBookingId(): Long
+ +readBookingRequest(fanId: Long, matchId: Long): BookingRequestDto
+ +readMatchId(): Long
+ +readPaymentRequest(booking: BookingResponseDto): PaymentRequestDto
+ +readRefundRequest(fanId: Long): RefundRequestDto
```

## Pages 01, 03 :: BrowseService

```diff
- +BrowseService(matchRepository: MatchRepository, matchSeatRepository: MatchSeatRepository, seatRepository: SeatRepository)
+ +BrowseService(matchRepository: MatchRepository, matchSeatRepository: MatchSeatRepository, seatRepository: SeatRepository, stadiumRepository: StadiumRepository, sectionRepository: SectionRepository, ticketPriceRepository: TicketPriceRepository)
+ -sectionRepository: SectionRepository
+ -stadiumRepository: StadiumRepository
+ -ticketPriceRepository: TicketPriceRepository
```

## Pages 01, 03 :: FanController

```diff
- +FanController(fanService: FanService)
+ +FanController(fanService: FanService, authController: AuthController)
+ -authController: AuthController
```

## Pages 01, 03 :: FanService

```diff
- +FanService(fanRepository: FanRepository, bookingRepository: BookingRepository, ticketRepository: TicketRepository)
- -bookingRepository: BookingRepository
+ +FanService(fanRepository: FanRepository, staffRepository: StaffRepository, ticketRepository: TicketRepository)
+ -staffRepository: StaffRepository
```

## Pages 03 :: GateView

```diff
+ +confirmCheckIn(): boolean
```

## Pages 01 :: MainController

```diff
- +MainController(mainView: MainView, seatMapView: SeatMapView, authController: AuthController, fanController: FanController, browseController: BrowseController, bookingController: BookingController, ticketController: TicketController, adminController: AdminController, simulationController: SimulationController)
- -simulationController: SimulationController
+ +MainController(mainView: MainView, seatMapView: SeatMapView, browseController: BrowseController, authController: AuthController, fanController: FanController, adminController: AdminController, bookingController: BookingController, ticketController: TicketController)
+ +routeAuthenticatedUser(user: UserResponseDto): void
```

## Pages 01 :: MainView

```diff
+ +readProfileUpdateRequest(): ProfileUpdateRequestDto
+ -dateFormatter: DateTimeFormatter
```

## Pages 01, 02, 03 :: MatchResponseDto

```diff
+ +MatchResponseDto(id: Long, stadiumId: Long, homeTeam: String, awayTeam: String, startTime: LocalDateTime, saleStatus: SaleStatus, stadiumName: String)
+ +getStadiumName(): String
+ -stadiumName: String
```

## Pages 01, 03 :: MatchSeatResponseDto

```diff
- +MatchSeatResponseDto(id: Long, matchId: Long, seatId: Long, sectionId: Long, status: SeatStatus, version: int)
+ +MatchSeatResponseDto(id: Long, matchId: Long, seatId: Long, sectionId: Long, status: SeatStatus, version: int, sectionName: String, seatLabel: String, price: BigDecimal, active: boolean)
+ +getPrice(): BigDecimal
+ +getSeatLabel(): String
+ +getSectionName(): String
+ +isActive(): boolean
+ -active: boolean
+ -price: BigDecimal
+ -seatLabel: String
+ -sectionName: String
```

## Pages 02 :: MatchService

```diff
- +MatchService(matchRepository: MatchRepository, ticketPriceRepository: TicketPriceRepository, matchSeatRepository: MatchSeatRepository)
+ +MatchService(matchRepository: MatchRepository, ticketPriceRepository: TicketPriceRepository, matchSeatRepository: MatchSeatRepository, bookingRepository: BookingRepository, stadiumRepository: StadiumRepository, sectionRepository: SectionRepository, seatRepository: SeatRepository)
+ -bookingRepository: BookingRepository
+ -seatRepository: SeatRepository
+ -sectionRepository: SectionRepository
+ -stadiumRepository: StadiumRepository
```

## Pages 02, 03 :: RefundController

```diff
- +RefundController(refundService: RefundService)
+ +RefundController(refundService: RefundService, authController: AuthController)
+ +getMyRefunds(fanId: Long): List<RefundResponseDto>
+ +getPendingRefunds(): List<RefundRequest>
+ -authController: AuthController
```

## Pages 02, 03 :: RefundService

```diff
- +RefundService(refundRepository: RefundRepository, bookingRepository: BookingRepository)
+ +RefundService(refundRepository: RefundRepository, bookingRepository: BookingRepository, paymentRepository: PaymentRepository, ticketRepository: TicketRepository, matchRepository: MatchRepository, matchSeatRepository: MatchSeatRepository)
+ +getMyRefunds(fanId: Long): List<RefundResponseDto>
+ +getPendingRefunds(): List<RefundRequest>
+ -matchRepository: MatchRepository
+ -matchSeatRepository: MatchSeatRepository
+ -paymentRepository: PaymentRepository
+ -ticketRepository: TicketRepository
```

## Pages 03 :: SellerView

```diff
- +displayBookingResponse(response: BookingResponseDto): void
- +displayPaymentResponse(response: PaymentResponseDto): void
- +readBookingRequest(): BookingRequestDto
- +readPaymentRequest(): PaymentRequestDto
+ +displayBookingResponse(booking: BookingResponseDto): void
+ +displayCreatedFan(fan: UserResponseDto): void
+ +displayMessage(message: String): void
+ +displayPaymentResponse(payment: PaymentResponseDto): void
+ +readBookingRequest(matchId: Long): BookingRequestDto
+ +readMatchId(): Long
+ +readPaymentRequest(booking: BookingResponseDto): PaymentRequestDto
```

## Pages 02 :: StadiumService

```diff
- +StadiumService(stadiumRepository: StadiumRepository, sectionRepository: SectionRepository, seatRepository: SeatRepository)
+ +StadiumService(stadiumRepository: StadiumRepository, sectionRepository: SectionRepository, seatRepository: SeatRepository, matchRepository: MatchRepository, matchSeatRepository: MatchSeatRepository, ticketPriceRepository: TicketPriceRepository)
+ -matchRepository: MatchRepository
+ -matchSeatRepository: MatchSeatRepository
+ -ticketPriceRepository: TicketPriceRepository
```

## Pages 03 :: TicketController

```diff
- +TicketController(ticketService: TicketService, gateView: GateView)
+ +TicketController(ticketService: TicketService, authController: AuthController, bookingRepository: BookingRepository, gateView: GateView)
+ -authController: AuthController
+ -bookingRepository: BookingRepository
```

## Pages 01, 02, 03, 05 :: TicketRepository

```diff
- +TicketRepository(filePath: Path)
+ +TicketRepository(filePath: Path, bookingRepository: BookingRepository)
+ -bookingRepository: BookingRepository
```

## Pages 03 :: TicketService

```diff
- +TicketService(ticketRepository: TicketRepository)
+ +TicketService(ticketRepository: TicketRepository, bookingRepository: BookingRepository)
+ -bookingRepository: BookingRepository
```
