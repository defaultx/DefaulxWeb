import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.sql.*;
import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;
import static javax.mail.Transport.send;

/**
 * Created by rahul on 25/12/2015.
 */

@WebServlet("/submit")
public class register extends HttpServlet {
    private String databaseURL, username, password;

    /**
     * function to send html email to user
     *
     * @param email
     */
    public static void sendEmail(String email, int msg) {
        // Recipient's email ID needs to be mentioned.
        String to = email;

        // Sender's email ID needs to be mentioned
        String from = "noreply@keylesskey.com";

        // Assuming you are sending email from localhost
        String host = "192.169.1.15";//"localhost";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);

        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Keyless Key Pass Code");

            // Send the actual HTML message, as big as you like
            message.setContent("<h1>Here is you app pass code: "+ msg + "</h1>", "text/html");

            // Send message
            send(message);
            System.out.println("Sent message successfully....");
        } catch (javax.mail.MessagingException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        ServletContext context = config.getServletContext();
        databaseURL = context.getInitParameter("databaseURL");
        username = context.getInitParameter("username");
        password = context.getInitParameter("password");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;
        String sqlStr = null;

        try {
            // Retrieve and process request parameters
            String fullName = request.getParameter("fname").trim();
            String email = request.getParameter("email").trim();
            String roomNum = request.getParameter("room").trim();

            // We shall build our output in a buffer, so that it will not be interrupted
            //  by error messages.
            StringBuilder outBuf = new StringBuilder();
            // Display the name, email and phone

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(databaseURL, username, password);
            stmt = conn.createStatement();
            // We shall manage our transaction (because multiple SQL statements issued)
            conn.setAutoCommit(false);

            Random rand = new SecureRandom();
            int min = 10000;
            int max = 99999;
            int pass = rand.nextInt(max - min);

            out.println("<html><head><title>Confirmation</title></head><body style=\"background-color:lightgreen;\">");
            out.println("<h2 align=center>Thank you for booking with us "+fullName+"</h2>");
            out.println("<h1 align=center>Your app code is: " + pass + "</h1>");
            out.println("<p align=center>Please download our app and use this code for your room key!</p>");
            out.println("<p align=center>We hope you enjoy your stay and our technology</p>");
            out.println("</body></html>");

            sqlStr = "INSERT INTO users (fname, email, pass, room, active)values ("
                    + "'" + fullName + "', '" + email + "', '" + pass + "', '" + roomNum + "', '" + "no"+"')";
            System.out.println(sqlStr);  // for debugging
            stmt.executeUpdate(sqlStr);

            conn.commit();
            sendEmail(email,pass);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
