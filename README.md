# PayLater - Your Personal Payment Reminder App

<img width="82" alt="Screenshot 2025-05-26 at 12 40 27 PM" src="https://github.com/user-attachments/assets/3fe7f6c5-f4fb-4eea-ac95-36ade3cf3e86" /> App v 1.0

PayLater is a minimalist and intuitive Android application designed to help you keep track of your pending payments and gently remind you when they're due. Built entirely with **Jetpack Compose**, **Room Database**, and **Hilt for Dependency Injection**, it offers a modern, reactive user experience.

## 🤔 When to Use PayLater?

PayLater is ideal for individuals or small businesses, like **retailers who frequently sell products or services on a credit basis**. It streamlines the process of managing receivables by providing:

* **Quick Entry:** Log payments given out immediately.
* **Automated Reminders:** Ensure timely follow-ups without manual tracking.
* **Clear Overview:** Easily see who owes what and how much is pending in total or per customer.


## ✨ Features

* **Effortless Payment Tracking:** Quickly add new payments with details like Payer Name, Contact Number, Payment Mode (Cash, UPI, Credit), and Amount.
* **Automated Reminders:** For all payments with a 'Pending' status (status 0), the app schedules recurring notifications every 5 days to remind you to settle them.
* **Smart Cancellation:** Once a payment's status changes to 'Completed' (status 1), its associated reminders are automatically cancelled.
* **Share Payment Details:** Easily share payment information with the payer using `Intent.ACTION_SEND`, facilitating quick communication and confirmation.
* **Comprehensive Overview:**
    * **Home Screen:** Displays a clear list of all your added payments.
    * **Group Screen:** Provides an aggregated view, grouping payments by 'Number' and showing the total amount due for each number, as well as the total pending amount for each number.
* **Modern Android Tech Stack:** Leverages the latest Android development tools and best practices.

## 📱 Screenshots

![Screenshot_20250526_123336](https://github.com/user-attachments/assets/431be81c-9e3e-4e21-8a2e-4f2082384516)
![s940](https://github.com/user-attachments/assets/4bf5efc8-1e33-4965-be7b-3d0115e351ba)
![s555](https://github.com/user-attachments/assets/4a33c5ac-d2b6-40d6-bd65-4096cc75c8ff)
![s444](https://github.com/user-attachments/assets/dbf5d1fd-2e2a-463f-9414-679db5cc835b)
![s333](https://github.com/user-attachments/assets/228e5df7-58c9-4d92-b7c9-154f4103cc95)
![s222](https://github.com/user-attachments/assets/f81ccf9a-bc54-498f-bba4-7a635b96878a)
![s93](https://github.com/user-attachments/assets/239c6fef-ad54-4b83-b2ef-ad8209abc44d)
![s83](https://github.com/user-attachments/assets/040a6d33-c0be-489e-89bd-caa717b19d7a)
![s56](https://github.com/user-attachments/assets/785c80f1-0c17-4de4-871b-f832480f6a41)
![s1](https://github.com/user-attachments/assets/c76e935a-af65-4e51-a114-7018b874f025)




## 🚀 Technologies Used

* **Kotlin:** Primary development language.
* **Jetpack Compose:** Modern toolkit for building native Android UI.
* **Android Architecture Components:**
    * **ViewModel:** Manages UI-related data in a lifecycle-conscious way.
    * **Room Persistence Library:** For local data storage, providing an abstraction layer over SQLite.
    * **Navigation Compose:** For navigating between screens.
* **Kotlin Coroutines & Flow:** For asynchronous operations and reactive data streams.
* **Hilt:** For robust and scalable dependency injection.
* **AlarmManager & BroadcastReceiver:** For scheduling and receiving background alarms for notifications.
* **Notifications API:** For user reminders.
* **Android Intents:** For sharing data with other applications.

## 🛠️ Setup and Installation

1.  **Clone the repository:**

    ``
        git clone https://github.com/wgnofi/PayLater
    ``
    
3.  **Open in Android Studio:**
    Open the cloned project in Android Studio.
4.  **Sync Gradle:**
    Allow Gradle to sync the project and download all necessary dependencies.
5.  **Run on Device/Emulator:**
    Run the application on an Android device (TIRAMISU or later Recommended) or an emulator.

### Important Notes:

* **Notification Permission:** On Android 13 (API 33) and above, the app will request the `POST_NOTIFICATIONS` permission. Ensure this is granted for reminders to appear.
* **Battery Optimization:** For reliable reminders, especially on devices running Android 6.0 (API 23) and higher, users might need to disable battery optimization for the app (though `setInexactRepeating` with `_WAKEUP` flags often helps).

## 💡 Learning & Development Showcase

This project demonstrates my proficiency in:

* Building responsive and modern UIs with Jetpack Compose, supporting **Dark Theme by default**.
* Implementing a clean MVVM architecture with a data `Repository` pattern.
* Handling local data persistence using Room Database.
* Managing asynchronous operations efficiently with Kotlin Coroutines and Flow.
* Applying dependency injection using Hilt for a scalable codebase.
* Implementing complex background tasks with `AlarmManager` and `BroadcastReceiver` for scheduled events.
* Designing and implementing user notifications.
* Utilizing Android Intents for inter-app communication (sharing).
* Understanding the Android component lifecycle and best practices for background work.

## 🛣️ Future Enhancements

* **Payment History:** Add a dedicated screen to view a detailed history of all payments, including completed ones.
* **Filtering and Sorting:** Allow users to filter payments by status, date range, or sort them by amount/date.
* **Categorization:** Enable assigning categories to payments.
* **Export Data:** Option to export payment data (to CSV or other formats).
* **Widget Support:** A home screen widget to show upcoming or pending payments at a glance.
* **Unit/Integration Tests:** Implement tests for ViewModel, Repository, and DAO layers.
* **Biometric Authentication:** Secure access to the app.
* **Improved Date Picker:** Use a more robust date picker for payment dates.
* **Data Synchronization:** Option to back up and restore data.

## 🤝 Contributing

Feel free to fork this repository, open issues, or submit pull requests. Any contributions are welcome!

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
