package ui;

import exceptions.InvalidTypeException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import static java.lang.Integer.parseInt;

// represents the WalletApp application in GUI form.
public class WalletAppGUI {

    private static int WIDTH = 1000;
    private static int HEIGHT = 550;
    private static final String JSON_STORE = "./data/testReaderInvalidTypeException.json";

    private ListOfTransactions listOfTransactions;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JFrame mainFrame;

    private JPanel inputPanel;
    private JPanel functionsPanel;
    private JPanel listDisplayPanel;
    private JScrollPane tablePanel;

    // inputPanel
    private JLabel enterDetails;
    private JLabel inputImageLabel;
    private JTextField inputAmount;
    private JTextField inputType;
    private JTextField inputCategory;
    private ImageIcon inputImageIcon;
    private JButton submitTransaction;
    private JButton save;
    private JButton load;

    // functionsPanel
    private JLabel functionsPanelMainText;
    private JTextField balanceField;
    private JTextField mostFrequentIncomeCategory;
    private JTextField mostFrequentExpenseCategory;
    private JButton calculateBalance;
    private JButton showMostFrequentIncomeCategory;
    private JButton showMostFrequentExpenseCategory;

    // listDisplayPanel
    private JLabel displayPanelMainText;
    private JTable listTable;
    private String[] columnNames;
    private String[][] data;
    private JButton displayAllTransactions;
    private JButton displayIncomeTransactions;
    private JButton displayExpenseTransactions;

    // runs the WalletApp application
    WalletAppGUI() {

        listOfTransactions = new ListOfTransactions();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        createInputPanel();
        createFunctionsPanel();
        createListDisplayPanel();
        createMainFrame();
    }


    // MODIFIES: this
    // EFFECTS: creates the list display panel in GUI
    private void createListDisplayPanel() {
        listDisplayPanel = new JPanel();
        listDisplayPanel.setBounds(0, 0, 400, HEIGHT);
        listTable = new JTable();
        int inputWidth = 380;
        int inputHeight = 25;
        createAllTransactionsTable();

        displayPanelMainText = new JLabel("Press the buttons below to see");
        displayPanelMainText.setBounds(20, 20, inputWidth, inputHeight);
        createListDisplayPanelButtons(inputWidth, inputHeight);

        listDisplayPanel.add(displayPanelMainText);
        listDisplayPanel.add(displayAllTransactions);
        listDisplayPanel.add(displayIncomeTransactions);
        listDisplayPanel.add(displayExpenseTransactions);
        listDisplayPanel.add(tablePanel);
    }

    // MODIFIES: this
    // EFFECTS: creates the buttons in listDisplayPanel
    private void createListDisplayPanelButtons(int inputWidth, int inputHeight) {
        displayAllTransactions = new JButton("All Transactions:");
        displayAllTransactions.setBounds(20, 50, inputWidth, inputHeight);
        displayAllTransactions.addActionListener(new AllTransactionsButton());

        displayIncomeTransactions = new JButton("Income Transactions:");
        displayIncomeTransactions.setBounds(20, 80, inputWidth, inputHeight);
        displayIncomeTransactions.addActionListener(new IncomeTransactionsButton());

        displayExpenseTransactions = new JButton("Expense Transactions:");
        displayExpenseTransactions.setBounds(20, 110, inputWidth, inputHeight);
        displayExpenseTransactions.addActionListener(new ExpenseTransactionsButton());
    }

    // ActionListener implementation for ExpenseTransactionsButton
    private class ExpenseTransactionsButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            createExpenseTransactionsTable();
            listDisplayPanel.add(tablePanel);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the ExpenseTransactionsTable
    private void createExpenseTransactionsTable() {
        columnNames = new String[]{"Amount", "Type", "Category"};
        collectExpenseTransactionsData();
        listTable = new JTable(data, columnNames);
        tablePanel = new JScrollPane(listTable);
        tablePanel.setBounds(20, 140, 380, HEIGHT);
        listTable.setBounds(0, 0, 380, HEIGHT);
    }

    // MODIFIES: this
    // EFFECTS: collects and forms the data required for the ExpenseTransactionsTable
    private void collectExpenseTransactionsData() {
        int size = listOfTransactions.numberOfExpenseTransactions();
        Transaction current;
        String type;
        String category;
        int amount;
        String amountText;
        String[][] currentArray = new String[size][3];
        for (int i = 0; i < size; i++) {
            current = listOfTransactions.getExpenseTransaction(i);
            type = "Expense";
            category = current.getCategory();
            amount = current.getAmount();
            amountText = Integer.toString(amount);
            currentArray[i][0] = amountText;
            currentArray[i][1] = type;
            currentArray[i][2] = category;
        }
        data = currentArray;
    }

    // ActionListener implementation for IncomeTransactionsButton
    private class IncomeTransactionsButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            createIncomeTransactionsTable();
            listDisplayPanel.add(tablePanel);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the IncomeTransactionsTable
    private void createIncomeTransactionsTable() {
        columnNames = new String[]{"Amount", "Type", "Category"};
        collectIncomeTransactionsData();
        listTable = new JTable(data, columnNames);
        tablePanel = new JScrollPane(listTable);
        tablePanel.setBounds(20, 140, 380, HEIGHT);
        listTable.setBounds(0, 0, 380, HEIGHT);
    }

