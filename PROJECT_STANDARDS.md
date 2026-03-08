# Hướng Dẫn Chuẩn Coding & Cấu Trúc Dự Án Automation Test

Tài liệu này quy định các tiêu chuẩn về cấu trúc project, cách đặt tên và quy tắc viết code (coding conventions) để áp dụng cho dự án DemoQA và các dự án Automation Test Java/Selenium sau này.

---

## 1. Cấu Trúc Dự Án (Project Structure)

Dự án tuân theo mô hình **Page Object Model (POM)** tách biệt rõ ràng giữa logic thao tác (Pages), định danh phần tử (Locators), và kịch bản kiểm thử (Tests).

```text
src
├── main
│   └── java
│       ├── core          # Chứa các lớp cốt lõi (BasePage, DriverManager, Configurations)
│       ├── locator      # Chứa các interface/class lưu trữ Locator (XPath/CSS)
│       ├── model        # Chứa POJO, Enums mô tả dữ liệu (Data Models)
│       ├── page         # Chứa logic thao tác trên trang (Page Objects)
│       ├── common          # Các tiện ích chung (Helpers, Constants, File Readers)
│       └── steps         # (Optional) Các bước logic nghiệp vụ phức tạp gộp nhiều page
└── test
    └── java
        ├── elements      # Các Test Class phân theo chức năng/màn hình
        ├── forms         # ...
        ├── widgets       # ...
        └── core          # BaseTest và các cấu hình Test Runner
```

### Quy tắc lưu trữ Package:
- **Tên package**: Viết thường toàn bộ (`model.enums`, `page.demoqa.elements`). Tránh dùng snake_case (gạch dưới) trong tên package nếu có thể.
- **Phân chia logic**:
  - `page`: Chỉ chứa hành động (click, type, get text). **KHÔNG** chứa Assertions.
  - `tests`: Chỉ chứa Assertions và luồng test. **KHÔNG** chứa driver calls trực tiếp (driver.findElement).
  - `locator`: Tách biệt locator ra khỏi Page class để dễ bảo trì.

---

## 2. Quy Tắc Đặt Tên (Naming Conventions)

### Classes & Interfaces
- Format: **PascalCase**
- Ví dụ: `LoginPage`, `WebTablesTest`, `User user`, `BaseTest`.
- Hậu tố:
  - Page: `...Page` (e.g., `HomePage`)
  - Test: `...Test` (e.g., `LoginTest`)
  - Utility: `...Utils` hoặc `...Helper` (e.g., `DateUtils`)

### Methods
- Format: **camelCase**
- Nên bắt đầu bằng động từ: `clickButton()`, `verifyLoginSuccess()`, `getUserName()`.
- Test Methods: Nên mô tả rõ mục đích test.
  - Ví dụ: `verifySortFirstNameAscending()`, `shouldLoginSuccessfullyWithValidCredentials()`.

### Variables
- Format: **camelCase**
- Rõ nghĩa: `firstName` thay vì `fn`, `userList` thay vì `ul`.

### Constants (Hằng số)
- Format: **UPPER_SNAKE_CASE**
- Ví dụ: `DEFAULT_TIMEOUT`, `BASE_URL`.

### Locators
- Format: **UPPER_SNAKE_CASE** (nếu để trong Interface/Static) hoặc **camelCase** (nếu dùng `By` field).
- Khuyến nghị dùng pattern static constant trong class `Locators`.
- Ví dụ: `public static final By LOGIN_BUTTON = By.id("login");`

---

## 3. Hướng Dẫn Viết Code (Coding Guidelines)

### 3.1 Page Objects (Pages)
- **Kế thừa**: Các Page class phải kế thừa từ `BasePage`.
- **Fluent Interface**: Các method thực hiện hành động (click, type) nên trả về `this` (hoặc Page tiếp theo) để cho phép gọi chuỗi (chaining).
- **Log**: Luôn ghi log cho các hành động quan trọng.
- **Wait**: Sử dụng Explicit Wait (WebDriverWait) cho các hành động tương tác, tránh `Thread.sleep()`.

**Mẫu (Template):**

