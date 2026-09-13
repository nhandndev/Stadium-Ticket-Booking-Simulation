# Stadium Ticket Booking Simulation - Document Index

Bo tai lieu nay duoc rut ket tu `README.md` va `contextchatbotcu.md` trong repo, dong thoi bo sung them cac yeu cau/feature thuc te cua mot he thong dat ve san van dong gan muc doanh nghiep.

Muc tieu: giu dung trong tam cua du an LAB211 la Java OOP, MVC, CSV persistence va concurrency simulation chong double booking; dong thoi mo rong tai lieu de khi code khong bi thieu edge case.

## Thu tu doc de lam du an

1. [00_REPO_SCAN_SUMMARY.md](00_REPO_SCAN_SUMMARY.md) - Tong ket hien trang repo va nhung gi da rut ra.
2. [01_PROJECT_SCOPE.md](01_PROJECT_SCOPE.md) - Pham vi du an, actor, muc tieu va uu tien.
3. [02_SRS.md](02_SRS.md) - Software Requirements Specification.
4. [03_USE_CASE_SPECIFICATION.md](03_USE_CASE_SPECIFICATION.md) - Actor, use case, include/extend, luong chinh/ngoai le.
5. [04_FEATURE_BACKLOG.md](04_FEATURE_BACKLOG.md) - Feature theo module, uu tien MVP/Core/Enterprise-like.
6. [05_LAYERED_ARCHITECTURE.md](05_LAYERED_ARCHITECTURE.md) - Phan tang MVC ro rang va package goi y.
7. [06_DOMAIN_DATA_MODEL.md](06_DOMAIN_DATA_MODEL.md) - Entity, CSV schema, quan he du lieu.
8. [07_BOOKING_AND_SIMULATION_DESIGN.md](07_BOOKING_AND_SIMULATION_DESIGN.md) - Booking engine, dong bo, simulator, metric.
9. [08_EDGE_CASES_AND_BUSINESS_RULES.md](08_EDGE_CASES_AND_BUSINESS_RULES.md) - Business rules va edge cases can xu ly.
10. [09_TEST_PLAN.md](09_TEST_PLAN.md) - Test plan cho unit/integration/concurrency/performance.
11. [10_DELIVERY_CHECKLIST.md](10_DELIVERY_CHECKLIST.md) - Checklist code, data, report, slide, demo.
12. [11_SERVICE_CONTRACTS.md](11_SERVICE_CONTRACTS.md) - Contract goi y cho Controller-Service-Repository.
13. [12_IMPLEMENTATION_ROADMAP.md](12_IMPLEMENTATION_ROADMAP.md) - Lo trinh code theo phase/sprint.
14. [13_AI_USAGE_AUDIT_AND_REFLECTION.md](13_AI_USAGE_AUDIT_AND_REFLECTION.md) - AI Log, audit prompt va AI Reflection.

## Nguyen tac ra quyet dinh

- Uu tien so 1: hoan thanh dung de bai LAB211.
- Uu tien so 2: simulation phai dung ban chat, cung booking core voi fan/seller.
- Uu tien so 3: feature mo rong phai phu hop he thong dat ve that, nhung khong lam vo kien truc MVC/CSV.
- Uu tien so 4: moi tac vu booking phai re-check seat availability tai thoi diem commit, khong tin vao trang thai da xem tren UI.
- Uu tien so 5: khong de Simulator include Fan `Create Booking` checkout flow, vi flow do co online payment; Simulator chi goi `Execute Booking Core`.
- Uu tien so 6: moi lan dung AI nen ghi log va audit prompt/output de report AI Reflection co bang chung that.
