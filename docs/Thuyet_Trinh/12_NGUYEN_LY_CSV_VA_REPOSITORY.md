# TÀI LIỆU CHUYÊN SÂU: NGUYÊN LÝ HOẠT ĐỘNG CỦA CSV VÀ REPOSITORY PATTERN

> **Mục tiêu tài liệu:** Giúp bạn hiểu tường tận 100% bản chất cách phần mềm lưu trữ dữ liệu xuống ổ cứng qua file CSV, cách hoạt động của mô hình `Repository Pattern`, và cách giải thích lưu loát, tự tin khi thuyết trình hoặc bảo vệ đồ án trước giảng viên.

---

## 1. Bản Chất: Tại Sao Dự Án Dùng CSV Mà Không Dùng Database (MySQL/SQL Server)?

### 1.1. Bối cảnh dự án
Dự án của chúng ta là **Hệ thống Mô phỏng Đặt vé Sân vận động chạy trên nền tảng Java Core Console thuần**.
- Không phụ thuộc vào cài đặt các hệ quản trị cơ sở dữ liệu cồng kềnh (MySQL, PostgreSQL, Oracle).
- Chạy được ngay trên mọi máy tính chỉ cần cài đặt Java (JRE/JDK).

### 1.2. File CSV là gì?
- **CSV** viết tắt của **Comma-Separated Values** (Các giá trị phân tách nhau bằng dấu phẩy).
- Bản chất file CSV chỉ là **một file văn bản thuần túy (Plain Text)** với đuôi `.csv`.
- Bạn có thể mở file CSV bằng Notepad, VSCode, hoặc Microsoft Excel.
- Mỗi dòng trong file đại diện cho **1 bản ghi (Record/Row)**. Các thuộc tính (Fields/Columns) cách nhau bởi dấu phẩy `,`.

#### Ví dụ thực tế trong thư mục `data/` của dự án:
- **`stadiums.csv`** (Sân vận động: `id,name,address`):
  ```csv
  1,My Dinh National Stadium,Le Duc Tho Road, Hanoi
  2,Hang Day Stadium,Trinh Hoai Duc, Hanoi
  ```
- **`sections.csv`** (Khán đài: `id,stadiumId,name`):
  ```csv
  1,1,Stand A
  2,1,Stand B
  3,1,Stand C
  ```
- **`seats.csv`** (Ghế ngồi: `id,sectionId,rowLabel,seatNumber,active`):
  ```csv
  1,1,A,1,true
  2,1,A,2,true
  3,1,B,1,false
  ```
- **`fans.csv`** (Tài khoản Fan: `id,fullName,email,passwordHash,phone,status`):
  ```csv
  1,Nguyen Van A,fan1@example.com,123456,0912345678,ACTIVE
  2,Tran Thi B,fan2@example.com,password2,0987654321,INACTIVE
  ```

---

## 2. Mô Hình Repository Pattern: Tại Sao Phải Dùng?

### 2.1. Vấn đề nếu KHÔNG dùng Repository
Nếu không có Repository, tầng `Service` hoặc `Controller` sẽ phải tự mở file, tự đọc dòng, tự tách dấu phẩy.
- Code đọc/ghi file sẽ nằm rải rác khắp nơi.
- Nếu ngày mai muốn đổi từ file CSV sang MySQL hoặc MongoDB, ta phải sửa lại toàn bộ Controller và Service.

### 2.2. Giải pháp của Repository Pattern
**Repository** đóng vai trò như một **"Người thủ kho"**:
- **Service (Tầng nghiệp vụ)** chỉ nói: *"Thủ kho ơi, lấy cho tôi danh sách tất cả các sân vận động"* (`stadiumRepository.findAll()`).
- Service hoàn toàn **không quan tâm** dữ liệu được lưu bằng file CSV, Database hay RAM.
- **Repository (Thủ kho)** sẽ tự lo việc: Mở file `stadiums.csv`, đọc từng dòng, biến chuỗi văn bản thành các đối tượng Java (`Stadium`), rồi trao trả về cho Service.

```mermaid
graph LR
    User[Người Dùng Console] --> View[MainView / AdminView]
    View --> Controller[StadiumController / AuthController]
    Controller --> Service[StadiumService / AuthService]
    Service --> Repo[StadiumRepository / FanRepository]
    Repo --> File[(File CSV trên Ổ Cứng)]
```

---

## 3. Lớp Cha Thần Thánh: `CsvRepository<T extends BaseEntity>`

Tất cả các repository chuẩn trong dự án ([StadiumRepository](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/stadium/repository/StadiumRepository.java), [SectionRepository](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/stadium/repository/SectionRepository.java), [SeatRepository](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/stadium/repository/SeatRepository.java), [MatchRepository](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/match/repository/MatchRepository.java), [FanRepository](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/user/repository/FanRepository.java), [StaffRepository](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/user/repository/StaffRepository.java), [AuditLogRepository](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/admin/repository/AuditLogRepository.java)) đều kế thừa lớp trừu tượng [CsvRepository.java](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/common/csv/CsvRepository.java).

