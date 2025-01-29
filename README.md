
# ![Logo](https://github.com/JairusChrisnie/AGOS2/blob/master/ResizedAgosLogo2.png?raw=true)  AGOS: A PUP Ferry Terminal Information Management
![image alt](https://github.com/JairusChrisnie/AGOS2/blob/master/PASIG%20RIVER%20FERRY%20SERVICE.png?raw=true)

## Description 
**Project AGOS** is a PUP Ferry Terminal Information Management designed to offer centralized and organized information about the ferry’s scheduled trips while implementing the concept of **Abstraction** in Object-Oriented Programming (OOP). This system aims to provide necessary details that will be beneficial for both the **passengers** and the **ferry’s operators**.

---

## Table of Contents  
- [Overview](#overview)  
- [Features](#features)  
- [Installation](#installation)  
- [Usage](#usage)  
- [Contributing](#contributing)  
- [License](#license)  
- [Acknowledgments](#acknowledgments)  

---

## Overview  
The **Pasig Ferry Information System** is a desktop application designed to streamline ferry schedule management for administrators and provide real-time schedule access for passengers. Built with Java Swing and MySQL, it offers:  
- **Admin Dashboard**: Manage ferry trips, add passengers, and edit schedules.  
- **Passenger Dashboard**: View schedules, station maps, and emergency contacts.  

---

## Features ✨  
### **Admin Dashboard**  
- 📅 Add, edit, or delete ferry trips.  
- 👥 Register passenger details for specific trips.  
- 🔄 Auto-generate unique Trip IDs based on route (Upstream/Downstream).  
- 🗃️ Archive deleted trips and passenger data.  

### **Passenger Dashboard**  
- 🚤 View real-time ferry schedules.  
- 🗺️ Access station maps and route information.  
- 🆘 Emergency contacts and system FAQs.  

---

## Installation 🛠️  
### **Prerequisites**  
- Java JDK 8+  
- MySQL Server  
- MySQL Connector/J (included in the `lib` folder)  

### **Steps**  
1. **Clone the Repository**  
   ```bash  
   git clone https://github.com/your-username/pasig-ferry-system.git   

2. **Set Up the Database**  
   - Create a MySQL database named `ferryDB`.  
   - Run the provided SQL script (`ferry_schema.sql`) to create tables.  

3. **Configure Database Credentials**  
   Update the JDBC connection details in:  
   - `adminDB.java`  
   - `PassengerInformation.java`  
   - `passengerDashboard.java`  
   ```java  
   // Example: Change credentials if needed  
   Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ferryDB", "root", "root");  
   ```  

4. **Run the Application**  
   Compile and execute the `loginPage.java` file.  

---

## Usage 🖥️  
1. **Launch the Application**  
   - Run `loginPage.java` to start the system.  

2. **Admin Login**  
   - **Username**: `adminPUP`  
   - **Password**: `passwordPUP`  
   - Manage trips, add passengers, and save changes to the database.  

3. **Passenger Access**  
   - Click the **Passenger** button on the welcome screen to view schedules and station info.  

![Admin Dashboard Preview](https://github.com/JairusChrisnie/AGOS2/blob/master/adminView.png?raw=true) 

---

## Contributing 🤝  
Contributions are welcome!  
1. Fork the repository.  
2. Create a feature branch:  
   ```bash  
   git checkout -b feature/new-feature  
   ```  
3. Commit your changes.  
4. Push to the branch and open a Pull Request.  


---

## License 📄  
This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.  

---

## Acknowledgments 🙌  
- Icons from [FlatIcon](https://www.flaticon.com).  
- MySQL for database management.  
- Java Swing for the GUI framework.  

---

**Prof. Chris Piamonte**: Special thanks to our professor for their guidance, advice, and support throughout the development of this project. The developers of this project would forever cherish the lessons — both academic and life lessons — that they have imparted throughout the semester.

**Members of Abstractly Yours, ANJYLC**: This project would not have been possible without the continuous effort and collaboration of each member:
- **Yvan Raphael C. Bombola**: Lead Developer
- **Nethan Edry L. Gragas**: Co-Lead Developer
- **Jairus Chrisnie R. Rimon**: Developer
- **John Paul T. Tamares**: Developer
- **Alliza Leira L. Lasac**: Documentation
- **Kristina Casandra C. Nagera**: Documentation

With countless late nights and tireless endeavours, the team worked hard to ensure the successful completion of this project.


![image](https://github.com/user-attachments/assets/ec826ba1-2acd-413b-a659-33fa33ff78fb)
---

**Contact**  
For questions or support, email: [your-email@example.com](mailto:your-email@example.com)  
``` 







