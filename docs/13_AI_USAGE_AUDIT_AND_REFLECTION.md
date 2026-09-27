# AI Usage Audit And Reflection

## Muc tieu

Tai lieu nay bo sung cho task T10: AI Reflection & Nop bai.

Muc tieu khong chi la ghi "da dung AI", ma phai chung minh nhom biet dung AI co kiem soat:

- Ghi lai prompt quan trong da dung.
- Audit prompt cua tung thanh vien sau moi lan hoi AI.
- Danh gia output AI dung/sai/thieu.
- Ghi cach nhom verify va sua output AI.
- Viet AI Reflection trong bao cao, toi thieu 500 tu moi thanh vien.
- Review code, fix bug, polish UI/console flow.
- Dong goi ZIP dung cau truc de bai.

## T10 - AI Reflection & Nop

| Task | Ten | Mo ta | Deliverables | Notes |
|---|---|---|---|---|
| T10 | AI Reflection & Nop | Tong hop AI Log ca nhan tung thanh vien. Viet AI Reflection: qua trinh dung AI, danh gia chat luong output, loi AI mac, bai hoc rut ra. Review code toan bo, fix bug, polish UI. Dong goi ZIP theo cau truc yeu cau. | AI Log file. AI Reflection trong bao cao >=500 tu/nguoi. ZIP nop hoan chinh. | Moi lan dung AI nen audit prompt va audit output. Co the them prompt truoc do vao log/audit neu con noi dung. |

## Folder De Xuat

```text
ai_logs/
  member1_ai_log.md
  member2_ai_log.md
  member3_ai_log.md
  prompt_audit_summary.md
```

Neu de bai yeu cau moi thanh vien co log rieng, khong gom het vao mot file duy nhat.

Current member log:

- `ai_logs/doan_ngoc_nhan_QE210282_ai_log.md`
- `ai_logs/prompt_audit_summary.md`

## AI Log Template Cho Tung Thanh Vien

```text
# AI Log - Member Name

## Entry 01

Date:
Tool/Model:
Project phase:

Original prompt:

AI output summary:

Accepted parts:

Rejected/modified parts:

Why changed:

Verification method:

Impact on project:

Follow-up prompt if any:
```

## Prompt Audit Template

Dung template nay sau moi lan hoi AI, nhat la khi prompt lien quan den code, SRS, diagram, concurrency hoac edge case.

```text
# Prompt Audit

Prompt ID:
Member:
Date:
Task:

## Original Prompt

Paste prompt here.

## Prompt Quality Review

Clarity:
- Was the request specific?
- Did it include project context?
- Did it include constraints such as Java, MVC, CSV, LAB211?

Completeness:
- Did it mention expected output format?
- Did it mention edge cases?
- Did it mention what should not be changed?

Risk:
- Could AI hallucinate requirements?
- Could AI suggest architecture too large for LAB scope?
- Could AI mix simulation with real payment flow?

## Output Audit

Useful output:

Incorrect output:

Missing output:

Changes made by team:

Verification:

Final decision:
```

## Prompt Audit Checklist

- [ ] Prompt co noi ro project la Stadium Ticket Booking Simulation.
- [ ] Prompt co noi ro Java OOP, MVC, CSV persistence.
- [ ] Prompt co noi ro simulator tap trung vao double booking/concurrency.
- [ ] Prompt khong lam simulator di qua online payment flow.
- [ ] Prompt yeu cau phan tang ro neu hoi ve architecture.
- [ ] Prompt yeu cau edge cases neu hoi ve feature/use case.
- [ ] Prompt yeu cau output theo Markdown/table/code neu can.
- [ ] Output AI duoc doc lai, khong copy blind.
- [ ] Output AI duoc so voi README/SRS/de bai.
- [ ] Output AI duoc test neu la code.
- [ ] Loi/thieu sot cua AI duoc ghi vao log.

## Cac Prompt Truoc Do Nen Dua Vao Audit

Nhung prompt/yeu cau da xuat hien trong qua trinh lam tai lieu nen duoc dua vao AI Log:

1. Quet folder stadium va rut ket toan bo thanh document.
2. Tao cac file SRS, Use Case, Feature va document can thiet.
3. Bo sung feature gan he thong dat ve doanh nghiep, khong chi project hoc.
4. Dam bao phan tang ro rang theo MVC/Service/Repository.
5. Giu simulation nhu cu, tap trung double booking.
6. Sua Use Case de Simulator khong include Fan `Create Booking` co payment.
7. Tach `Create Booking` checkout flow va `Execute Booking Core`.
8. Them `Hold Seats`, `Expire Seat Hold`, `Expire Pending Booking`.
9. Them `Idempotency Key` de tranh duplicate request.
10. Them cancellation/refund, ticket check-in, notification, manage staff/roles, ticket sales control, pricing.
11. Them T10 AI Reflection & Nop, moi lan dung AI thi audit prompt/output.

