# Booking And Simulation Design

## Nguyen tac cot loi

Mot booking thanh cong khong duoc dua tren seat status da hien tren UI. He thong phai re-check trong transaction logic ngay truoc khi lock/confirm.

Invariant quan trong nhat:

```text
For each matchId + seatId, there is at most one VALID ticket.
```

## Seat Lifecycle

```text
AVAILABLE -> LOCKED -> BOOKED
LOCKED -> AVAILABLE
BOOKED -> CANCELLED/REFUNDED optional through ticket/booking status
```

`LOCKED -> AVAILABLE` xay ra khi:

- Payment failed.
- Payment cancelled.
- Payment expired.
- Booking validation failed after partial lock.
- Lock timeout.
- System detects stale lock.

## Booking Flow - Customer Checkout

```text
createBooking(request)
  validateRequest(request)
  calculateBookingTotal(request)
  executeBookingCore(request)
    reCheckMatchAndSeatAvailability()
    applySynchronizationStrategy()
    reserveSeatStateForCheckout()
  holdSeats(expiresAt)
  createPendingBooking(expiresAt)
  createPayment(PENDING)
  processPayment()
  if payment success:
    confirmBooking()
    markSeatsBooked()
    createTickets()
    recordTransaction(SUCCESS)
    sendBookingConfirmation optional
  else:
    releaseSeats()
    markBookingFailed()
    recordTransaction(FAILED)
```

Important distinction:

- `Create Booking` cua Fan/Seller la checkout flow, co payment.
- `Execute Booking Core` la loi tranh chap ghe: check availability, apply synchronization va reserve/commit seat state.
- `Hold Seats` la buoc rieng cua checkout flow cho Fan/Seller, gan `bookingId` va `expiresAt` cho ghe dang duoc giu tam thoi.
- Simulator chi goi `Execute Booking Core`, khong goi full checkout/payment flow.

## Execute Booking Core

```text
executeBookingCore(request)
  checkSeatAvailability(matchId, seatIds)
  checkNoValidTicket(matchId, seatIds)
  applySynchronizationStrategy(mechanism):
    updateSeatStateSafely()
    reserveOrCommitSeatStateDependingOnMode()
  return SeatBookingAttemptResult
```

Fan/Seller mode:

- Core thuong update `AVAILABLE -> LOCKED`.
- Sau payment/offline confirmation moi `LOCKED -> BOOKED`.
- `Hold Seats` gan hold voi booking/session va expiry, khong phai trach nhiem payment.

Simulator mode:

- Core co the update truc tiep theo attempt rule hoac tao simulated booking/ticket tuy cach code.
- Simulator co the bo qua hold/payment va chi chay contention core.
- Khong simulate online payment cho tung thread.
- Ket qua chinh can do la success/failure/conflict/double booking.

## Seat Hold And Expiry

```text
holdSeats(bookingId, seatIds, expiresAt)
  AVAILABLE -> LOCKED

expirePendingBooking()
  find Booking where status=PENDING and expiresAt < now
  mark Booking EXPIRED
  releaseSeats()
  LOCKED -> AVAILABLE
```

Khuyen nghi payment window demo: 5 phut tren tai lieu, khi test co the dat 5-10 giay de de demo.

## Validation Rules

- Fan ton tai va active.
- Match ton tai va open for sale.
- Seat list khong rong.
- Seat count <= 4.
- Khong duplicate seat trong cung request.
- Seat thuoc dung stadium/section cua match.
- Seat khong maintenance.
- `matchId + seatId` chua co valid ticket.
- Neu optimistic locking: version hien tai phai khop version da doc.
- Neu co `idempotencyKey`, cung key voi request da thanh cong khong tao booking/ticket moi.
- Gia ve duoc tinh thanh snapshot truoc khi confirm.

## Synchronization Mechanisms

### NO_LOCK

Muc dich:

- Unsafe baseline.
- Dung de chung minh race condition co the gay double booking.

Rui ro:

- Nhieu thread cung thay seat available.
- Nhieu thread cung tao ticket valid cho cung match/seat.

Khong dung cho production.

### SYNCHRONIZED

Muc dich:

- Dong bo trong cung JVM/process.

Cach lam:

- Dung `synchronized` tren critical section cua booking.
- Hoac lock object theo `matchId + seatId`.

Uu diem:

- Don gian.
- Tot cho demo Java concurrency.

Han che:

- Khong bao ve neu co nhieu process cung ghi CSV.

### FILE_LOCK

Muc dich:

- Mo phong lock o muc file/storage.

Cach lam:

- Dung Java NIO `FileChannel.lock()`.
- Lock quanh doan doc-check-write critical.

Uu diem:

- Gan voi CSV persistence.
- Co y nghia neu nhieu process cung truy cap file.

Han che:

- Throughput thap hon.
- Can release lock trong `finally`.

### OPTIMISTIC

Muc dich:

- Cho phep concurrency cao, conflict thi retry/fail.

Cach lam:

1. Doc seat voi `version`.
2. Validate seat available.
3. Khi update, chi thanh cong neu version chua doi.
4. Neu version da doi, throw `OptimisticLockException`.
5. Tang version sau moi lan update status.

Uu diem:

- Tot khi conflict khong qua cao.

Han che:

- Can implementation can than khi ghi CSV.
- High contention co the nhieu conflict.

## Simulator Scenarios

### High Contention

```text
1000 fan threads -> 1 target seat
```

Expected:

- Safe mechanisms: success <= 1 cho seat do.
- NO_LOCK co the tao double booking neu timing gap du lon.

### Medium Contention

```text
1000 fan threads -> 100 target seats
```

Expected:

- Success toi da bang so seat target neu moi booking 1 seat.
- Conflict co nhung it hon high contention.

### Low Contention

```text
1000 fan threads -> many different seats
```

Expected:

- Throughput cao hon.
- Conflict thap.

## Required Java Concurrency Utilities

- `ExecutorService`: tao va quan ly pool task.
- `CountDownLatch`: cho cac thread start gan nhu cung luc.

Flow:

```text
prepare tasks
readyLatch.countDown when task ready
startLatch.await in each task
main waits readyLatch
main startLatch.countDown
collect Future results
shutdown executor
```

Simulator execution rule:

```text
Execute Concurrent Booking
  -> Execute Booking Core
  -> Apply Synchronization Strategy
  -> Record attempt result
```

Khong lam:

```text
Execute Concurrent Booking
  -> Create Booking
  -> Make Online Payment
```

Ly do: simulator do tranh chap ghe, khong phai do payment checkout. Neu de 1000 threads di qua payment flow thi use case va metric bi sai trong tam LAB.

## Metrics

| Metric | Cong thuc / y nghia |
|---|---|
| totalAttempts | So booking tasks da chay |
| successfulBookings | So booking thanh cong |
| failedBookings | So booking fail |
| conflictCount | Optimistic conflict/seat conflict |
| doubleBookingCount | So pair/record duplicate valid ticket |
| executionTimeMs | End - start |
| throughput | successfulBookings / seconds |
| doubleBookingRate | doubleBookingCount / totalAttempts * 100 |

## Double Booking Detection

Sau simulation, scan tickets:

```text
group by matchId + seatId where ticket.status == VALID
if group size > 1:
  doubleBookingCount += group size - 1
```

Can scan ca transaction neu muon:

- Transaction success nhung khong co ticket.
- Ticket valid nhung transaction failed.

## Result Table Mau

| Mechanism | Threads | Scenario | Success | Failed | Conflict | Double Booking | Time ms | Throughput |
|---|---:|---|---:|---:|---:|---:|---:|---:|
| NO_LOCK | 1000 | HIGH | TBD | TBD | TBD | TBD | TBD | TBD |
| SYNCHRONIZED | 1000 | HIGH | TBD | TBD | TBD | 0 | TBD | TBD |
| FILE_LOCK | 1000 | HIGH | TBD | TBD | TBD | 0 | TBD | TBD |
| OPTIMISTIC | 1000 | HIGH | TBD | TBD | TBD | 0 | TBD | TBD |

## Research Conclusion Can Viet Trong Report

`NO_LOCK` co throughput co the cao nhung khong dam bao consistency.

`SYNCHRONIZED` de cai dat va an toan trong mot JVM nhung giam concurrency.

`FILE_LOCK` gan voi CSV/file persistence va an toan hon khi ghi file, nhung chi phi lock cao.

`OPTIMISTIC` can versioning, cho concurrency tot trong low/medium contention, nhung high contention se co nhieu conflict.