    // MODIFIES: this
    // EFFECTS: collects and forms the data required for the IncomeTransactionsTable
    private void collectIncomeTransactionsData() {
        int size = listOfTransactions.numberOfIncomeTransactions();
        Transaction current;
        String type;
        String category;
        int amount;
        String amountText;
        String[][] currentArray = new String[size][3];
        for (int i = 0; i < size; i++) {
            current = listOfTransactions.getIncomeTransaction(i);
            type = "Income";
            category = current.getCategory();
            amount = current.getAmount();
            amountText = Integer.toString(amount);
            currentArray[i][0] = amountText;
            currentArray[i][1] = type;
            currentArray[i][2] = category;
        }
        data = currentArray;
    }

    // ActionListener implementation for the AllTransactionsButton
    private class AllTransactionsButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            createAllTransactionsTable();
            listDisplayPanel.add(tablePanel);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the AllTransactionsTable
    private void createAllTransactionsTable() {
        columnNames = new String[]{"Amount", "Type", "Category"};
        collectAllTransactionsData();
        listTable = new JTable(data, columnNames);
        tablePanel = new JScrollPane(listTable);
        tablePanel.setBounds(20, 140, 380, HEIGHT);
        listTable.setBounds(0, 0, 380, HEIGHT);
    }

    // MODIFIES: this
    // EFFECTS: collects and forms the data required for the AllTransactionsTable
    private void collectAllTransactionsData() {
        int size = listOfTransactions.numberOfTransactions();
        Transaction current;
        String type;
        String category;
        int amount;
        String amountText;
        String[][] currentArray = new String[size][3];
        for (int i = 0; i < size; i++) {
            current = listOfTransactions.getTransaction(i);
            if (current.getType()) {
                type = "Income";
            } else {
                type = "Expense";
            }
            category = current.getCategory();
            amount = current.getAmount();
            amountText = Integer.toString(amount);
            currentArray[i][0] = amountText;
            currentArray[i][1] = type;
            currentArray[i][2] = category;
        }
        data = currentArray;
    }


    // MODIFIES: this
    // EFFECTS: creates the FunctionsPanel in the app
    private void createFunctionsPanel() {
        functionsPanel = new JPanel(null);
        functionsPanel.setBounds(700, 0, 300, HEIGHT);
        int inputWidth = 260;
        int inputHeight = 25;

        functionsPanelMainText = new JLabel("Press the buttons below to find the");
        functionsPanelMainText.setBounds(20, 40, inputWidth, inputHeight);

        createFunctionsPanelBalanceSection(inputWidth, inputHeight);
        createFunctionsPanelIncomeSection(inputWidth, inputHeight);
        createFunctionsPanelExpensesSection(inputWidth, inputHeight);

        functionsPanel.add(functionsPanelMainText);
        functionsPanel.add(calculateBalance);
        functionsPanel.add(balanceField);
        functionsPanel.add(showMostFrequentIncomeCategory);
        functionsPanel.add(mostFrequentIncomeCategory);
        functionsPanel.add(showMostFrequentExpenseCategory);
        functionsPanel.add(mostFrequentExpenseCategory);
    }

    // MODIFIES: this
    // EFFECTS: creates the most frequent expenses category section in the FunctionsPanel
    private void createFunctionsPanelExpensesSection(int inputWidth, int inputHeight) {
        showMostFrequentExpenseCategory = new JButton("Most Frequent Expense Category:");
        showMostFrequentExpenseCategory.setBounds(20, 190, inputWidth, inputHeight);
        showMostFrequentExpenseCategory.addActionListener(new ShowMostFrequentExpenseCategoryButton());
        mostFrequentExpenseCategory = new JTextField();
        mostFrequentExpenseCategory.setBounds(20, 220, inputWidth, inputHeight);
    }

    // MODIFIES: this
    // EFFECTS: creates the most frequent income category section in the FunctionsPanel
    private void createFunctionsPanelIncomeSection(int inputWidth, int inputHeight) {
        showMostFrequentIncomeCategory = new JButton("Most Frequent Income Category:");
        showMostFrequentIncomeCategory.setBounds(20, 130, inputWidth, inputHeight);
        showMostFrequentIncomeCategory.addActionListener(new ShowMostFrequentIncomeCategoryButton());
        mostFrequentIncomeCategory = new JTextField();
        mostFrequentIncomeCategory.setBounds(20, 160, inputWidth, inputHeight);
    }

    // MODIFIES: this
    // EFFECTS: creates the balance section in the FunctionsPanel
    private void createFunctionsPanelBalanceSection(int inputWidth, int inputHeight) {
        calculateBalance = new JButton("Current Balance:");
        calculateBalance.setBounds(20, 70, inputWidth, inputHeight);
        calculateBalance.addActionListener(new CalculateBalanceButton());
        balanceField = new JTextField();
        balanceField.setBounds(20, 100, inputWidth, inputHeight);
    }

