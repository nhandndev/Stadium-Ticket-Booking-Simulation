# Kịch bản phản biện về Dependency Injection

## 1. DI là gì?

### Câu trả lời để nói

> DI là Dependency Injection, nghĩa là cung cấp cho một class các object mà nó cần sử dụng từ bên ngoài. Trong dự án, em dùng constructor injection, tức là truyền các dependency qua constructor.
>
> Ví dụ AuthService cần FanRepository và StaffRepository để tìm tài khoản. Main tạo hai Repository rồi truyền chúng vào constructor của AuthService.

### Ví dụ rút gọn từ cách dự án đang làm

```java
public class AuthService {
    private final FanRepository fanRepository;
    private final StaffRepository staffRepository;

    public AuthService(
            FanRepository fanRepository,
            StaffRepository staffRepository) {
        this.fanRepository = fanRepository;
        this.staffRepository = staffRepository;
    }
}
```

Main tạo và truyền object vào.

```java
FanRepository fanRepository =
        new FanRepository(dataFolder.resolve("fans.csv"));

StaffRepository staffRepository =
        new StaffRepository(dataFolder.resolve("staff.csv"));

AuthService authService =
        new AuthService(fanRepository, staffRepository);
```

- Dependency là object mà class cần để làm việc.
- Constructor nhận các object được truyền vào.
- Field giữ tham chiếu tới object để các method sử dụng về sau.
- Trong đoạn trên, hai Repository là dependency của AuthService.

## 2. Vì sao em dùng DI trong dự án này?

### Câu trả lời để nói

> Em dùng DI để tập trung việc tạo và kết nối object tại Main. Cách này giúp em cấu hình thư mục CSV ở một chỗ và chủ động dùng chung phần xác thực giữa các Controller.
>
> Constructor cũng cho em nhìn rõ mỗi class cần những object nào để hoạt động.

### Những lợi ích cụ thể

- Main chọn dataFolder cho lần chạy hiện tại và tạo các Repository từ thư mục đó.
- Các Service nhận Repository đã cấu hình sẵn.
- Các Controller nhận cùng AuthController để cùng truy cập trạng thái đăng nhập.
- Khi đọc Main, có thể lần theo quan hệ Repository, Service, Controller và View.

## 3. Nếu thầy hỏi “Chỉ cần new trong từng class là được mà?”

### Câu trả lời để nói

> Dạ được. Class có thể tự tạo dependency bằng new và chương trình vẫn chạy được. Cách đó có thể làm Main ngắn hơn.
>
> Điểm khác nhau nằm ở nơi chịu trách nhiệm tạo dependency. Khi class tự new, nó tự quyết định cách tạo dependency và cấu hình liên quan. Với constructor injection, Main quyết định rồi cung cấp object cho class.

### Cách Service tự tạo dependency

Đây là phương án minh họa để so sánh.

```java
public class AuthService {
    private final FanRepository fanRepository;
    private final StaffRepository staffRepository;

    public AuthService() {
        this.fanRepository =
                new FanRepository(Path.of("data/fans.csv"));

        this.staffRepository =
                new StaffRepository(Path.of("data/staff.csv"));
    }
}
```

Main gọi constructor không tham số.

```java
AuthService authService = new AuthService();
```

Luồng khởi tạo như sau.

```text
Main tạo AuthService
→ constructor AuthService tự tạo hai Repository
→ Service sử dụng hai Repository đó
```

### Cách constructor injection

```text
Main tạo hai Repository
→ Main truyền chúng vào constructor AuthService
→ Service lưu và sử dụng các object được cung cấp
```

Cả hai cách đều có lệnh new. DI mô tả cách cung cấp dependency cho class.

## 4. Bất lợi cụ thể khi mỗi Controller tự tạo phần xác thực

### Tình huống minh họa

Giả sử MainController và BookingController mỗi nơi tự tạo một AuthService.

```text
MainController
└── AuthService A
    └── currentUser

BookingController
└── AuthService B
    └── currentUser
```

