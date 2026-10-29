# 📚 Thư Viện Tài Liệu Dự Án (Documentation Index)

> **Hệ thống Mô phỏng Đặt vé Sân vận động (Stadium Ticket Booking Simulation)**  
> **Kiến trúc:** Controller coordinates `View` for console I/O and `Service -> Repository -> CSV` for business operations.
> **Sơ đồ chuẩn:** [ClassDiagram.drawio](ClassDiagram.drawio) (hoặc [Class_Diagrams/ClassDiagram.drawio](Class_Diagrams/ClassDiagram.drawio))

---

## 🗂️ CẤU TRÚC PHÂN LOẠI TÀI LIỆU TRONG THƯ MỤC `docs/`

Để thuận tiện tra cứu và báo cáo, toàn bộ tài liệu trong thư mục này được phân thành 3 khối chuyên biệt:

```text
docs/
├── ClassDiagram.drawio          <-- Bản vẽ 7 trang Use Case & Class Diagram chuẩn mới nhất
├── README.md                    <-- Mục lục điều hướng tài liệu tổng thể (File này)
├── Class_Diagrams/              <-- Thư mục chứa sơ đồ (ClassDiagram.drawio, .mmd working copies)
├── Thuyet_Trinh/                <-- Thư mục chứa 14 kịch bản báo cáo & demo thực chiến
└── [00 -> 15]_*.md              <-- Bộ tài liệu Đặc tả Kỹ thuật, Kiến trúc, SRS & Concurrency
```

---

Console usability review and 50 isolated smoke scenarios:
[15_CONSOLE_VIEW_REVIEW.md](15_CONSOLE_VIEW_REVIEW.md), including exact visible UML member changes.

## 1. 🌟 KHỐI SƠ ĐỒ THIẾT KẾ CHUẨN (CLASS DIAGRAMS & USE CASE)

*   📐 **[ClassDiagram.drawio](ClassDiagram.drawio)** (Bản sao tại [Class_Diagrams/ClassDiagram.drawio](Class_Diagrams/ClassDiagram.drawio)):  
    **Bản vẽ thiết kế chuẩn mực 7 trang** (00 Use Case, 01 Account/Guest, 02 Stadium/Match/Admin, 03 Booking/Payment, 04 Simulation, 05 Repository, 06 Domain Model).
    *   *Trang 02:* Đã cập nhật phương thức `+formatItem(item: Object): String` trên `AdminManagementView` khớp 100% mã nguồn.
*   📁 **[Class_Diagrams/README.md](Class_Diagrams/README.md)**: Hướng dẫn cấu trúc Class Diagram và các bản vẽ nháp Mermaid (`.mmd`).

---

## 2. 🎤 KHỐI KỊCH BẢN THUYẾT TRÌNH & DEMO THỰC CHIẾN (`docs/Thuyet_Trinh/`)

*   📖 **[Thuyet_Trinh/README.md](Thuyet_Trinh/README.md)**: Danh mục kịch bản báo cáo toàn diện.
*   **[14_KICH_BAN_DEMO_TUNG_FEATURE_HIEN_TAI.md](Thuyet_Trinh/14_KICH_BAN_DEMO_TUNG_FEATURE_HIEN_TAI.md)**: Script demo hiện tại: từng feature, dữ liệu nhập, kết quả, lời giải thích, luồng Fan/Seller/Gate/Refund và dữ liệu demo riêng. File 13 là bản chặng 01-07 cũ.
*   🔍 **[11_CHI_TIET_LUONG_CODE_FLOW.md](Thuyet_Trinh/11_CHI_TIET_LUONG_CODE_FLOW.md)**: Truy vết luồng thực thi (Call-Stack Tracing) từ `MainController` qua từng tầng.
*   💾 **[12_NGUYEN_LY_CSV_VA_REPOSITORY.md](Thuyet_Trinh/12_NGUYEN_LY_CSV_VA_REPOSITORY.md)**: Giải trình chi tiết nguyên lý hoạt động của Generic CSV Repository Pattern.
*   📊 **[10_KICH_BAN_BAO_CAO_TIEN_DO.md](Thuyet_Trinh/10_KICH_BAN_BAO_CAO_TIEN_DO.md)**: Kịch bản báo cáo tiến độ 65% trước Giảng viên.

---

## 3. 📋 KHỐI ĐẶC TẢ HỆ THỐNG, SRS & THIẾT KẾ CHI TIẾT (`00 -> 14`)

| Nhóm nghiệp vụ | Mã tài liệu & Liên kết | Nội dung chi tiết |
| :--- | :--- | :--- |
| **Tổng quan & Phạm vi** | [00_REPO_SCAN_SUMMARY.md](00_REPO_SCAN_SUMMARY.md)<br>[01_PROJECT_SCOPE.md](01_PROJECT_SCOPE.md) | Hiện trạng mã nguồn, phạm vi dự án, Actor và mục tiêu khoa học |
| **Yêu cầu & Nghiệp vụ** | [02_SRS.md](02_SRS.md)<br>[03_USE_CASE_SPECIFICATION.md](03_USE_CASE_SPECIFICATION.md)<br>[04_FEATURE_BACKLOG.md](04_FEATURE_BACKLOG.md) | Software Requirements Specification, Use Case chi tiết và Feature Backlog |
| **Kiến trúc & Dữ liệu** | [05_LAYERED_ARCHITECTURE.md](05_LAYERED_ARCHITECTURE.md)<br>[06_DOMAIN_DATA_MODEL.md](06_DOMAIN_DATA_MODEL.md)<br>[14_MODEL_ENTITY_FIELD_GUIDE.md](14_MODEL_ENTITY_FIELD_GUIDE.md) | Phân tầng Controller-First, cấu trúc CSV Schema và từ điển trường Entity |
| **Concurrency & Booking**| [07_BOOKING_AND_SIMULATION_DESIGN.md](07_BOOKING_AND_SIMULATION_DESIGN.md)<br>[08_EDGE_CASES_AND_BUSINESS_RULES.md](08_EDGE_CASES_AND_BUSINESS_RULES.md) | Thiết kế 4 chiến lược khóa (No-Lock, Sync, FileLock, Optimistic) và quy tắc chống Double Booking |
| **Test & Kế hoạch** | [09_TEST_PLAN.md](09_TEST_PLAN.md)<br>[10_DELIVERY_CHECKLIST.md](10_DELIVERY_CHECKLIST.md)<br>[11_SERVICE_CONTRACTS.md](11_SERVICE_CONTRACTS.md)<br>[12_IMPLEMENTATION_ROADMAP.md](12_IMPLEMENTATION_ROADMAP.md) | Kế hoạch kiểm thử, hợp đồng giao diện Service, lộ trình 11 chặng |
| **Nhật ký AI Reflection** | [13_AI_USAGE_AUDIT_AND_REFLECTION.md](13_AI_USAGE_AUDIT_AND_REFLECTION.md) | Bằng chứng kiểm toán sử dụng AI và báo cáo phản tư học tập |
