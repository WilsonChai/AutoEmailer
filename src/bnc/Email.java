package bnc;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Email {

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

    static List<String> generateEmail() throws Exception {

        List<String> list = new ArrayList<>();
        list.add("test");
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
        String date = "";
        int position = 0;

        for (int i = 1; i < maxRows; i++) {

            if (cells[1][i].getContents() != "") {
                date = cells[1][i].getContents();
                position = i;
            }
        }

        // Generates all the email contents
        String currentClient;

        for (int i = position; i < maxRows; i++) {

            if (cells[2][i].getContents() != "" && cells[4][i].getContents() != "") { // name !empty, amount !empty
                currentClient = cells[2][i].getContents();
                System.out.println("Subject: EFT Payment Date " + date.replaceAll("-", " "));
                System.out.println("To customer: " + currentClient);
                System.out.println("     Invoice No:                       Amount:");
                System.out.printf("     %-20s%20s\n", cells[3][i].getContents(), cells[4][i].getContents().replaceAll("\"", ""));

                if (cells[2][i + 1].getContents() != "" && cells[3][i + 1].getContents() != "") { //  nextName != empty && nextInvoice != empty
                    System.out.printf("\nTotal Amount Transferred:  %18s\n", cells[4][i].getContents().replaceAll("\"", ""));
                    System.out.println("Date of Transfer: " + date + "-18");
                    System.out.println("----------------------------------------------------------------");

                } else if (cells[2][i + 1].getContents() == "" && cells[3][i + 1].getContents() == "") {
                    System.out.printf("\nTotal Amount Transferred:  %18s\n", cells[4][i].getContents().replaceAll("\"", ""));
                    System.out.println("Date of Transfer: " + date + "-18");
                    System.out.println("----------------------------------------------------------------");
                }

            } else if (cells[2][i].getContents() == "" && cells[3][i].getContents() != "" && cells[4][i].getContents() != "") { //
                System.out.printf("     %-20s%20s\n", cells[3][i].getContents(), cells[4][i].getContents().replaceAll("\"", ""));

                if (cells[6][i].getContents() != "") {
                    System.out.printf("\nTotal Amount Transferred:  %18s\n", cells[6][i].getContents().replaceAll("\"", ""));
                    System.out.println("Date of Transfer: " + date + "-18");
                    System.out.println("----------------------------------------------------------------");
                }
            }
        }

        return list;
    }
}

