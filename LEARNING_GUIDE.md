# Hướng Dẫn Tự Học Automation Test (Từ DemoQAWed)

Chào mừng bạn đến với dự án Automation Test thực chiến trên nền tảng DemoQA! 
Tài liệu này được thiết kế như một **cuốn sách giáo khoa thu nhỏ**, giúp bất kỳ ai — từ một người mới bắt đầu (Beginner) cho tới một Kỹ sư Kiểm thử Tự động chuyên nghiệp (Professional) — đều có thể hiểu, học hỏi và đóng góp vào framework này.

---

## 🎯 Phần 1: Tổng Quan (Overview)
Khung làm việc (Framework) này được xây dựng trên Java 17, Selenium WebDriver 4, và TestNG. 
Nó không chỉ là những dòng lệnh rời rạc để click chuột hay điền form. Framework này thể hiện tư duy **Kiến trúc Phần mềm** (Software Architecture) để code dễ đọc, dễ bảo trì và mở rộng.

**Mục tiêu của Framework:**
1. **Dễ đọc (Readable)**: Nhìn vào file `Test`, bạn đọc hiểu kịch bản y như đọc truyện tiếng Anh.
2. **Dễ bảo trì (Maintainable)**: UI website thay đổi? Bạn chỉ cần sửa 1 dòng code duy nhất ở `locator`.
3. **Mở rộng (Scalable)**: Chạy song song 10 test (Parallel execution), chạy ngầm (Headless), đổi trình duyệt chỉ cần chỉnh 1 file configuration duy nhất.

---

## 🏗️ Phần 2: Giải Mã Cấu Trúc Dự Án (Architecture)

Dự án áp dụng chặt chẽ mô hình **Page Object Model (POM)**. Hãy nhìn vào cấu trúc cây thư mục (đã được tối ưu hóa theo chuẩn Java Quốc Tế):

```text
DemoQAWed/src/
├── main/java/                 # Nơi chứa mọi công cụ, bản đồ và kỹ năng
│   ├── common/                # (Tools) Các hàm tiện ích dùng chung (Helper, Constants)
│   ├── core/                  # (Engine) Động cơ của Framework (BasePage, DriverManager)
│   ├── locator/               # (Map) Bản đồ - chứa MỌI tọa độ (XPath, CSS) của các nút bấm
│   ├── model/                 # (Data) Khối dữ liệu (Ví dụ: thông tin 1 User để đăng ký)
│   └── page/                  # (Skills) Các hành động thực sự (Click, Type) trên một trang cụ thể
└── test/java/                 # Nơi chứa kịch bản kiểm thử (Người đạo diễn)
    ├── core/                  # Thiết lập sân khấu trước mỗi bài test (BaseTest)
    ├── demoqa/                # Các bài test được phân nhóm theo từng tính năng
    └── testdata/              # Dữ liệu mẫu (DataFactory) dùng để bơm vào kịch bản
```

### 🧠 Những Mẫu Thiết Kế (Design Patterns) Đang Sử Dụng:
Nếu bạn muốn đạt trình độ "Professional", bạn phải hiểu tại sao framework lại viết như vậy:
1. **Singleton Pattern (`DriverManager`)**: Đảm bảo tại một thời điểm (trên 1 thread), chỉ có **DUY NHẤT** một trình duyệt (driver) được mở ra.
2. **Page Object Model (`...Page.java` & `...Test.java`)**: Tách biệt giữa người đạo diễn (Test Class) và diễn viên (Page Class). Đạo diễn chỉ nói "Hãy đăng nhập", diễn viên tự biết phải làm gì (tìm ô Textbox, điền chữ, nhấn nút).
3. **Fluent Interface (Method Chaining)**: Trong file Page, thay vì trả về `void`, các method trả về `this` (chính nó). Giúp viết code thành một chuỗi: `loginPage.enterUsername("admin").enterPassword("123").clickSubmit();`
4. **Builder Pattern (`TextboxFormData`)**: Dành cho các form có quá nhiều trường dữ liệu (First Name, Last Name, Email, Age, Salary, Dept), thay vì truyền 6 tham số vào 1 hàm, ta "cấu trúc" một cục dữ liệu và truyền vào một lần.

---

## 🚀 Phần 3: Hành Trình Người Mới (Beginner's Guide)

### 3.1. Phân Tích Kịch Bản Mẫu (AAA Pattern)
Mở một file test như `TextboxTest.java`, bạn sẽ thấy tư duy **AAA (Arrange - Act - Assert)**:

```java
@Test(description = "Verify user can submit valid data")
public void testValidTextboxSubmission() {
    // 1. Arrange: Chuẩn bị diễn viên, dữ liệu
    TextboxPage textboxPage = new TextboxPage(); 
    TextboxFormData validData = TestDataFactory.createValidTextboxData(); 

    // 2. Act: Thực hiện hành động nối tiếp nhau (Fluent Interface)
    textboxPage.enterFullName(validData.getFullName())
               .enterEmail(validData.getEmail())
               .enterCurrentAddress(validData.getCurrentAddress())
               .enterPermanentAddress(validData.getPermanentAddress())
               .clickSubmit(); // Tự động cuộn trang (AutoScroll) nhờ Listener!

    // 3. Assert: Kiểm tra kết quả
    Assert.assertEquals(textboxPage.getOutputName(), "Name:" + validData.getFullName());
}
```

