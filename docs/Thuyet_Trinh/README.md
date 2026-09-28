# Kịch bản thuyết trình dự án

Bộ tài liệu này là lời nói gợi ý để thuyết trình dựa trên Use Case mới nhất,
sáu trang Class Diagram và source code hiện có của dự án Stadium Ticket Booking
Simulation.

## Thứ tự trình bày

1. [Kịch bản tổng quan](00_KICH_BAN_TONG_QUAN.md)
2. [Use Case hệ thống](01_USE_CASE.md)
3. [Script gạch đầu dòng từ Use Case đến Class Diagram](01A_SCRIPT_USECASE_DEN_CLASS_DIAGRAM.md)
4. [Class Diagram trang 1 - Main, View và Authentication](02_CLASS_01_MAIN_VIEW_AUTH.md)
5. [Class Diagram trang 2 - Stadium, Match và Administrator](03_CLASS_02_STADIUM_MATCH_ADMIN.md)
6. [Class Diagram trang 3 - Booking, Payment và Ticket](04_CLASS_03_BOOKING_PAYMENT_TICKET.md)
7. [Class Diagram trang 4 - Concurrency Simulation](05_CLASS_04_SIMULATION.md)
8. [Class Diagram trang 5 - CSV Repository và BaseEntity](06_CLASS_05_REPOSITORY_BASE_ENTITY.md)
9. [Class Diagram trang 6 - Quan hệ Model](07_CLASS_06_MODEL_RELATIONSHIPS.md)
10. [Phần source code đã hoàn thành](08_CODE_DA_HOAN_THANH.md)
11. [Kịch bản demo và câu hỏi phản biện](09_DEMO_VA_PHAN_BIEN.md)

## Cách sử dụng

- Đoạn bắt đầu bằng **Lời thuyết trình** có thể nói trực tiếp.
- Đoạn bắt đầu bằng **Chỉ trên sơ đồ** là thao tác trỏ chuột khi trình bày.
- Đoạn bắt đầu bằng **Điểm cần nhấn mạnh** là ý không nên bỏ qua.
- Script số 01A là bản để nhìn và nói theo ý: mỗi feature có mục đích và ví dụ;
  các trang Class Diagram có ý nghĩa và lý do của relationship.
- Mỗi file Class Diagram có phần **Mối quan hệ UML**, giải thích loại quan hệ,
  chiều nối, multiplicity và lý do sử dụng từng đường nối.
- File Use Case giải thích mục đích của từng actor trước, sau đó giải thích riêng
  từng feature mà actor được sử dụng.
- Không nói các class trong Class Diagram là đã code xong. Class Diagram là thiết
  kế mục tiêu; trạng thái code thật được trình bày riêng ở file số 08.
- Khi thời gian ngắn, dùng file số 00 rồi chọn các ý chính trong từng trang.

## Nguồn đối chiếu

- Use Case: trang `System Overview - UseCase` trong file Draw.io mới nhất.
- Class Diagram: sáu file Mermaid trong `docs/Class_Diagrams/`.
- Code hiện tại: thư mục `src/`.
