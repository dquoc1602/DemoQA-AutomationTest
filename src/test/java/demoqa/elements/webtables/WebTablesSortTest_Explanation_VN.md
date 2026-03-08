# Giải thích cơ chế hoạt động của WebTablesSortTest.java

File `WebTablesSortTest.java` được thiết kế để kiểm thử tự động chức năng sắp xếp (sorting) của bảng dữ liệu (Web Table) trên trang DemoQA. Dưới đây là giải thích chi tiết về cách mã nguồn này vận hành.

---

## 1. Các thành phần chính (Important Components)

- **`WebTablesPage table`**: Đây là đối tượng trang (Page Object). Nó chứa các "hành động" mà người dùng có thể làm trên trình duyệt như: click vào tiêu đề cột, đọc dữ liệu từ bảng, tìm kiếm, v.v.
- **`WebTableRecord`**: Một lớp (class) đại diện cho một dòng dữ liệu. Nó giúp chúng ta quản lý thông tin như Tên, Họ, Tuổi, Email, Lương và Phòng ban một cách dễ dàng.
- **`Column`**: Một danh sách định nghĩa sẵn (Enum) tên các cột trong bảng (ví dụ: `FIRST_NAME`, `LAST_NAME`, `SALARY`).
- **`SortDirection`**: Enum định nghĩa trạng thái sắp xếp: `ASC` (Tăng dần), `DESC` (Giảm dần), hoặc `NONE` (Chưa sắp xếp).

---

## 2. Quy trình hoạt động của các hàm kiểm thử

### A. Hàm khởi tạo (`setup`)
Sử dụng annotation `@BeforeEach`, hàm này chạy trước mỗi bài test.
- **Mục đích**: Đảm bảo bảng luôn có ít nhất 3 dòng dữ liệu. Nếu bảng trống hoặc quá ít dòng, nó sẽ tự động dùng hàm `table.addRecord()` để thêm dữ liệu mẫu. Điều này giúp các bài test sắp xếp có ý nghĩa hơn.

### B. Cơ chế kiểm tra sắp xếp (Core Logic)
Mỗi bài test (như `verifySortFirstName` hay `verifySortSalary`) đều tuân theo các bước sau:

1. **Thực hiện hành động**: Gọi `table.clickColumnHeader(Column.XXX)`. Hành động này mô phỏng việc người dùng click chuột vào tiêu đề cột trên trang web.
2. **Lấy dữ liệu thực tế**: Sử dụng `table.getAllRecords()` để lấy danh sách các dòng đang hiển thị trên trình duyệt. Sau đó dùng Java Stream (`.map(...)`) để tách riêng cột muốn kiểm tra ra thành một danh sách (List).
3. **Tạo dữ liệu mong đợi**: Dùng code Java để tự sắp xếp danh sách đó theo đúng quy tắc (Tăng dần hoặc Giảm dần).
4. **So sánh (Assertion)**: Dùng `assertEquals(expected, actual)` để so sánh danh sách từ trang web với danh sách đã được code Java sắp xếp. Nếu hai danh sách giống hệt nhau, bài test vượt qua (Pass).

### C. Xử lý kiểu dữ liệu đặc biệt
- **Sắp xếp văn bản**: Sử dụng `String.CASE_INSENSITIVE_ORDER` để đảm bảo chữ hoa và chữ thường được xử lý công bằng.
- **Sắp xếp số (Lương, Tuổi)**: Vì dữ liệu lấy từ web luôn là dạng chuỗi (String), code sẽ chuyển đổi chúng về kiểu số (`Long.parseLong` hoặc `Integer.parseInt`) trước khi so sánh. Điều này tránh lỗi logic (ví dụ: trong chuỗi "10" có thể đứng trước "2", nhưng về số thì 10 phải lớn hơn 2).

---

## 3. Demo ví dụ hoạt động (Example Snippet)

Dưới đây là một đoạn code rút gọn minh họa cách một hàm test kiểm tra việc sắp xếp theo **Lương (Salary)**:

```java
@Test
void demoSortSalary() {
    // 1. Click vào tiêu đề cột 'Salary' để sắp xếp tăng dần
    table.clickColumnHeader(Column.SALARY);

    // 2. Lấy danh sách lương thực tế từ bảng (convert từ String sang Long)
    List<Long> actualSalaries = table.getAllRecords().stream()
            .map(record -> Long.parseLong(record.salary))
            .collect(Collectors.toList());

    // 3. Tạo danh sách mong đợi bằng cách dùng hàm sort của Java
    List<Long> expectedSalaries = actualSalaries.stream()
            .sorted() // Sắp xếp tăng dần bằng code Java
            .collect(Collectors.toList());

    // 4. So sánh
    assertEquals(expectedSalaries, actualSalaries, "Lỗi: Bảng không sắp xếp đúng theo lương!");
}
```

---

## 4. Các kịch bản nâng cao trong file
- **`verifySortAfterAddingRecord`**: Kiểm tra xem sau khi thêm 1 người mới, thứ tự sắp xếp cũ có được giữ nguyên không.
- **`verifySortWithSearch`**: Kiểm tra xem khi đang tìm kiếm (filter), chức năng sắp xếp có còn hoạt động chính xác trên những kết quả còn lại hay không.
- **`verifySortPersistenceAfterResize`**: Kiểm tra xem khi thay đổi số dòng hiển thị trên một trang (ví dụ từ 10 dòng xuống 5 dòng), trang web có còn nhớ là mình đang sắp xếp theo cột nào không.