AuthService có field trạng thái đăng nhập.

```java
private User currentUser;
```

Đăng nhập qua AuthService A chỉ thay đổi field của object A.

```text
AuthService A.currentUser = Nhân
AuthService B.currentUser = null
```

Nếu BookingController kiểm tra AuthService B, nó thấy chưa có người đăng nhập.

### Dự án hiện tại xử lý thế nào?

Main tạo một AuthController cùng AuthService bên trong.

```java
AuthController authController = new AuthController(
        new AuthService(fanRepository, staffRepository));
```

Main truyền cùng biến authController cho các Controller cần xác thực.

```text
MainController ─────┐
BookingController ──┤
TicketController ───┼──→ cùng AuthController → cùng AuthService
AdminController ────┘
```

### Câu trả lời để nói

> Nếu mỗi Controller tự tạo một AuthService riêng thì mỗi object có currentUser riêng. Đăng nhập ở object này sẽ chưa cập nhật trạng thái cho object khác. Em truyền chung AuthController để các Controller cùng truy cập một bộ xác thực.

### Lưu ý khi giải thích

- Object được dùng chung vì Main truyền cùng tham chiếu vào các nơi cần dùng.
- Constructor injection vẫn cho phép truyền nhiều object khác nhau nếu người viết chủ động tạo chúng.
- Việc dùng chung trạng thái trong ví dụ là lựa chọn kết nối object của dự án Console hiện tại.

## 5. Bất lợi khi mỗi Service tự viết đường dẫn CSV

Giả sử Service tự tạo Repository như sau.

```java
this.fanRepository =
        new FanRepository(Path.of("data/fans.csv"));
```

- Đường dẫn đã được cố định bên trong Service.
- Khi muốn demo bằng thư mục khác, cần sửa cấu hình đó hoặc bổ sung cách truyền đường dẫn vào.
- Khi nhiều class cùng viết đường dẫn riêng, người viết phải đảm bảo các đường dẫn cùng trỏ tới bộ dữ liệu mong muốn.

Dự án hiện tại chọn thư mục tại Main.

```java
Path dataFolder = args.length > 0
        ? Path.of(args[0])
        : Path.of("data");

FanRepository fanRepository =
        new FanRepository(dataFolder.resolve("fans.csv"));
```

Có thể chạy với bộ dữ liệu chính.

```bash
java -cp out app.Main data
```

Hoặc chạy với một bộ dữ liệu demo đã chuẩn bị.

```bash
java -cp out app.Main /tmp/demo-data
```

### Câu trả lời để nói

> Em cấu hình dataFolder ở Main rồi tạo các Repository theo thư mục đó. Các Service nhận Repository đã chuẩn bị, nên em có thể chuyển bộ dữ liệu khi chạy demo mà vẫn giữ cùng cách xử lý nghiệp vụ.

## 6. Vì sao phải tạo dependency trước?

```java
FanRepository fanRepository =
        new FanRepository(dataFolder.resolve("fans.csv"));

StaffRepository staffRepository =
        new StaffRepository(dataFolder.resolve("staff.csv"));

AuthService authService =
        new AuthService(fanRepository, staffRepository);
```

- Constructor AuthService cần nhận hai object Repository.
- Hai object phải được tạo trước khi truyền vào constructor đó.
- Main sắp thứ tự khởi tạo theo dependency.

```text
Repository
→ Service nhận Repository
→ Controller nhận Service và View cần thiết
→ MainController bắt đầu điều phối menu
```

### Câu trả lời để nói

> Dependency phải tồn tại trước khi em truyền nó vào object cần dùng. Vì vậy, Main tạo Repository trước, rồi mới tạo Service và Controller sử dụng chúng.

## 7. Vì sao field dependency có final?

```java
private final FanRepository fanRepository;
```

final ngăn field bị gán lại sau khi đã được khởi tạo.

Gán trong constructor là hợp lệ.

```java
public AuthService(FanRepository fanRepository) {
    this.fanRepository = fanRepository;
}
```