### 3.1. Ý nghĩa của `Generic <T extends BaseEntity>`
- `T`: Đại diện cho bất kỳ Entity nào (`Stadium`, `Fan`, `Seat`...).
- `extends BaseEntity`: Bắt buộc đối tượng `T` phải có thuộc tính `id` (Long) cùng hàm `getId()` và `setId()`.
- **Nhờ có Generic, chúng ta chỉ cần viết logic CRUD 1 LẦN DUY NHẤT** ở lớp cha, và tất cả 7 repository con đều tự động có sẵn các tính năng:
  - `findAll()`
  - `findById(Long id)`
  - `save(T entity)`
  - `update(T entity)`
  - `delete(Long id)`
  - `findByCondition(Predicate<T> condition)`

### 3.2. Mẫu thiết kế Template Method (Template Method Pattern)
Lớp cha `CsvRepository` làm hết mọi việc nặng nhọc: mở file, đọc ghi đĩa, sinh ID tự tăng. Nó chỉ để lại **đúng 2 phương thức trừu tượng** cho các lớp con tự định nghĩa:

```java
public abstract class CsvRepository<T extends BaseEntity> {
    // 1. Biến 1 dòng chuỗi CSV thành 1 Đối tượng Java
    public abstract T parseLine(String line);

    // 2. Biến 1 Đối tượng Java thành 1 dòng chuỗi CSV
    public abstract String formatLine(T entity);
}
```

#### Ví dụ lớp con triển khai cực kỳ ngắn gọn:
Trong [StadiumRepository.java](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/stadium/repository/StadiumRepository.java):
```java
// Đọc: "1,My Dinh,Hanoi" -> new Stadium(1L, "My Dinh", "Hanoi")
public Stadium parseLine(String line) {
    String[] parts = line.split(",", -1);
    long id = Long.parseLong(parts[0].trim());
    String name = parts[1].trim();
    String address = parts[2].trim();
    return new Stadium(id, name, address);
}

// Ghi: Stadium(1L, "My Dinh", "Hanoi") -> "1,My Dinh,Hanoi"
public String formatLine(Stadium entity) {
    return entity.getId() + "," + entity.getName() + "," + entity.getAddress();
}
```

---

## 4. Chi Tiết Từng Thao Tác (CRUD) Hoạt Động Dưới Ổ Đĩa Như Thế Nào?

### 4.1. `findAll()` - Đọc toàn bộ dữ liệu từ file
```
File CSV trên đĩa (text) 
    ---> Files.readAllLines(filePath) 
    ---> Vòng lặp for từng dòng 
    ---> parseLine(line) 
    ---> List<T> trong RAM
```
1. Gọi `Files.readAllLines(filePath, StandardCharsets.UTF_8)` để nạp toàn bộ các dòng chữ vào bộ nhớ RAM.
2. Bỏ qua các dòng trống (`line.trim().isEmpty()`).
3. Chạy hàm `parseLine(line)` để ép kiểu chuỗi thành đối tượng Model Java.
4. Trả về `List<T>` hoàn chỉnh cho Controller/Service sử dụng.

### 4.2. `findById(Long id)` - Tìm theo mã ID
1. Gọi `findAll()` để lấy toàn bộ danh sách đối tượng lên RAM.
2. Duyệt qua từng đối tượng, kiểm tra: `if (entity.getId().equals(id)) return entity;`.
3. Nếu hết danh sách không thấy thì trả về `null`.

### 4.3. `save(T entity)` - Thêm mới & Tự động tăng ID (Auto-Increment)
Trong SQL có `AUTO_INCREMENT`, còn trong file CSV thì làm sao tự sinh ID?
Đây là cơ chế tự động sinh ID trong `CsvRepository.save()`:
```java
List<T> entities = findAll(); // 1. Đọc tất cả đối tượng hiện có
if (entity.getId() == null) {
    long maxId = 0;
    for (T current : entities) {
        maxId = Math.max(maxId, current.getId()); // 2. Tìm ID lớn nhất hiện tại
    }
    entity.setId(maxId + 1); // 3. Tự tăng ID lên 1
}
entities.add(entity); // 4. Thêm đối tượng mới vào List
writeAll(entities);   // 5. Ghi đè lại toàn bộ file CSV
return entity;
```

### 4.4. `update(T entity)` - Sửa thông tin
1. Đọc toàn bộ danh sách `entities = findAll()`.
2. Dùng vòng lặp tìm phần tử có cùng ID:
   ```java
   if (current.getId().equals(entity.getId())) {
       entities.set(i, entity); // Thay thế đối tượng cũ bằng đối tượng mới
   }
   ```
3. Gọi `writeAll(entities)` ghi đè lại file CSV.

### 4.5. `delete(Long id)` - Xóa bản ghi
1. Đọc danh sách `entities = findAll()`.
2. Dùng `removeIf`:
   ```java
   entities.removeIf(entity -> entity.getId().equals(id));
   ```
3. Gọi `writeAll(entities)` ghi đè lại file CSV.

---

## 5. Các Repository Đặc Biệt: Tại Sao Không Dùng `CsvRepository`?

