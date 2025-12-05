# Bill Tracker - Cross-Platform Monthly Expense Manager

Bill Tracker is a modern, cross-platform application designed to help users manage their recurring monthly bills efficiently. Built with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**, it runs seamlessly on **Android**, **iOS**, and **Desktop (Windows/macOS/Linux)** from a single codebase.

## 🚀 Features

### Core Functionality
- **Recurring Bill Management**: Create templates for monthly bills (e.g., Netflix, Rent, Electricity).
- **Monthly Tracking**: Automatically generates a checklist for each month based on your templates.
- **Payment Status**: Mark bills as paid/unpaid and track payment dates.
- **Smart Actions**:
    - **Open Payment URL**: Directly open the bill payment page in the browser.
    - **Copy URL**: Quickly copy the payment link to the clipboard.
- **CRUD Operations**: Easily add, edit, and delete bill templates.

### UI/UX & Customization
- **Modern Dashboard**: A beautiful, card-based interface with a gradient summary report.
- **Visual Reports**: Interactive circular progress indicator showing monthly payment status (Total, Paid, Remaining).
- **Multi-Currency Support**: Switch between **Turkish Lira (₺)**, **US Dollar ($)**, and **Euro (€)**.
- **Theme Support**: Choose between **Light**, **Dark**, or **System Default** themes.
- **Localization**: Fully localized in **Turkish (Türkçe)**, **English**, and **Spanish (Español)**.
- **Responsive Design**: Optimized layouts for both mobile (vertical stack) and desktop (spacious cards).

## 🛠 Tech Stack & Architecture

This project demonstrates a production-ready implementation of Kotlin Multiplatform.

### Technologies
- **Language**: [Kotlin](https://kotlinlang.org/) (100% Kotlin codebase)
- **UI Framework**: [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) (Declarative UI shared across platforms)
- **Dependency Injection**: [Koin](https://insert-koin.io/) (Lightweight, Kotlin-first DI)
- **Database**: [Room Multiplatform](https://developer.android.com/kotlin/multiplatform/room) (SQLite abstraction with full KMP support)
- **Asynchronous Programming**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
- **Navigation**: Custom ViewModel-driven state management.

### Architecture
The project follows **Clean Architecture** principles with **MVVM (Model-View-ViewModel)** pattern:

- **Data Layer**:
    - `BillTemplate` & `BillTransaction` (Entities)
    - `BillDao` (Room Database Access)
    - `BillRepository` (Single Source of Truth, handles data merging and persistence)
- **Domain/Business Logic**:
    - Encapsulated within the Repository and ViewModel.
    - Logic for merging templates with monthly transactions.
- **UI Layer**:
    - `MainViewModel`: Manages UI state (`bills`, `summary`, `currency`, `theme`, `language`).
    - `DashboardScreen`: Main composable observing ViewModel state.
    - `Components`: Reusable UI elements (`BillCard`, `SummaryCard`, `AddBillDialog`).

## 📱 Platforms Supported

- **Android**: Native Android app (minSdk 24).
- **iOS**: Native iOS app (via Kotlin/Native & Compose).
- **Desktop**: JVM-based desktop application (Windows, macOS, Linux).

## 📦 Installation & Setup

### Prerequisites
- **JDK 17** or higher (Required for Room & AGP).
- **Android Studio** (for Android/Desktop dev) or **Fleet**.
- **Xcode** (for iOS dev, macOS only).

### Running the App

#### Desktop
Run the following command in the terminal:
```bash
# Windows
.\gradlew.bat :composeApp:run

# Mac/Linux
./gradlew :composeApp:run
```

#### Android
Connect a device or start an emulator, then run:
```bash
# Windows
.\gradlew.bat :composeApp:installDebug

# Mac/Linux
./gradlew :composeApp:installDebug
```

#### iOS
1. Open `iosApp/iosApp.xcodeproj` in Xcode.
2. Ensure the shared framework is built (`./gradlew :composeApp:embedAndSignAppleFrameworkForXcode`).
3. Run on a Simulator or Device.

## 🎨 Screenshots

## 📄 License

This project is open-source and available under the MIT License.
