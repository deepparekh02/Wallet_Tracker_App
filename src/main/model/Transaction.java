package model;

import exceptions.InvalidTypeException;
import org.json.JSONObject;
import persistence.Writable;

// represents a transaction of a certain amount in a certain category that is either an expense or income
public class Transaction implements Writable {
    private boolean type;          // true represents income, false represents expense
    private int amount;         // the amount in the transaction
    private String category;       // the category of a transaction


    // EFFECTS:  creates a new transaction with amount as transactionAmount, category as categoryName,
    //           comment as additionalComment and date as todayDate. Further throws the InvalidTypeException if needed.
    public Transaction(int transactionAmount, String type, String categoryName) throws InvalidTypeException {
        this.amount = transactionAmount;
        this.category = categoryName;
        this.type = convertStringToType(type);
    }

    public int getAmount() {
        return amount;
    }

    public boolean getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    //
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("amount", amount);
        json.put("type", convertTypeToString());
        json.put("category", category);
        return json;
    }

    // EFFECTS: returns string value income or expense dependent on value of type
    public String convertTypeToString() {
        if (this.type) {
            return "income";
        } else {
            return "expense";
        }
    }

    // EFFECTS: returns False if "expense" or "Expense"; returns True if "income" or "Income",
    //          throws InvalidTypeException if some other string was entered.
    public Boolean convertStringToType(String type) throws InvalidTypeException {
        if (type.equals("expense") || type.equals("Expense")) {
            return false;
        } else if (type.equals("income") || type.equals("Income")) {
            return true;
        } else {
            throw new InvalidTypeException();
        }
    }
}