Trong dự án có 2 repository **không kế thừa** `CsvRepository`:
1. [TicketPriceRepository.java](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/match/repository/TicketPriceRepository.java)
2. [MatchSeatRepository.java](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/match/repository/MatchSeatRepository.java)

### Lý do: Khóa chính tổng hợp (Composite Key)
- `CsvRepository` chỉ quản lý các thực thể có **1 khóa chính duy nhất** kiểu `Long id` (`extends BaseEntity`).
- Nhưng **Giá vé (`TicketPrice`)** không có ID riêng! Nó được định danh bằng cặp 2 trường: **`(matchId, sectionId)`** (Trận nào, Khán đài nào).
  - Ví dụ: Trận 1, Khán đài 2 có giá là `200,000 VND`. Không thể có 2 dòng cùng có `matchId=1, sectionId=2`.
  - Vì vậy hàm tìm kiếm phải là: `findPrice(Long matchId, Long sectionId)`.
  - Hàm xóa phải là: `delete(Long matchId, Long sectionId)`.
- **Ghế trận đấu (`MatchSeat`)**: Cũng được truy vấn chính dựa trên cặp `(matchId, seatId)`.

Vì sự khác biệt về khóa chính này, chúng được thiết kế riêng các hàm chuyên biệt để tối ưu tốc độ và nghiệp vụ.

---

## 6. Bộ Câu Hỏi Phản Biện Thường Gặp Của Giảng Viên (Kèm Câu Trả Lời)

### Câu 1: *"Tại sao mỗi lần thêm/sửa/xóa em lại đọc và ghi đè lại toàn bộ file CSV? Như vậy có chậm không?"*
> **Trả lời:**
> *"Thưa thầy/cô, file CSV là file tuần tự dạng văn bản (flat text file), không có cơ chế đánh chỉ mục (B-Tree index) hay con trỏ sửa đổi từng byte giữa file như Database. Do đó, việc đọc danh sách lên RAM, thao tác trên bộ nhớ trong rồi ghi đè lại file là giải pháp chuẩn tắc và tin cậy nhất trong Java Core để tránh làm rách file (corrupt) dữ liệu. Với quy mô dữ liệu của ứng dụng mô phỏng (vài nghìn đến vài chục nghìn dòng), RAM máy tính chỉ mất vài mili-giây để xử lý, hoàn toàn đáp ứng tốt hiệu năng."*

### Câu 2: *"Cơ chế tự tăng ID hoạt động như thế nào nếu xóa một bản ghi ở giữa?"*
> **Trả lời:**
> *"Hệ thống sử dụng thuật toán tìm `maxId` lớn nhất hiện có trong danh sách và gán ID mới là `maxId + 1`. Vì vậy, ngay cả khi xóa các bản ghi ở giữa (ví dụ xóa ID số 3 trong dãy 1, 2, 3, 4), phần tử mới thêm vào sẽ có ID là 5, không bao giờ bị trùng lặp ID cũ, hoàn toàn tương đồng với cơ chế `AUTO_INCREMENT` của MySQL."*

### Câu 3: *"Làm thế nào để đảm bảo tính toàn vẹn dữ liệu khi ghi file?"*
> **Trả lời:**
> *"Tất cả các chuỗi trước khi ghi file đều đi qua hàm `formatLine(entity)`. Hàm này kiểm tra các trường bắt buộc (not null) và định dạng đúng thứ tự cột quy định. Nếu có lỗi dữ liệu, hàm sẽ ném `AppException(ErrorCode.INVALID_INPUT)` ngay lập tức, ngăn chặn việc ghi dữ liệu bẩn vào file CSV."*

---

## 7. Bảng Tóm Tắt Các Repository Trong Dự Án

| Tên Repository | Tệp CSV tương ứng | Khóa chính (Identifier) | Kế thừa `CsvRepository`? |
| :--- | :--- | :--- | :---: |
| [StadiumRepository](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/stadium/repository/StadiumRepository.java) | `stadiums.csv` | `id` | **Có** |
| [SectionRepository](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/stadium/repository/SectionRepository.java) | `sections.csv` | `id` | **Có** |
| [SeatRepository](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/stadium/repository/SeatRepository.java) | `seats.csv` | `id` | **Có** |
| [MatchRepository](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/match/repository/MatchRepository.java) | `matches.csv` | `id` | **Có** |
| [FanRepository](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/user/repository/FanRepository.java) | `fans.csv` | `id` (tìm kiếm bằng email) | **Có** |
| [StaffRepository](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/user/repository/StaffRepository.java) | `staff.csv` | `id` (tìm kiếm bằng email) | **Có** |
| [AuditLogRepository](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/admin/repository/AuditLogRepository.java) | `audit_logs.csv` | `id` | **Có** |
| [TicketPriceRepository](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/match/repository/TicketPriceRepository.java) | `ticket_prices.csv` | `(matchId, sectionId)` | Không (Composite Key) |
| [MatchSeatRepository](file:///Users/lilnhan/Desktop/BTC/Stadium-Ticket-Booking-Simulation/src/match/repository/MatchSeatRepository.java) | `match_seats.csv` | `(matchId, seatId)` | Không (Quản lý tồn kho vé) |
