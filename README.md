# My Wallet Tracker #

### A handy application that remembers what you earn and spend. ###

*Description* of the application:
- **What will the application do?** The application will keep track of a person's income and expenditure and show them 
a summary of all the transactions in the last certain time periods while showing them their *positive or negative 
balance.*
- **Who will use it?** The application will be used by people who want to have a better control on their finances. It
can be used by university students or adults who are having trouble with their finances.
- **Why is this project of interest to you?** I feel this application can help me take control of my own finances as 
well as I have struggled in doing so in the past. As a new university student who now has to manage his own finances, 
an application that keeps track of my transactions can be of great help.

## User Stories ##
- As a user, I want to be able to create and record a new transaction and add it to a list of transactions.
- As a user, I want to be able to see the summary of all of my entered transactions.
- As a user, I want to be able to record my transaction as Income or Expense and see a summary of just my income
transactions or just my expense transactions.
- As a user, I want to be able to see the positive or negative balance on my account.
- As a user, I want to be able to further categorize each income or expense item and see my most frequent categories 
for both income and expense. 
- As a user, I want to be able to save the transactions I entered to file.
- As a user, I want to be able to be able to load my previous transactions from the file. 
- As a user, when I select the quit option from the application menu, I want to be reminded to save my history
 to file and have the option to do so or not.
 
 ## Phase 4: Task 2 ##
- **Option chosen:** Testing and Designing a class in the model package which is robust. 
- **Classes and Methods Involved:** 
  - The method convertStringToType() in the Transactions class creates and throws a new
InvalidTypeException which is further thrown by the constructor in the same class. 
  - The method actionPerformed(ActionEvent e) in the inner class SubmitTransactionButton, which implements an 
  ActionListener, in the outer class WalletAppGUI catches the exception after calling the Transaction constructor 
  and shows a message in a pop-up dialog box to the user to notify that they submitted an invalid type to the program.
  - The addTransaction method in the JsonReader class also throws the InvalidTypeException when the Json file being read
  has an invalid type stored on it. This is further thrown up all the way in the methods addTransactions,
  parseListOfTransactions and read. It is caught in the actionPerformed method in the inner class LoadButton which
  implements ActionListener in the class WalletAppGUI. It shows an error message to the user informing them of the 
  invalid type stored on file in the form of a pop-up dialog box when caught.
  
## Phase 4: Task 3
 - I would have made Transaction an abstract class and have it extended by two classes IncomeTransaction and 
 ExpenseTransaction that have the same methods but different implementations on top of some special class-specific
 methods.
 - I would have had formed a bi-directional relationship between Transaction and ListOfTransactions so that each 
 transaction would have knowledge of which ListOfTransactions object it belongs to, and I would have programmed the 
 ability to shift a Transaction object from one ListOfTransactions object to another.
 - I would have made the type field of each Transaction object be an enumeration with only two options expense or 
 income instead of setting them up through the use of strings. I would have made the user select from the two options, 
 through a drop down list box or two option buttons, in the GUI to record the type of the Transaction.