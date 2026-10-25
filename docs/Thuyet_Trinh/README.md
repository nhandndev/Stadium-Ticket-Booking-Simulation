# Bộ Tài Liệu & Kịch Bản Thuyết Trình Dự Án

> **Sơ đồ chuẩn hiện tại:** [Controller_First_Full.drawio](../Class_Diagrams/Controller_First_Full.drawio) (Trang 00 đến 06).  
> Kiến trúc triển khai: **Controller-First MVC + Service + CSV Repository**.  
> Tiến độ mã nguồn: **Hoàn thành Chặng 1 - 7 (~65% toàn bộ hệ thống)**.

Bộ tài liệu này cung cấp toàn bộ kịch bản, lời thuyết minh và các câu hỏi phản biện bám sát hệ thống Use Case, sáu trang Class Diagram và 60 file mã nguồn Java đã hoàn thành trong thư mục `src/`.

---

## Danh mục tài liệu theo thứ tự trình bày

1. [Kịch bản tổng quan toàn dự án](00_KICH_BAN_TONG_QUAN.md)
2. [Use Case hệ thống và phân quyền Actor](01_USE_CASE.md)
3. [Script gạch đầu dòng từ Use Case đến Class Diagram](01A_SCRIPT_USECASE_DEN_CLASS_DIAGRAM.md)
4. [Class Diagram trang 1 - Main, View và Authentication](02_CLASS_01_MAIN_VIEW_AUTH.md)
5. [Class Diagram trang 2 - Stadium, Match và Administrator](03_CLASS_02_STADIUM_MATCH_ADMIN.md)
6. [Class Diagram trang 3 - Booking, Payment và Ticket](04_CLASS_03_BOOKING_PAYMENT_TICKET.md)
7. [Class Diagram trang 4 - Concurrency Simulation](05_CLASS_04_SIMULATION.md)
8. [Class Diagram trang 5 - CSV Repository và BaseEntity](06_CLASS_05_REPOSITORY_BASE_ENTITY.md)
9. [Class Diagram trang 6 - Quan hệ Model](07_CLASS_06_MODEL_RELATIONSHIPS.md)
10. [Báo cáo chi tiết mã nguồn đã hoàn thành (Chặng 1 - 7, ~65%)](08_CODE_DA_HOAN_THANH.md)
11. [Kịch bản demo thực tế và câu hỏi phản biện](09_DEMO_VA_PHAN_BIEN.md)
12. [**Kịch bản báo cáo tiến độ 65% (Dành cho buổi báo cáo tiến độ)**](10_KICH_BAN_BAO_CAO_TIEN_DO.md)
13. [**Chi tiết luồng code từ MainController đến toàn bộ dự án (Call-Stack Tracing)**](11_CHI_TIET_LUONG_CODE_FLOW.md)

---

## Hướng dẫn sử dụng hiệu quả

- **Báo cáo tiến độ Milestone (Hiện tại):** Sử dụng trực tiếp file số 10 ([10_KICH_BAN_BAO_CAO_TIEN_DO.md](10_KICH_BAN_BAO_CAO_TIEN_DO.md)) và số 11 ([09_DEMO_VA_PHAN_BIEN.md](09_DEMO_VA_PHAN_BIEN.md)).
- Đoạn bắt đầu bằng **Lời thuyết trình:** Là văn phong tự nhiên có thể dùng trực tiếp khi nói trước giảng viên.
- Đoạn bắt đầu bằng **Chỉ trên sơ đồ:** Hướng dẫn thao tác trỏ chuột trực quan vào các phần tử tương ứng trên Class Diagram.
- Đoạn bắt đầu bằng **Điểm cần nhấn mạnh:** Các luận điểm kỹ thuật quan trọng thể hiện độ sâu kiến thức OOP và Concurrency.
- **Tính chính xác về tiến độ:** Phân định rạch ròi giữa các module đã code (`src/` - 65%) và các module mục tiêu sẽ làm ở giai đoạn tiếp theo (Stage 08 Booking, Stage 09 Seller/Gate, Stage 10 Simulation - 35%).
