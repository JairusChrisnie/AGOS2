
# ![Logo](https://github.com/JairusChrisnie/AGOS2/blob/master/ResizedAgosLogo2.png?raw=true)  AGOS: A PUP Ferry Terminal Information Management

## Description 
**Project AGOS** is a PUP Ferry Terminal Information Management designed to offer centralized and organized information about the ferry’s scheduled trips while implementing the concept of **Abstraction** in Object-Oriented Programming (OOP). This system aims to provide necessary details that will be beneficial for both the **passengers** and the **ferry’s operators**.


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

### **Logout**
Once the admin has finalized the schedule, they can log out of the system.  
