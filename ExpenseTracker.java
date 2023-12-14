import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Expense {
    private String category;
    private double amount;
    private String date;

    public Expense(String category, double amount, String date) {
        this.category = category;
        this.amount = amount;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
}

class ExpenseTracker {
    private Map<String, List<Expense>> expenses;
    private Map<String, Double> budgets;

    public ExpenseTracker() {
        this.expenses = new HashMap<>();
        this.budgets = new HashMap<>();
    }

    public void logExpense(String category, double amount, String date) {
        Expense expense = new Expense(category, amount, date);
        expenses.computeIfAbsent(category, k -> new ArrayList<>()).add(expense);
    }

    public void setBudget(String category, double budget) {
        budgets.put(category, budget);
    }

    public void showExpenseHistory() {
        System.out.println("Expense History:");
        for (Map.Entry<String, List<Expense>> entry : expenses.entrySet()) {
            String category = entry.getKey();
            List<Expense> categoryExpenses = entry.getValue();

            System.out.println("Category: " + category);
            for (Expense expense : categoryExpenses) {
                System.out.println("  Amount: $" + expense.getAmount() +
                        ", Date: " + expense.getDate());
            }
        }
    }

    public void showBudgets() {
        System.out.println("Budgets:");
        for (Map.Entry<String, Double> entry : budgets.entrySet()) {
            String category = entry.getKey();
            double budget = entry.getValue();
            System.out.println("Category: " + category + ", Budget: $" + budget);
        }
    }

    public void visualizeExpenses() {
    
        System.out.println("Visualization of Expenses (Graphs/Charts)");
    }

    public void checkBudgetLimits() {
        System.out.println("Checking Budget Limits...");
        for (Map.Entry<String, List<Expense>> entry : expenses.entrySet()) {
            String category = entry.getKey();
            double totalSpent = entry.getValue().stream().mapToDouble(Expense::getAmount).sum();
            double budget = budgets.getOrDefault(category, 0.0);

            if (totalSpent > budget) {
                System.out.println("Overspending alert for category " + category +
                        "! Total spent: $" + totalSpent + ", Budget: $" + budget);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        ExpenseTracker expenseTracker = new ExpenseTracker();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nExpense Tracker Menu:");
            System.out.println("1. Log Expense");
            System.out.println("2. Set Budget");
            System.out.println("3. Show Expense History");
            System.out.println("4. Show Budgets");
            System.out.println("5. Visualize Expenses");
            System.out.println("6. Check Budget Limits");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter expense category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter expense amount: $");
                    double amount = scanner.nextDouble();
                    scanner.nextLine(); 
                    System.out.print("Enter expense date (MM/DD/YYYY): ");
                    String date = scanner.nextLine();
                    expenseTracker.logExpense(category, amount, date);
                    break;
                case 2:
                    System.out.print("Enter budget category: ");
                    String budgetCategory = scanner.nextLine();
                    System.out.print("Enter budget amount: $");
                    double budgetAmount = scanner.nextDouble();
                    scanner.nextLine();
                    expenseTracker.setBudget(budgetCategory, budgetAmount);
                    break;
                case 3:
                    expenseTracker.showExpenseHistory();
                    break;
                case 4:
                    expenseTracker.showBudgets();
                    break;
                case 5:
                    expenseTracker.visualizeExpenses();
                    break;
                case 6:
                    expenseTracker.checkBudgetLimits();
                    break;
                case 7:
                    System.out.println("Exiting Expense Tracker. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 7.");
            }
        }
    }
}
