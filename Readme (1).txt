Hospital Management System (HMS)
Project Overview
The Hospital Management System (HMS) is a Java-based command-line application aimed at automating hospital operations. It facilitates efficient management of hospital resources, enhances patient care, and streamlines administrative processes. This system includes various roles such as Patient, Doctor, Pharmacist, and Administrator, each with specific capabilities to ensure proper management of appointments, medical records, and hospital inventory.
Features
1. User Roles
* Patient: Patients can view and manage their medical records, schedule, reschedule, and cancel appointments, and view appointment outcome records.
* Doctor: Doctors can manage their availability, view and update patient medical records, accept or decline appointment requests, and record appointment outcomes.
* Pharmacist: Pharmacists can view appointment outcomes to fulfill prescriptions, update prescription statuses, view medication inventory, and submit replenishment requests.
* Administrator: Administrators can manage hospital staff, view and update medication inventory, approve replenishment requests, and view all appointments.
2. System Capabilities
* User Management: Each user must log in using their unique hospital ID and a password. First-time users must change their default password.
* Medical Record Management: Doctors can view and update the medical records of patients under their care.
* Appointment Management: Patients can schedule, reschedule, or cancel appointments with doctors. Doctors can accept or decline appointment requests and manage their availability.
* Inventory Management: Pharmacists and administrators can manage the inventory of medications.
* Notification System: The system allows sending notifications (e.g., appointment confirmations).
Project Structure
The project is organized into different packages, each serving a specific role:
* com.hms.user: Contains classes for various user roles such as Patient, Doctor, Pharmacist, and Administrator.
* com.hms.appointment: Contains classes related to appointment handling, such as Appointment, AppointmentManager, and AppointmentOutcomeRecord.
* com.hms.inventory: Handles the medication inventory through classes like InventoryManager and Medicine.
* com.hms.util: Utility classes such as ContactInfo for storing user contact details.
Key Classes and Methods
* User: Abstract class for common user attributes and login functionality.
* Patient: Concrete implementation of User for patient-specific actions like viewing and scheduling appointments.
* Doctor: Provides functionality for managing patient records, setting availability, and managing appointments.
* Pharmacist: Handles viewing appointment outcomes, updating prescription statuses, and inventory management.
* Administrator: Manages hospital staff and inventory.
* AppointmentManager: Implements the core functionality for managing appointments and appointment outcomes.
Setup Instructions
1. Prerequisites:
   * Java Development Kit (JDK) 11 or higher.
   * Any Java IDE (e.g., IntelliJ IDEA, Eclipse) or a text editor with command-line capabilities.
2. Project Setup:
   * Clone or download the project from the repository.
   * Open the project in your preferred IDE.
   * Ensure the required .csv files (Staff_List.csv, Patient_List.csv, Medicine_List.csv) are placed under src/hms/data for system initialization.
3. Running the Application:
   * Run the NewLoginSystem.java file located in the com.hms.user package to start the application.
   * Follow the instructions in the command-line interface to log in and interact with the system based on your role.
Usage Instructions
* Login: Use your unique hospital ID and password to log in. For first-time users, use the default password and change it after the first login.
* Patient Actions: View medical records, schedule or reschedule appointments, and manage personal information.
* Doctor Actions: View patient records, manage appointments, and record outcomes.
* Pharmacist Actions: View appointment outcomes, update prescription status, and manage inventory.
* Administrator Actions: Manage staff, inventory, and appointment details.
CSV Files
The system relies on .csv files to initialize data:
* Staff_List.csv: Contains initial information about doctors, pharmacists, and administrators.
* Patient_List.csv: Contains patient information.
* Medicine_List.csv: Contains the inventory of medications and their stock levels.
Future Enhancements
* Graphical User Interface (GUI): Replace the command-line interface with a user-friendly GUI.
* Database Integration: Replace the .csv files with a proper database for more efficient data management.
* Notification Enhancements: Integrate SMS and email notifications for patients and staff.
* Report Generation: Add the capability for generating reports on patient histories, staff schedules, and inventory levels.
Contributing
If you wish to contribute to the project, please follow these steps:
1. Fork the repository.
2. Create a new feature branch (git checkout -b feature-branch-name).
3. Commit your changes (git commit -m 'Add feature').
4. Push to the branch (git push origin feature-branch-name).
5. Create a Pull Request.
License
This project is open source and available under the MIT License.