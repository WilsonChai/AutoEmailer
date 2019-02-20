# Automatic Email Sender
> This application parses Excel sheets to generate emails and sends them to clients.

## Built With

* [JavaFX](https://openjfx.io/) - Java GUI framework
* [JExcelApi](http://jexcelapi.sourceforge.net/) - Java Excel library

Automatic Email Sender was built for the CEO of BNC International Inc. to help send weekly emails to his clients. The purpose of these emails are to confirm Electronic Fund Transfers (EFT) from the company to its clients. They are generated from Microsoft Excel sheets, which contain the client's name, invoice number, and amount paid. Then it will find the client's email from its name from another sheet to send it.

## Usage example

Below is a snapshot of the GUI in use.

![Imgur](https://i.imgur.com/sqif6bW.png)

On the left, it displays a list of all the emails generated and a Send All button. On the right, The email address, subject, and content is shown. Their contents can be changed by the user by modifying the text fields and pressing the save button or deleting it altogether with the delete button.

Below are snippets of the two Excel sheets the email was generated from (their data was changed to protect their privacy):

The client list

![Client List](https://i.imgur.com/6Y8hv35.png) 

EFT Information

![EFT Information](https://i.imgur.com/dNNJX2d.png)


## Author

Wilson Chai – [@WilsonChaiTea](https://twitter.com/WilsonChaiTea) – WilsonChaiTea@gmail.com

## License

Distributed under the GNU license. See ``LICENSE`` for more information.
