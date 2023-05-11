package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

// represents a list of transactions
public class ListOfTransactions implements Writable {
    private List<Transaction> allTransactionsList;
    private List<Transaction> incomeList;
    private List<Transaction> expenseList;

    // EFFECTS: constructs three empty lists that will hold transactions
    public ListOfTransactions() {
        allTransactionsList = new ArrayList<>();
        incomeList = new ArrayList<>();
        expenseList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a transaction to the list of Transactions, adds to incomeList or expenseList based on type
    public void addToList(Transaction newItem) {
        this.allTransactionsList.add(newItem);
        if (newItem.getType()) {
            this.incomeList.add(newItem);
        } else {
            this.expenseList.add(newItem);
        }
    }

    // EFFECTS: calculates the balance of the user by adding income items and
    //          subtracting expense items.
    public int calculateBalance() {
        int size = this.allTransactionsList.size();
        int balance = 0;
        for (int i = 0; i < size; i++) {
            if (this.allTransactionsList.get(i).getType()) {
                balance = balance + this.allTransactionsList.get(i).getAmount();
            } else {
                balance = balance - this.allTransactionsList.get(i).getAmount();
            }
        }
        return balance;
    }

    // EFFECTS: returns true if list is empty, otherwise false
    public boolean isListEmpty() {
        return this.allTransactionsList.isEmpty();
    }

    // EFFECTS: returns the transaction at ith place in allTransactionsList
    public Transaction getTransaction(int i) {
        return this.allTransactionsList.get(i);
    }

    // EFFECTS: returns the transaction at ith place in incomeList
    public Transaction getIncomeTransaction(int i) {
        return this.incomeList.get(i);
    }

    // EFFECTS: returns the transaction at ith place in expenseList
    public Transaction getExpenseTransaction(int i) {
        return this.expenseList.get(i);
    }

    // EFFECTS: returns the number of transactions in listOfTransactions
    public int numberOfTransactions() {
        return this.allTransactionsList.size();
    }

    // EFFECTS: returns the number of income transactions in listOfTransactions
    public int numberOfIncomeTransactions() {
        return this.incomeList.size();
    }

    // EFFECTS: returns the number of expense transactions in listOfTransactions
    public int numberOfExpenseTransactions() {
        return this.expenseList.size();
    }

    // EFFECTS: returns the most frequent expense category specified by the user in the ListOfTransactions
    public String mostFrequentExpenseCategory() {
        int maxCounter = 0;
        int currentCounter;
        String currentCategoryOuterLoop;
        String currentCategoryInnerLoop;
        String maxCategory = "none";
        for (int i = 0; i < numberOfExpenseTransactions(); i++) {
            currentCounter = 0;
            currentCategoryOuterLoop = getExpenseTransaction(i).getCategory();
            for (int j = 0; j < numberOfExpenseTransactions(); j++) {
                currentCategoryInnerLoop = getExpenseTransaction(j).getCategory();
                if (currentCategoryOuterLoop.equals(currentCategoryInnerLoop)) {
                    currentCounter++;
                }
            }
            if (currentCounter > maxCounter) {
                maxCounter = currentCounter;
                maxCategory = currentCategoryOuterLoop;
            }
        }
        return maxCategory;
    }

    // EFFECTS: returns the most frequent income category specified by the user in the ListOfTransactions
    public String mostFrequentIncomeCategory() {
        int maxCounter = 0;
        int currentCounter;
        String currentCategoryOuterLoop;
        String currentCategoryInnerLoop;
        String maxCategory = "none";
        for (int i = 0; i < numberOfIncomeTransactions(); i++) {
            currentCounter = 0;
            currentCategoryOuterLoop = getIncomeTransaction(i).getCategory();
            for (int j = 0; j < numberOfIncomeTransactions(); j++) {
                currentCategoryInnerLoop = getIncomeTransaction(j).getCategory();
                if (currentCategoryOuterLoop.equals(currentCategoryInnerLoop)) {
                    currentCounter++;
                }
            }
            if (currentCounter > maxCounter) {
                maxCounter = currentCounter;
                maxCategory = currentCategoryOuterLoop;
            }
        }
        return maxCategory;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("transactions", transactionsToJson());
        return json;
    }

    // EFFECTS: returns transactions in this ListOfTransactions as a JSON array
    private JSONArray transactionsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Transaction t : this.allTransactionsList) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }
}