Nếu sau đó có method cố gán sang Repository khác, Java báo lỗi biên dịch.

```java
this.fanRepository = anotherRepository;
```

Các lời gọi xử lý dữ liệu vẫn được phép.

```java
fanRepository.save(fan);
fanRepository.update(fan);
```

### Câu trả lời để nói

> Em dùng final vì dependency được xác định lúc tạo object và em không có nhu cầu gán lại field đó. final giúp trình biên dịch ngăn việc vô tình thay dependency. Repository vẫn có thể thực hiện các thao tác đọc và ghi dữ liệu bình thường.

### Nếu thầy hỏi “Bỏ final có được không?”

> Dạ được. Bỏ final thì constructor injection vẫn hoạt động. Khi đó field có thể được gán lại trong các method khác. final là lựa chọn để giữ ổn định tham chiếu của dependency.

final trên field cũng không tự làm object an toàn khi nhiều thread sử dụng cùng lúc. Luồng bảo vệ ghế của dự án có vùng synchronized riêng.

## 8. Khi nào tự new bên trong class là hợp lý?

Một method có thể tự tạo danh sách kết quả phục vụ công việc của nó.

```java
List<Fan> result = new ArrayList<>();
```

Trong trường hợp này, danh sách là dữ liệu làm việc cục bộ. Việc tạo tại method giúp giới hạn phạm vi sử dụng rõ ràng.

### Câu trả lời để nói

> Em vẫn dùng new bên trong class cho các object cần tạo tại chỗ, như danh sách kết quả hoặc DTO. Với những dependency có cấu hình chung hoặc cần chia sẻ trạng thái, em chọn tạo tại Main và truyền vào.

## 9. Cách này có đánh đổi gì?

- Main dài hơn vì chứa phần khởi tạo và kết nối object.
- Constructor có nhiều tham số khi class cần nhiều dependency.
- Phải tạo object đúng thứ tự trước khi truyền vào nhau.

### Câu trả lời để nói

> Cách này làm Main dài hơn và một số constructor có nhiều tham số. Em chấp nhận điều đó để cấu hình dữ liệu tập trung, nhìn rõ dependency và chủ động quản lý object dùng chung.

## 10. Bản nói tổng hợp khoảng một phút

> Dự án em dùng Dependency Injection thông qua constructor. Nghĩa là class nhận các object mà nó cần sử dụng từ bên ngoài.
>
> Main vẫn dùng new để tạo Repository, Service, Controller và View, sau đó truyền dependency vào constructor tương ứng. Điểm khác với việc mỗi class tự new dependency là Main chịu trách nhiệm tạo và kết nối các object.
>
> Em chọn cách này vì có hai nhu cầu cụ thể. Thứ nhất, các Repository phải sử dụng cùng thư mục dữ liệu được chọn khi khởi động. Thứ hai, các Controller cần dùng chung AuthController để cùng truy cập trạng thái đăng nhập.
>
> Nếu mỗi Controller tự tạo một AuthService riêng thì mỗi object sẽ có currentUser riêng. Đăng nhập qua một object sẽ chưa làm thay đổi trạng thái của object khác.
>
> Em dùng final ở những field dependency được cố định sau khi khởi tạo để tránh gán lại nhầm. Bỏ final thì constructor injection vẫn hoạt động. Cách này có đánh đổi là Main dài hơn và constructor có nhiều tham số.

## 11. Những câu cần diễn đạt chính xác

- Nói “Tạo tập trung tại Main giúp dễ quản lý cấu hình và object dùng chung”.
- Nói “Main vẫn dùng new để tạo dependency”.
- Nói “Dùng chung object vì em truyền cùng tham chiếu”.
- Nói “final ngăn gán lại field, còn object vẫn thực hiện được các thao tác của nó”.
- Nói “Dự án tự kết nối các object bằng Java thông thường”.

Các đoạn ví dụ được rút gọn để giải thích. Cấu trúc xác thực thực tế của dự án là Controller sử dụng chung AuthController, bên trong AuthController giữ AuthService.
