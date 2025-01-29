
# ![Logo](https://github.com/JairusChrisnie/AGOS2/blob/master/ResizedAgosLogo2.png?raw=true)  AGOS: A PUP Ferry Terminal Information Management
![image alt](https://github.com/JairusChrisnie/AGOS2/blob/master/PASIG%20RIVER%20FERRY%20SERVICE.png?raw=true)

## Description 
**Project AGOS** is a PUP Ferry Terminal Information Management designed to offer centralized and organized information about the ferry’s scheduled trips while implementing the concept of **Abstraction** in Object-Oriented Programming (OOP). This system aims to provide necessary details that will be beneficial for both the **passengers** and the **ferry’s operators**.

## Installation Instructions

### Prerequisite
Here are the following programs you’ll need to run the program:

- **Java Development Kit (JDK)**  
  [Download JDK](https://www.oracle.com/ph/java/technologies/downloads/)
  
- **Visual Studio Code (VS Code)**  
  With Java Extensions installed  
  [Download VS Code](https://code.visualstudio.com/download)

- **Eclipse**  
  [Download Eclipse](https://www.eclipse.org/downloads/)

After making sure the mentioned software is installed, you can now proceed to running the code. 

### Using VS Code
To use VS Code to run the program, follow the steps below:
1. Clone the repository from GitHub.
2. Open VS Code and load the project folder.
3. You can use either of the two main ways to run the program:
   - **Using the terminal**:  
     Once the terminal is open, compile the Java files using `javac`.  
     Run the application afterwards.
   - **Using the Run and Debug button**:  
     Click the **Run and Debug** button to run the program directly in VS Code.

### Using Eclipse
If you are using Eclipse, follow these steps to run the program:

## Usage

### **Choose**
Whether the user is an **admin** or **passenger**.  
- If **admin**, they will be redirected to the admin portal.  
- If **passenger**, they will be redirected to the passenger view. 

![image alt](https://github.com/JairusChrisnie/AGOS2/blob/master/welcomeAgos.png?raw=true) 

### **Admin**
If the user is an **admin**, click the admin option and enter the required credentials.  
Upon successful login, the admin can access and preview the current ferry schedule.  
The admin is prompted with the option to **add/edit**.  

### **Add/Edit Schedule**
If the admin selects ‘add/edit’ to revise the ferry’s schedule, they will be directed to a new section/table with additional options:  
- **Add Row**  
- **Delete**  
- **Edit**  
- **Add Passenger**  
- **Save**  

#### **Add Row**
The admin can add rows to insert necessary details (_Trip ID, Body No, Route, Location, Estimated Time of Arrival, Seats Available, and Status_) in the system.  

![image alt](https://github.com/JairusChrisnie/AGOS2/blob/master/adminAdd.png?raw=true)

#### **Delete**
The admin can select and delete existing rows that are no longer needed. A confirmation prompt will be implemented.  

![image alt](https://github.com/user-attachments/assets/2b9230ab-2385-4163-908c-7b711e18bd7b)

#### **Edit**
The admin can modify the information in existing rows, such as editing the ETA, Seats Available, and Status.  

![image alt](https://github.com/JairusChrisnie/AGOS2/blob/master/adminEdit.png?raw=true)

#### **Add Passenger**
Upon clicking, an information panel will appear. The admin can now proceed to input passenger information.  

![image alt](https://github.com/JairusChrisnie/AGOS2/blob/master/passengerAddInfo.png?raw=true)

### **Logout**
Once the admin has finalized the schedule, they can log out of the system.  

![image alt](https://github.com/JairusChrisnie/AGOS2/blob/master/adminLogOut.png?raw=true)

## **Passenger**
By clicking the **‘Passenger’** button, the passenger can immediately access the current schedule.  
Other than the **‘Schedule’**, the passenger also has two other options:  

![image alt](https://github.com/JairusChrisnie/AGOS2/blob/master/passengerSched.png?raw=true)

### **Stations** 
The passenger can acquire a **map** of the Pasig River, complete with each ferry station and the landmarks near them.  

![image alt](https://github.com/JairusChrisnie/AGOS2/blob/master/passengerStations.png?raw=true)

#### **Help**
The passenger is given help and contact information for any **inquiries/emergencies**. Information about the Pasig River Ferry Service is also provided.

![image alt](https://github.com/JairusChrisnie/AGOS2/blob/master/passengerHelp.png?raw=true)

## Contributing

Contributions from the community are always welcome! To contribute, follow these instructions:

1. **Fork the repository** – This creates a personal copy of the code on your own GitHub account for development.
2. **Create a new branch** – This ensures that adding new features or codes won’t conflict with the main codebase.
3. **Make the fix/Add the feature** – Implement your changes and commit them with messages.
4. **Push to the branch** – Upload your changes to your forked repository.
5. **Submit a pull request** – This makes a request to the project managers for merging your forked repository to the main repository for review.

The project managers thank the future developers who continue to improve this project!

## License

This project is licensed under the MIT License - see the [MIT LICENSE](https://github.com/JairusChrisnie/AGOS2/blob/master/MIT%20License.txt) file for details.

## Acknowledgement

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