```java
package page.demoqa.elements;

import core.BasePage;
import locator.demoqa.elements.ButtonsLocators;
import org.openqa.selenium.By;

public class ButtonsPage extends BasePage {

    // Constructor gọi super() để khởi tạo driver/wait từ BasePage
    public ButtonsPage() {
        super();
    }

    // Method hành động - Trả về this để chaining
    public ButtonsPage clickDoubleClickButton() {
        logger.info("Double clicking on the button"); // Luôn có log
        // Sử dụng method wrapper trong BasePage (nếu có) thay vì driver trực tiếp
        doubleClick(ButtonsLocators.DOUBLE_CLICK_BTN); 
        return this;
    }

    public ButtonsPage clickRightClickButton() {
        logger.info("Right clicking on the button");
        rightClick(ButtonsLocators.RIGHT_CLICK_BTN);
        return this;
    }

    // Method lấy thông tin - Trả về dữ liệu (String, int, boolean)
    public String getDoubleClickMessage() {
        return getText(ButtonsLocators.DOUBLE_CLICK_MSG);
    }
}
```

### 3.2 Locators
- Nên tách riêng file Locators để dễ quản lý khi UI thay đổi.
- Có thể dùng `class` chứa biến `public static final By`.

**Mẫu (Template):**

```java
package locator.demoqa.elements;

import org.openqa.selenium.By;

public class ButtonsLocators {
    // Đặt tên biến gợi nhớ loại element (BTN, INPUT, MSG, LBL)
    public static final By DOUBLE_CLICK_BTN = By.id("doubleClickBtn");
    public static final By RIGHT_CLICK_BTN = By.id("rightClickBtn");
    public static final By DOUBLE_CLICK_MSG = By.id("doubleClickMessage");
}
```

### 3.3 Test Classes
- **Kế thừa**: Kế thừa `BaseTest` để tận dụng `@BeforeMethod`, `@AfterMethod` (setup/teardown driver).
- **AAA Pattern**: Arrange (Chuẩn bị) -> Act (Thực hiện) -> Assert (Kiểm tra).
- **Độc lập**: Các bài test nên độc lập, không phụ thuộc trạng thái của nhau.
- **Assertions**: Dùng TestNG (`Assert.assertEquals`, `Assert.assertTrue`). Có msg lỗi rõ ràng.
- **KHÔNG Logic**: Tuyệt đối **KHÔNG** viết logic xử lý (vòng lặp for/while, câu lệnh if-else phức tạp, xử lý chuỗi) trong Test Class. 
    - Logic này phải được đóng gói trong Page hoặc Helper. 
    - Test Class chỉ nên đọc như một kịch bản đơn giản (Step 1 -> Step 2 -> Checks).
    - Mục đích: Giúp Test dễ đọc, dễ bảo trì và tái sử dụng logic ở nhiều test case khác nhau.

**Mẫu (Template):**

```java
package elements;

import core.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import page.demoqa.elements.ButtonsPage;

public class ButtonsTest extends BaseTest {

    @Test(description = "Verify double click works correctly")
    void testDoubleClick() {
        // Arrange
        ButtonsPage buttonsPage = new ButtonsPage();
        // buttonsPage.open(); // Giả sử có hàm open (hoặc đã mở từ BeforeMethod nếu config)

        // Act
        buttonsPage.clickDoubleClickButton();

        // Assert
        String actualMsg = buttonsPage.getDoubleClickMessage();
        Assert.assertEquals(actualMsg, "You have done a double click", "Double click message should match");
    }
}
```

### 3.4 Data Models
- Sử dụng POJO (Plain Old Java Object) để đại diện cho các đối tượng dữ liệu phức tạp (User, Form Data).
- Sử dụng Lombok (nếu có) để giảm boilerplate code.

**Mẫu (Template):**

```java
package model;

public class User {
    private String firstName;
    private String lastName;
    private String email;

    // Constructor, Getters, Setters
    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
    
    // ... getters/setters
}
```

---

## 4. Các Best Practices Khác

1.  **Code Format**: Luôn format code (Ctrl+Alt+L trong IntelliJ) trước khi commit.
2.  **Clean Code**:
    - Xóa các import thừa.
    - Không để code bị comment (dead code).
    - Tách hàm nếu hàm quá dài (> 20-30 dòng).
3.  **Comments**: Chỉ comment những đoạn logic phức tạp ("Tại sao" làm vậy), không comment code làm gì (vì code nên tự giải thích).
4.  **Version Control (Git)**:
    - Message commit rõ ràng: `[Page/Test] Description of changes`.
    - Không commit file `.class`, `target/`, `.idea/`.
5.  **Tách Biệt Logic (Separation of Concerns)**:
    - **Logic Code**: Nằm ở `BasePage`, `BaseTest`, `...Page`, `Helper`.
    - **Test Code**: `...Test` chỉ gọi functions và assert kết quả.
    - *Lý do*: Thay đổi UI chỉ cần sửa Page, thay đổi logic test chỉ cần sửa Test.

---

Tài liệu này là kim chỉ nam cho việc phát triển dự án. Mọi thành viên cần đọc kỹ và tuân thủ.
