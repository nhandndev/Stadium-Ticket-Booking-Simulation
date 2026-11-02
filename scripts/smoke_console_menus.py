"""Run isolated console-navigation checks; never read or write project data/."""

import csv
from pathlib import Path
import subprocess
import tempfile

ROOT = Path(__file__).resolve().parents[1]


def seed(folder):
    fixtures = {
        "fans.csv": [
            [1, "Demo Fan", "fan@example.invalid", "DemoPass123", "0900000001", "FAN", "ACTIVE"],
            [2, "Other Fan", "other@example.invalid", "DemoPass123", "0900000002", "FAN", "ACTIVE"],
        ],
        "staff.csv": [
            [1, "Demo Admin", "admin@example.invalid", "DemoPass123", "0900000011", "ADMIN", "ACTIVE"],
            [2, "Demo Seller", "seller@example.invalid", "DemoPass123", "0900000012", "SELLER", "ACTIVE"],
            [3, "Demo Gate", "gate@example.invalid", "DemoPass123", "0900000013", "GATE_STAFF", "ACTIVE"],
        ],
        "stadiums.csv": [[1, "Demo Stadium", "Hanoi"]],
        "sections.csv": [[1, 1, "North Stand"]],
        "seats.csv": [[i, 1, "A", i, "true"] for i in range(1, 9)],
        "matches.csv": [[1, 1, "Vietnam", "Thailand", "2035-11-15T19:00", "CLOSED"]],
        "ticket_prices.csv": [[1, 1, 100000]],
    }
    for filename, values in fixtures.items():
        with (folder / filename).open("w", newline="", encoding="utf-8") as file:
            csv.writer(file, lineterminator="\n").writerows(values)


def login(role):
    return [2 if role in ("fan", "other") else 3, role + "@example.invalid", "DemoPass123"]


def session(role, actions):
    return login(role) + [2] + actions + [0, 0, 0]


def venue(group, actions):
    return [1, group] + actions + [0, 0]


def match(group, actions):
    return [2, group] + actions + [0, 0]


def group(number, actions):
    return [number] + actions + [0]


def read_csv(folder, filename):
    path = folder / filename
    if not path.exists():
        return []
    with path.open(encoding="utf-8", newline="") as file:
        return list(csv.reader(file))


