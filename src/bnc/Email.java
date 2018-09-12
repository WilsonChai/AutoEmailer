package bnc;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.*;

public class Email {

    private int year = Calendar.getInstance().get(Calendar.YEAR);
    private static String date;
    private String client;
    private String email;
    private String subject;
    private StringBuilder emailContent;

    public Email(String client) {
        this.client = client;
        this.email = client + "@email.com";
        emailContent = new StringBuilder("Invoice No:                      Amount:\n");
    }


    public void generateSubject(String date) {
        this.subject = "EFT payment Date " + date + "-" + year;
    }

    static void sendEmail() {
        // Recipient's email ID needs to be mentioned.
        String to = "nosecsplz@hotmail.com";

        // Sender's email ID needs to be mentioned
        String from = "secsplz@hotmail.com";
        final String username = "secsplz@hotmail.com";
        final String password = "137946";

        String host = "smtp-mail.outlook.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject("Testing Subject");

            // Now set the actual message
            message.setText("Hello, this is sample for to check send " +
                    "email using JavaMailAPI ");

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (
                MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    static Map<String, Email> generateEmail() throws Exception {

        Map<String, Email> emails = new HashMap<>();

        // Reads Excel file sheets and store into cells[][]
        Workbook workbook = Workbook.getWorkbook(new File("test.xls"));
        Sheet sheet = workbook.getSheet(0);
        int maxRows = sheet.getRows();

        Cell[][] cells = new Cell[7][maxRows + 1];

        for (int column = 0; column < 7; column++) {
            for (int row = 0; row < maxRows; row++) {
                cells[column][row] = sheet.getCell(column, row);
            }
        }

        workbook.close();

        // Finds the last month's statement and remembers position
        int position = 0;

        for (int i = 1; i < maxRows; i++) {

            if (cells[1][i].getContents() != "") {
                date = cells[1][i].getContents();
                position = i;
            }
        }

        String currentClient = "";

        // Generates all the email contents
        for (int i = position; i < maxRows; i++) {

            if (cells[2][i].getContents() != "" && cells[4][i].getContents() != "") { // name !empty, amount !empty
                currentClient = cells[2][i].getContents();
                emails.put(currentClient, new Email(currentClient));
                emails.get(currentClient).generateSubject(date);
                emails.get(currentClient).setEmailContent(cells[3][i].getContents(), cells[4][i].getContents().replaceAll("\"", ""));

                if (cells[2][i + 1].getContents() != "" && cells[3][i + 1].getContents() != "") { //  nextName != empty && nextInvoice != empty
                    emails.get(currentClient).setEmailContent(cells[4][i].getContents().replaceAll("\"", ""));

                } else if (cells[2][i + 1].getContents() == "" && cells[3][i + 1].getContents() == "") {
                    emails.get(currentClient).setEmailContent(cells[4][i].getContents().replaceAll("\"", ""));
                }

            } else if (cells[2][i].getContents() == "" && cells[3][i].getContents() != "" && cells[4][i].getContents() != "") {
                emails.get(currentClient).setEmailContent(cells[3][i].getContents(), cells[4][i].getContents().replaceAll("\"", ""));

                if (cells[6][i].getContents() != "") {
                    emails.get(currentClient).setEmailContent(cells[6][i].getContents().replaceAll("\"", ""));
                }
            }
        }
        return emails;
    }


    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public String getClient() {
        return client;
    }

    public StringBuilder getEmailContent() {
        return emailContent;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmailContent(String invoice, String amount) {
        Formatter formatter = new Formatter(this.emailContent, Locale.US);
        formatter.format("%-30s %10s\n", invoice.trim(), amount.trim());
    }

    public void setEmailContent(String totalAmount) {
        Formatter formatter = new Formatter(this.emailContent, Locale.US);
        formatter.format("\nTotal Amount Transferred:  %s\n", totalAmount);
        formatter.format("Date of Transfer: " + date + "-" + year + "\n");
    }

    public void setEmailContent(StringBuilder emailContent) {
        this.emailContent = new StringBuilder(emailContent);
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}

