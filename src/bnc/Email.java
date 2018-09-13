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

    // constructor
    public Email(String client) {
        this.client = client;
        this.email = "";
        emailContent = new StringBuilder("Invoice No:                      Amount:\n");
    }

    // methods
    private void generateSubject(String date) {
        this.subject = "EFT payment Date " + date + "-" + year;
    }

    static void sendEmail(String email, String subject, String emailContent) {
        // Recipient's email ID needs to be mentioned.
        String to = email;

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
            message.setSubject(subject);

            // Now set the actual message
            message.setText(emailContent);

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
        Workbook workbook = Workbook.getWorkbook(new File("EFT Information.xls"));
        Sheet statementSheet = workbook.getSheet(0);
        Sheet emailSheet = workbook.getSheet(1);
        int maxRows = statementSheet.getRows();

        // extracts all the cells in statement and store into statementCells array
        Cell[][] statementCells = new Cell[7][maxRows + 1];

        for (int column = 0; column < 7; column++) {
            for (int row = 0; row < maxRows; row++) {
                statementCells[column][row] = statementSheet.getCell(column, row);
            }
        }

        // extracts all the emails from emailSheet and store into HashMap
        Map<String, String> emailList = new HashMap<>();

        for (int row = 0; row < emailSheet.getRows(); row++) {
            if (emailSheet.getCell(0, row).getContents() != "" && emailSheet.getCell(6, row).getContents() != "") {
                if (!emailSheet.getCell(0, row).getContents().equals("Company Name")) {
                    emailList.put(
                            emailSheet.getCell(0, row).getContents().toLowerCase().replaceAll("\\W", ""),
                            emailSheet.getCell(6, row).getContents());
                }
            }
        }

//        for (Map.Entry<String, String> entry : emailList.entrySet())
//            System.out.println(entry.getKey() + " " + entry.getValue());

        workbook.close();

        // Finds the last month's statement and remembers position
        int position = 0;

        for (int i = 1; i < maxRows; i++) {

            if (statementCells[1][i].getContents() != "") {
                date = statementCells[1][i].getContents();
                position = i;
            }
        }

        String currentClient = "";

        // Generates all the email contents
        for (int i = position; i < maxRows; i++) {

            if (statementCells[2][i].getContents() != "" && statementCells[4][i].getContents() != "") { // name !empty, amount !empty
                currentClient = statementCells[2][i].getContents();
                emails.put(currentClient, new Email(currentClient));
                for (Map.Entry<String, String> e : emailList.entrySet()) {
                    if (e.getKey().startsWith(convertString(currentClient)) || e.getKey().contains(convertString(currentClient))) {
                        emails.get(currentClient).setEmail(e.getValue());
                    }
                }
                emails.get(currentClient).generateSubject(date);
                emails.get(currentClient).setEmailContent(statementCells[3][i].getContents(), statementCells[4][i].getContents().replaceAll("\"", ""));

                if (statementCells[2][i + 1].getContents() != "" && statementCells[3][i + 1].getContents() != "") { //  nextName != empty && nextInvoice != empty
                    emails.get(currentClient).setEmailContent(statementCells[4][i].getContents().replaceAll("\"", ""));

                } else if (statementCells[2][i + 1].getContents() == "" && statementCells[3][i + 1].getContents() == "") {
                    emails.get(currentClient).setEmailContent(statementCells[4][i].getContents().replaceAll("\"", ""));
                }

            } else if (statementCells[2][i].getContents() == "" && statementCells[3][i].getContents() != "" && statementCells[4][i].getContents() != "") {
                emails.get(currentClient).setEmailContent(statementCells[3][i].getContents(), statementCells[4][i].getContents().replaceAll("\"", ""));

                if (statementCells[6][i].getContents() != "") {
                    emails.get(currentClient).setEmailContent(statementCells[6][i].getContents().replaceAll("\"", ""));
                }
            }
        }
        return emails;
    }

    private static String convertString(String client) {
        return client.replaceAll("\\W", "").toLowerCase();
    }

    // getters
    String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    String getClient() {
        return client;
    }

    StringBuilder getEmailContent() {
        return emailContent;
    }

    // setters
    void setEmail(String email) {
        this.email = email;
    }

    private void setEmailContent(String invoice, String amount) {
        Formatter formatter = new Formatter(this.emailContent, Locale.US);
        formatter.format("%-30s %10s\n", invoice.trim(), amount.trim());
    }

    private void setEmailContent(String totalAmount) {
        Formatter formatter = new Formatter(this.emailContent, Locale.US);
        formatter.format("\nTotal Amount Transferred:  %s\n", totalAmount);
        formatter.format("Date of Transfer: " + date + "-" + year + "\n");
    }

    void setEmailContent(StringBuilder emailContent) {
        this.emailContent = new StringBuilder(emailContent);
    }

    void setSubject(String subject) {
        this.subject = subject;
    }
}