def main():
    with tempfile.TemporaryDirectory(prefix="stadium-menu-smoke-") as temporary:
        folder = Path(temporary)
        build = folder / "classes"
        data = folder / "csv"
        data.mkdir()
        seed(data)
        subprocess.run(
            ["javac", "--release", "17", "-encoding", "UTF-8", "-d", str(build)]
            + [str(p) for p in sorted((ROOT / "src").rglob("*.java"))],
            check=True,
        )
        count = 0

        def run(name, actions, required=(), forbidden=(), payment_fails=False):
            nonlocal count
            command = ["java", "-cp", str(build), "app.Main", str(data)]
            if payment_fails:
                command.append("--payment-fails")
            result = subprocess.run(command, input="\n".join(map(str, actions)) + "\n",
                                    text=True, capture_output=True, timeout=15)
            output = result.stdout + result.stderr
            assert result.returncode == 0 and "Goodbye!" in output, (name, output)
            for text in ("Exception in thread", "For input string", "NullPointerException") + tuple(forbidden):
                assert text not in output, (name, text, output)
            for text in required:
                assert text in output, (name, text, output)
            count += 1
            print("PASS", name)
            return output

        run("Guest browsing remains available", [1, 1, "Vietnam", 1, 1, 0, 0, 0],
            ["MATCH DETAILS #1"])
        run("Wrong login route remains blocked", [2, "admin@example.invalid", "DemoPass123", 0],
            ["Use Login As Staff"])
        run("Admin navigation and one-level back",
            session("admin", [1, 1, 0, 2, 0, 3, 0, 0, 2, 1, 0, 2, 0, 3, 0, 0, 3, 1, 0, 2, 9, 0, 0, 0, 5, 0]),
            ["ADMIN > VENUES > STADIUMS", "ADMIN > VENUES > SECTIONS", "ADMIN > VENUES > SEATS",
             "ADMIN > MATCHES > MATCH MANAGEMENT", "ADMIN > MATCHES > TICKET PRICING",
             "ADMIN > MATCHES > TICKET SALES", "STAFF > ROLES", "REPORTS AND AUDIT LOGS"])
        run("Admin invalid input stays in section",
            session("admin", venue(1, ["oops", 99, 0, 0]) + [3, 2, 9, "bad", 99, 0, 0, 0]),
            ["Please enter a valid number.", "Invalid choice."])
        run("Stadium CRUD", session("admin", venue(1, [
            1, "Temporary Stadium", "HCM", 2, 3, 2, 4, 2, "Updated Stadium", "HCM", 5, 2, "n",
            5, 2, "y"])), ["Temporary Stadium", "Updated Stadium", "Operation completed."])
        assert len(read_csv(data, "stadiums.csv")) == 1
        run("Section CRUD", session("admin", venue(2, [
            1, 1, "Temporary Section", 2, 1, 3, 2, 4, 2, "Updated Section", 5, 2, "y"])),
            ["Temporary Section", "Updated Section", "Operation completed."])
        assert len(read_csv(data, "sections.csv")) == 1
        run("Seat CRUD and activation", session("admin", venue(3, [
            1, 1, "B", 1, 2, 1, 3, 9, 4, 9, "B", 2, 7, 9, "y", 6, 9, 5, 9, "y"])),
            ["Seat 2", "Active: false", "Active: true", "Operation completed."])
        assert len(read_csv(data, "seats.csv")) == 8
        run("Match CRUD", session("admin", match(1, [
            1, 1, "Home", "Away", "2036-01-01T19:00", 2, 3, 2,
            4, 2, 1, "Updated Home", "Updated Away", "2036-01-02T19:00", 5, 2, "y"])),
            ["Updated Home vs Updated Away", "Operation completed."])
        run("Price CRUD", session("admin", match(2, [
            2, 1, 3, 1, 1, 4, 1, 1, 120000, 5, 1, 1, "y", 1, 1, 1, 100000])),
            ["120000 VND", "Operation completed."])
        run("Sales open close status", session("admin", match(3, [
            1, 1, 3, 1, 2, 1, "y", 3, 1, 1, 1])), ["OPEN", "CLOSED"])
        assert len(read_csv(data, "match_seats.csv")) == 8
        run("Fan all submenus and empty lists",
            session("fan", group(2, [1, 2, 3]) + group(3, [1, 2]) + [4, 1, 2, 3, 0]),
            ["No bookings yet.", "No tickets yet.", "No bookings are waiting", "No refund requests"],
            ["Booking ID:", "Ticket ID:"])
        run("Fan invalid input and cancellation stay in group",
            session("fan", group(1, ["bad", 99, 1, 0, 2, 1, 0])),
            ["Please enter a valid number.", "Invalid choice.", "Booking cancelled."])
        run("Fan pending booking", session("fan", group(1, [2, 1, 1, "n"])),
            ["This booking remains pending", "North Stand A1"])
        assert read_csv(data, "bookings.csv")[0][5] == "PENDING"
        run("Fan booking list details and pay", session("fan", group(2, [1, 2, 1, 3, 1, "y"])),
            ["Status SUCCESS", "Customer: Demo Fan", "North Stand A1"])
        run("Fan ticket list and details", session("fan", group(3, [1, 2, 1])),
            ["Not checked in", "North Stand A1"])
        run("Seller navigation invalid input and empty customer",
            session("seller", group(1, ["bad", 99, 1, "nobody"]) + group(2, [3, "nobody"]) + group(3, [1, "nobody"])),
            ["Please enter a valid number.", "Invalid choice.", "No Fan found."])
        run("Seller customer search and registration", session("seller", group(1, [
            1, "", 2, "Counter Customer", "counter@example.invalid", "Counter123", "0901111111"])),
            ["Demo Fan", "Created Fan #3"])
        run("Seller create and look up customer booking", session("seller", group(2, [
            1, 1, 2, "counter", 3, 1, "2,3", 3, "counter", 3])),
            ["Booking #2", "North Stand A2", "North Stand A3"])
        run("Seller cash and manual ticket issue", session("seller", group(3, [
            1, "counter", 3, 2, "RECEIVED", "n", 2, "counter", 3, 2])),
            ["OFFLINE_CASH", "Status SUCCESS", "Code "])
        assert len(read_csv(data, "tickets.csv")) == 3
        run("Seller issue retry remains idempotent", session("seller", group(3, [2, "counter", 3, 2])),
            ["Code "])
        assert len(read_csv(data, "tickets.csv")) == 3
        run("Unpaid cancellation", session("fan", group(1, [2, 1, 4, "n"]) + [4, 2, 3, "y", 0]),
            ["CANCELLED"])
        run("Refund request", session("fan", [4, 1, 1, "Cannot attend", 3, 0]),
            ["REQUESTED"])
        run("Admin refund review", session("admin", [4, 1, 1, "y", "Approved by policy"]),
            ["APPROVED"])
        assert read_csv(data, "refunds.csv")[0][4] == "APPROVED"
        run("Fan and Staff account groups", session("admin", [
            3, 1, 2, 5, 1, 0, 2, 2, 5, 1, 1, 9, 2, 2, 2, 3, 2, "y", 1, 2, 1, 0, 0, 0]),
            ["Role: GATE_STAFF", "Role: UNASSIGNED", "Role: SELLER"])
        run("Reports grouped separately", session("admin", [5, 1, 2, 0]),
            ["SYSTEM SUMMARY", "AUDIT LOGS", "REVIEW_REFUND"])
        code = read_csv(data, "tickets.csv")[1][4]
        run("Gate validation cancellation and check-in", session("gate", [
            1, code, 1, 2, code, 1, "n", 2, code, 1, "y", 2, code, 1]),
            ["Check-in cancelled.", "CHECKED_IN", "already"])
        run("Online failure remains handled", session("fan", group(1, [2, 1, 5, "y"])),
            ["Payment failed."], payment_fails=True)
        print(f"{count} console menu scenarios passed. Project data/ was not touched.")


if __name__ == "__main__":
    main()
