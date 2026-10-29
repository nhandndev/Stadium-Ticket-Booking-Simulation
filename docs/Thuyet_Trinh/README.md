# Bộ Tài Liệu & Kịch Bản Thuyết Trình Dự Án

> **Sơ đồ chuẩn hiện tại:** [ClassDiagram.drawio](../Class_Diagrams/ClassDiagram.drawio) (Trang 00 đến 06).  
> Kiến trúc triển khai: **Controller-First MVC + Service + CSV Repository**.  
> Demo hiện tại: **Chặng 01-09; Simulation execution còn ở chặng 10**.

Bản demo hiện tại là [14_KICH_BAN_DEMO_TUNG_FEATURE_HIEN_TAI.md](14_KICH_BAN_DEMO_TUNG_FEATURE_HIEN_TAI.md): từng feature, phím nhập, kết quả và lời giải thích. Các tài liệu milestone 65% và file 13 bên dưới phản ánh giai đoạn cũ; không dùng các menu/dữ liệu cũ đó thay cho script 14.

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
14. [**Nguyên lý hoạt động của CSV và Repository Pattern (Giải thích chi tiết cơ chế lưu trữ)**](12_NGUYEN_LY_CSV_VA_REPOSITORY.md)
15. [**Kịch bản Demo Full Tính Năng thực chiến (Từng bước bấm phím & Lời thoại)**](13_KICH_BAN_DEMO_FULL_FEATURE.md)
16. [**Kịch bản demo từng feature hiện tại: Admin, Guest, Fan, Seller, Gate và Refund**](14_KICH_BAN_DEMO_TUNG_FEATURE_HIEN_TAI.md)

---

## Hướng dẫn sử dụng hiệu quả

- **Demo hiện tại:** dùng script 14. Báo cáo milestone chặng 01-07 chỉ còn là tài liệu lịch sử tiến độ, không đại diện toàn bộ feature hiện đã code.
- Đoạn bắt đầu bằng **Lời thuyết trình:** Là văn phong tự nhiên có thể dùng trực tiếp khi nói trước giảng viên.
- Đoạn bắt đầu bằng **Chỉ trên sơ đồ:** Hướng dẫn thao tác trỏ chuột trực quan vào các phần tử tương ứng trên Class Diagram.
- Đoạn bắt đầu bằng **Điểm cần nhấn mạnh:** Các luận điểm kỹ thuật quan trọng thể hiện độ sâu kiến thức OOP và Concurrency.
- **Tính chính xác về tiến độ:** Booking, Payment, Refund, Seller và Gate đã có trong `src/`; không giới thiệu màn hình chạy Simulation như phần đã hoàn thành.