    // ActionListener implementation for CalculateBalanceButton
    private class CalculateBalanceButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int balance = listOfTransactions.calculateBalance();
            balanceField.setText("Your current Balance is " + balance);
        }
    }

    // ActionListener implementation for ShowMostFrequentIncomeCategoryButton
    private class ShowMostFrequentIncomeCategoryButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String result = listOfTransactions.mostFrequentIncomeCategory();
            mostFrequentIncomeCategory.setText(result);
        }
    }

    // ActionListener implementation for ShowMostFrequentExpenseCategoryButton
    private class ShowMostFrequentExpenseCategoryButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String result = listOfTransactions.mostFrequentExpenseCategory();
            mostFrequentExpenseCategory.setText(result);
        }
    }

    // MODIFIES: this
    // EFFECTS: creates the Main Frame of the app
    private void createMainFrame() {
        mainFrame = new JFrame();
        mainFrame.setLayout(null);
        mainFrame.setTitle("WalletApp");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setSize(WIDTH, HEIGHT);
        mainFrame.setVisible(true);
        mainFrame.getContentPane().setBackground(new Color(00, 00, 100));
        mainFrame.add(inputPanel);
        mainFrame.add(functionsPanel);
        mainFrame.add(listDisplayPanel);
    }

    // MODIFIES: this
    // EFFECTS: creates the InputPanel in the app
    private void createInputPanel() {
        inputPanel = new JPanel(null);
        inputPanel.setBounds(400, 0, 300, HEIGHT);
        int inputWidth = 260;
        int inputHeight = 25;

        createInputPanelImage(inputWidth);
        createInputPanelText(inputWidth, inputHeight);
        createInputPanelButtons(inputWidth, inputHeight);

        inputPanel.add(enterDetails);
        inputPanel.add(inputAmount);
        inputPanel.add(inputType);
        inputPanel.add(inputCategory);
        inputPanel.add(submitTransaction);
        inputPanel.add(inputImageLabel);
        inputPanel.add(save);
        inputPanel.add(load);
    }

    // MODIFIES: this
    // EFFECTS: creates the buttons in InputPanel
    private void createInputPanelButtons(int inputWidth, int inputHeight) {
        submitTransaction = new JButton("Submit Transaction");
        submitTransaction.setBounds(20, 370, inputWidth, inputHeight);
        submitTransaction.addActionListener(new SubmitTransactionButton());

        save = new JButton("Save");
        save.setBounds(20, 420, inputWidth, inputHeight);
        save.addActionListener(new SaveButton());

        load = new JButton("Load");
        load.setBounds(20, 450, inputWidth, inputHeight);
        load.addActionListener(new LoadButton());
    }

    // MODIFIES: this
    // EFFECTS: creates the image in InputPanel
    private void createInputPanelImage(int inputWidth) {
        inputImageIcon = new ImageIcon("data/MoneyLogo.jpeg");
        inputImageLabel = new JLabel();
        inputImageLabel.setIcon(inputImageIcon);
        inputImageLabel.setBounds(20, 40, inputWidth, 200);
    }

    // MODIFIES: this
    // EFFECTS: creates the label and text fields in InputPanel
    private void createInputPanelText(int inputWidth, int inputHeight) {
        enterDetails = new JLabel("Enter your transaction below");
        enterDetails.setBounds(20, 250, inputWidth, inputHeight);

        inputAmount = new JTextField();
        inputAmount.setText("Enter Amount");
        inputAmount.setBounds(20, 280, inputWidth, inputHeight);

        inputType = new JTextField();
        inputType.setText("Enter Income Or Expense");
        inputType.setBounds(20, 310, inputWidth, inputHeight);

        inputCategory = new JTextField();
        inputCategory.setText("Enter Category");
        inputCategory.setBounds(20, 340, inputWidth, inputHeight);
    }

    // ActionListener implementation for SubmitTransactionButton
    private class SubmitTransactionButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String amountText = inputAmount.getText();
            int amountNum = parseInt(amountText);
            String type = inputType.getText();
            String category = inputCategory.getText();
            inputAmount.setText("Enter Amount");
            inputType.setText("Enter Income Or Expense");
            inputCategory.setText("Enter Category");
            try {
                listOfTransactions.addToList(new Transaction(amountNum, type, category));
            } catch (InvalidTypeException ite) {
                JOptionPane.showMessageDialog(inputPanel, "An invalid type was entered. Please enter "
                        + "'expense' or 'income'.");
            }
        }
    }

    // ActionListener implementation for SaveButton
    private class SaveButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(listOfTransactions);
                jsonWriter.close();
            } catch (FileNotFoundException fnf) {
                JOptionPane.showMessageDialog(inputPanel, "Unable to write to file: " + JSON_STORE);
            }
        }
    }

    // ActionListener implementation for LoadButton
    private class LoadButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                listOfTransactions = jsonReader.read();
            } catch (IOException fnf) {
                JOptionPane.showMessageDialog(inputPanel, "Unable to read from file: " + JSON_STORE);
            } catch (InvalidTypeException ite) {
                JOptionPane.showMessageDialog(inputPanel, "An invalid type was stored on file in one of the "
                        + "transactions.");
            }
        }
    }

}



