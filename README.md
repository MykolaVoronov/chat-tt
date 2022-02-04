# Chat

This web application simulates the operation of a chat. 
Through it, you can log in, log out, send messages.

# Technologies

- Apache Tomcat
- Maven
- JDBC
- MySQL
- Servlet API
- JSTL
- JSP

# Setup

1. Install and configure MySQL & MySQL Workbench
2. Install and configure Tomcat 9.0.56
3. In MySQL Workbench, execute the script from resources/init_db.sql
4. Link the database to the application:

    * *In the /util/ConnectionUtil.java, change the USERNAME, PASSWORD, and URL constants to the ones you used when configuring MySQL. JDBC_DRIVER change if you are using another DBMS.*
   
5. After that you can run the application ;)