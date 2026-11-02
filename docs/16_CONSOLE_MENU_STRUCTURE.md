# Console Menu Structure

Updated: 2026-11-01. Replaces the flat operation menus described in earlier demo notes.

## Navigation Rules

- Keep the existing main menu, login routes and role dashboards. The Fan dashboard entry 2 is now named Tickets and bookings.
- Group operations by user task, not Controller or Java class name.
- Each submenu numbers its own actions from 1. `0` returns exactly one level; it does not log out or exit from an operation submenu.
- Repeat the active submenu after a completed action or recoverable input error.
- Show the current location in the console heading and name the destination of Back.
- Keep Controller-first navigation. View prints/reads; Controller validates menu choices and calls the existing use-case handlers.
- No new business feature, framework, CSV schema or role was introduced.

## Fan

From Fan dashboard: `2. Tickets and bookings`.

```text
Tickets and bookings
  1. Book tickets
       1. View seat map and prices
       2. Create booking
  2. My bookings and payments
       1. Booking history
       2. Booking details
       3. Pay pending booking
  3. My tickets
       1. My tickets / Ticket history
       2. Ticket details
  4. Cancellations and refunds
       1. Request refund for paid booking
       2. Cancel unpaid pending booking
       3. My refund requests
```

Immediate checkout after creating a booking and booking from browsing results still work. Declining payment points the user to My bookings and payments. Ownership, payment and availability checks are unchanged.

## Seller

From Seller dashboard: `2. Sales counter`.

```text
Sales counter
  1. Customers
       1. Search / List customers
       2. Create Fan account
  2. Customer bookings
       1. View seat map and prices
       2. Create booking for customer
       3. Customer booking history
  3. Payments and ticket issue
       1. Accept cash payment
       2. Issue tickets for paid booking
```

Customer selection, RECEIVED confirmation and optional immediate ticket issue are retained. Calling Issue tickets again does not create duplicate tickets.

## Administrator

From Admin dashboard: `2. Manage system operations`.

```text
System operations
  1. Venues and seating
       1. Stadiums: Create / List / Details / Update / Delete
       2. Sections: Create / List by stadium / Details / Update / Delete
       3. Seats: Create / List by section / Details / Update / Delete / Activate / Deactivate
  2. Matches, pricing and sales
       1. Matches: Create / List / Details / Update / Delete
       2. Ticket pricing: Create / List by match / Details / Update / Delete
       3. Ticket sales: Open / Close / Status
  3. Accounts and roles
       1. Fan accounts: Create / List / Details / Search / Filter / Update / Activate / Deactivate / Delete
       2. Staff accounts: Create / List / Details / Search / Filter / Update / Activate / Deactivate
            9. Staff roles
                 1. Assign role
                 2. Change role
                 3. Revoke role
  4. Refund reviews
       1. Review a request
  5. Reports and audit logs
       1. System summary
       2. Audit logs
```

Slash-separated commands above are numbered 1 onward in their listed order. Each real console screen displays one action per line. Fan and Staff account management retain their existing action numbers 1-8; Fan Delete is 9, while Staff roles is 9 in Staff management.

The original 17-action venue list is split into three object menus. Match maintenance, pricing and sales are separated so that editing a match is not confused with opening sales. Role changes are separate from account profile edits. Monitoring is separate from data maintenance.

## Unchanged Screens

Guest browsing, search-result actions, Gate validation/check-in and role profile screens remain small and retain their numbers. Simulator execution is not implemented by this change.

## Diagram Additions

Both canonical draw.io copies are synchronized. No new class or relationship is required; the additional methods only print navigation menus.

Pages 01 and 03 :: BookingView:

```diff
+ +showBookingActionsMenu(group: int): int
```

Page 02 :: AdminManagementView:

```diff
+ +showVenueActionsMenu(group: int): int
+ +showMatchActionsMenu(group: int): int
+ +showAccountsMenu(): int
+ +showReportsMenu(): int
+ +showStaffRoleMenu(): int
```

Page 03 :: SellerView:

```diff
+ +showSellerActionsMenu(group: int): int
```

The class boxes expand where necessary; colors, columns, class IDs and relationship styles are retained. Private Controller helpers implement the submenu loops without adding public business APIs.

## Verification

```bash
python3 scripts/smoke_console_menus.py
```

The script compiles Java 17 into a temporary directory and runs 27 CLI scenarios with independent fixture CSV data. It checks submenu navigation, back, invalid input, venue/match/pricing CRUD, account role operations, Fan booking/payment/cancellation/tickets, Seller cash/issue/retry, refund approval, monitoring and Gate check-in. It asserts selected persisted CSV outcomes; it does not touch project `data/` or the IDE output folder.

The two draw.io copies were checked for identical content, valid IDs/references, member bounds and newly introduced box overlaps. This does not replace manual visual inspection in the draw.io editor or a full concurrency/security audit.

The Vietnamese [feature demo script](Thuyet_Trinh/14_KICH_BAN_DEMO_TUNG_FEATURE_HIEN_TAI.md) now uses the new menu paths.

For every individual keystroke and actual captured console response, use the
[142-flow transcript](Thuyet_Trinh/15_DEMO_TUNG_PHIM_VA_KET_QUA_CONSOLE.md).