### 3.2. Muốn Thêm 1 Bài Phím Bấm (Buttons) Mới, Phải Làm Gì?
Đừng viết tất cả vào 1 file! Hãy đi theo 3 bước sau:

**Bước 1: Ánh xạ phần tử (The Map)**
Vào `src/main/java/locator/...`, tạo `ButtonsLocators.java`. 
*Mọi XPath/CSS phải là biến `public static final By` và viết HOA TẤT CẢ.*
```java
public class ButtonsLocators {
    public static final By DOUBLE_CLICK_BTN = By.id("doubleClickBtn");
}
```

**Bước 2: Dạy máy tính cách bấm (The Skill)**
Vào `src/main/java/page/...`, tạo `ButtonsPage.java` kế thừa (`extends`) từ `BasePage`.
```java
public class ButtonsPage extends BasePage {
    public ButtonsPage doubleClick() {
        doubleClickButton(ButtonsLocators.DOUBLE_CLICK_BTN); // Hàm này BasePage đã viết giùm!
        return this; // Đừng quên return this
    }
}
```

**Bước 3: Viết kịch bản Test (The Script)**
Vào `src/test/java/demoqa/...`, tạo `ButtonsTest.java` kế thừa (`extends`) từ `BaseTest`.
Gọi lớp `ButtonsPage` và viết `Assert`. XONG!

---

## ⚡ Phần 4: Vươn Tới Chuyên Nghiệp (Advanced Professional)

Nếu bạn tò mò: *"Tại sao click mãi không được?"* hay *"Tại sao test chạy lúc pass lúc fail (Flaky Test)?"*, thì đây là bí kíp của chuyên gia.

### 4.1. Core Engine Của Chúng Ta Hoạt Động Thế Nào?
Thay vì dùng `driver.findElement().click()` thô sơ, Framework này sử dụng một "chuỗi thừa kế" sâu sắc trong package `core.base`:

```text
Helper (Log, Đọc file, Tạo Random Data)
  └── ElementFinder (Các hàm tìm element cơ bản, check tồn tại)
      └── WaitHelper (Các hàm chờ thông minh: Chờ hiện, Wait for AJAX, Wait for Page Load)
          └── ElementInteraction (Gõ phím, Click, Mouse Hover)
              └── JavaScriptHelper (Cuộn trang, Tự tô màu element khi click)
                  └── WindowManager (Đổi Tab, Đổi Iframe, Bật Tắt Cửa Sổ)
                      └── WebElementInteraction...
                          └── ...
                              └── BasePage (Class cuối cùng gom mọi sức mạnh)
```
**Bài học**: Việc chia nhỏ thành từng Helper class áp dụng nguyên tắc **Single Responsibility (SRP)** - Mỗi class chỉ lo ĐÚNG một việc.

### 4.2. Giải Quyết "Flaky Tests" Với Explicit Waits
Không BAO GIỜ dùng `Thread.sleep(5000)` trong project này.
Thay vào đó, trong các hàm của `BasePage` (thừa kế từ `WaitHelper`), chúng ta gọi:
- `waitForElementToBeClickable(locator)`: Đợi đến khi nút bấm có thể nhân được.
- `waitForElementToBeVisible(locator)`: Đợi nút hiển thị.
- `waitForPageLoad()`: Đợi vòng xoay trang load xong.

### 4.3. Sức Mạnh Của Event Listeners (`AutoScrollListener`)
Đã bao giờ bạn click một nút nhưng Selenium báo lỗi "Element is not clickable at point" vì cái nút đang bị che ngất dưới chân trang?
- Framework xử lý việc đó ở Tầng Lõi bằng file `AutoScrollListener.java`. 
- Khi bạn gọi lệnh Click, trước khi Selenium bấm thật, event listener sẽ "bắn tín hiệu" bóp cò báo Javascript cuộn chuột (ScrollIntoView) xuống chính xác vị trí cái nút rồi mới click. 
- Ngay trong phần khởi tạo, Test Class thậm chí không biết điều này đang xảy ra! 

---

## 🛡️ Tóm Lại 5 Quy Tắc Vàng (Golden Rules)
1. **Tuyệt đối KHÔNG assert trong file Page.** Trách nhiệm của Page là thao tác (Click), không phải Cảnh Sát để kiểm tra (Assert). Cảnh Sát phải ở file Test.
2. **Tuyệt đối KHÔNG viết xpath cứng (hardcode) trong Test hay Page.** Tất cả phải gom vào file `...Locators.java`.
3. Tên package phải viết `thườngtấtcả`, không gạch dưới. (`locator.demoqa.alertsframewindows`).
4. Khai báo Method phải là `camelCase`, Class là `PascalCase`, Constants là `UPPER_SNAKE_CASE`.
5. Đừng chạy test thô bằng tay. Mở Terminal và gõ: 
   `mvn clean test -Dheadless=true` để chứng tỏ bạn là Master of Automation.

Chúc bạn có một chuyến phiêu lưu tuyệt vời với DemoQA Framework!