## Vi Du AI Reflection Structure >=500 Tu/Nguoi

Moi thanh vien nen viet theo cau truc sau:

1. Context: thanh vien phu trach phan nao cua du an.
2. How AI was used: dung AI de lam gi, vi du SRS, use case, code, test, debug.
3. Quality of AI output: phan nao huu ich, phan nao can sua.
4. AI mistakes: AI tung sai/thieu o dau.
5. Human verification: nhom kiem tra lai bang cach nao.
6. Prompt improvement: prompt sau tot hon prompt truoc nhu the nao.
7. Lessons learned: bai hoc khi dung AI trong software engineering.
8. Ethical/academic note: AI ho tro, nhom van phai hieu va chiu trach nhiem.

## Mau AI Reflection Ngan De Mo Rong

```text
Trong du an Stadium Ticket Booking Simulation, toi su dung AI nhu mot cong cu ho tro phan tich yeu cau, thiet ke use case, goi y kien truc MVC va kiem tra edge case. AI giup toi nhan ra mot so van de ma neu chi lam nhu project hoc co the bo sot, vi du double booking khi nhieu thread cung dat mot ghe, seat hold bi treo neu user thoat giua payment, va duplicate request khi nguoi dung bam dat ve nhieu lan.

Tuy nhien, toi khong copy output AI mot cach may moc. Co lan AI goi y cho Simulator include truc tiep Create Booking cua Fan, trong khi Create Booking lai include Make Online Payment. Sau khi review, toi thay dieu nay sai voi muc tieu cua simulator, vi simulator can do tranh chap ghe va synchronization, khong nen tao 1000 payment flows. Vi vay nhom da sua thiet ke bang cach tach Execute Booking Core ra khoi customer checkout flow. Fan va Seller co the dung checkout rieng co payment, con Simulator chi goi booking core/attempt seat booking.

Qua trinh nay giup toi hieu rang AI co the dua ra cau tra loi co ve hop ly nhung van sai ve logic nghiep vu neu prompt chua du rang buoc. De cai thien, toi bat dau viet prompt ro hon, yeu cau giu Java MVC, CSV persistence, LAB211 scope, va nhan manh simulation double booking. Moi output quan trong deu duoc so sanh voi README, SRS va rule cua de bai. Neu la code, can compile/test; neu la document, can kiem tra consistency giua use case, service contract va data model.

Bai hoc lon nhat la AI nen duoc dung nhu mot reviewer va co-designer, khong phai nguoi quyet dinh cuoi cung. Nhom phai hieu vi sao mot feature duoc them, vi sao mot quan he include/extend dung hoac sai, va vi sao edge case can xu ly. Viec ghi AI Log va audit prompt giup qua trinh lam bai minh bach hon, dong thoi giup nhom nhin lai cach prompt anh huong den chat luong output.
```

Doan mau tren chua du 500 tu; moi thanh vien can viet them trai nghiem ca nhan, phan viec cu the va bang chung verify.

## AI Output Audit Examples

| AI Suggestion | Audit Result | Final Decision |
|---|---|---|
| Simulator include Create Booking | Sai logic neu Create Booking co online payment | Sua thanh Simulator include Execute Booking Core |
| Them Seat Hold Timeout | Dung voi he thong booking thuc te | Dua vao SRS/edge case/P2 implementation |
| Them Refund/Check-in/Notification | Hop ly nhung khong bat buoc LAB | Dua vao enterprise-like P2/P3 |
| CRUD Admin bung het len Use Case Diagram | Co the lam diagram roi | Ghi Manage X = CRUD + Search |

## Final AI Submission Checklist

- [ ] Moi thanh vien co `ai_logs/memberX_ai_log.md`.
- [ ] Moi log co prompt goc, output summary, accepted/rejected parts.
- [ ] Co `prompt_audit_summary.md` hoac section audit trong tung log.
- [ ] AI Reflection >=500 tu/nguoi trong report.
- [ ] Reflection co noi loi AI tung mac va cach sua.
- [ ] Reflection co noi cach nhom verify output AI.
- [ ] Code da review lai, khong co doan AI generate ma nhom khong hieu.
- [ ] Report khong noi qua muc ve feature chua implement.
- [ ] ZIP nop dung cau truc de bai.
