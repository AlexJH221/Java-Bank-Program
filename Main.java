import java.util.Scanner;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.IOException;



public class Main {

	static final int DELETE_ACCOUNT = 1;
	static final int ACCESS_ACCOUNT = 2;

	static ArrayList<Bank> accounts;
	static Scanner keyboard = new Scanner(System.in);

	public static void main(String[] args) {
		runProgram();
	}

	public static void runProgram() {
		try {
			File file = new File("Bank.txt");
			readProgram(file);
			promptUser();
			showFinalContent();
			addContentToFile(file);

		} catch (FileNotFoundException e) {
			System.out.println("This file does not exist!");
		} catch (IOException e) {
			System.out.println("The name of the file is incorrect!");
		}
	}

	public static void readProgram(File file) throws IOException {
		if (file.createNewFile()) {
			System.out.println("New ATM Bank System has been created!");
			System.out.println();
		}

		Scanner fileReader = new Scanner(file);
		getContents(fileReader);
		fileReader.close();

		System.out.println("Current contents of the file:");
		System.out.println();

		for (Bank account : accounts) {
			System.out.println(account);
		}
	}

	public static void getContents(Scanner fileReader) {
		accounts = new ArrayList<Bank>();

		while (fileReader.hasNext()) {
			String name = fileReader.next();
			double moneyAmount = fileReader.nextDouble();
			accounts.add(new Bank(name, moneyAmount));
		}
	}

	public static void promptUser() {
		System.out.println("1: Add Account");
		System.out.println("2: Delete Account");
		System.out.println("3: Access Account");
		System.out.println("4: Exit Bank");
		System.out.print("What would you like to do: ");

		int decision = getChoice(1, 4);
		performAction(decision);
	}

	public static int getChoice(int minInt, int maxInt) {
		while (!keyboard.hasNextInt()) {
			String decision = keyboard.nextLine();
			System.out.println(decision + " is not an integer!");
			System.out.print("What would you like to do: ");
		}

		int decision = keyboard.nextInt();
		keyboard.nextLine();

		if (decision < minInt || decision > maxInt) {
			System.out.println(decision + " is not a valid integer!");
			System.out.print("What would you like to do: ");
			return getChoice(minInt, maxInt);
		}

		System.out.println();

		return decision;
	}

	public static void performAction(int decision) {
		String message = "";

		switch (decision) {
			case 1:
				addAccount();
				promptUser();
				break;
			case 2:
				message = "Enter the first name of the account you would like to delete: ";
				makeChange(message, DELETE_ACCOUNT);
				promptUser();
				break;
			case 3:
				message = "Enter first name of account you would like to access: ";
				makeChange(message, ACCESS_ACCOUNT);
				promptUser();
				break;
			case 4:
				break;
		}
	}

	public static void addAccount() {
		System.out.print("Enter the first name of the new account: ");
		String name = keyboard.next();
		double amount = getAmount("Enter the amount of money to be added into the new account: ");

		accounts.add(new Bank(name, amount));
		System.out.println("New account has been successfully created!");
		System.out.println();
	}

	public static double getAmount(String message) {
		System.out.print(message);

		while (!keyboard.hasNextDouble()) {
			String input = keyboard.nextLine();
			System.out.println(input + " is not an double!");
			System.out.print(message);
		}

		double amount = keyboard.nextDouble();

		return amount;
	}

	public static void makeChange(String message, int actionCode) {
		System.out.print(message);
		String name = keyboard.next();

		Iterator<Bank> accountsItr = accounts.iterator();
		boolean accountFound = false;

		while (accountsItr.hasNext()) {
			Bank account = accountsItr.next();

			if (account.getName().equals(name)) {
				if (actionCode == DELETE_ACCOUNT) {
					accountsItr.remove();
				} else if (actionCode == ACCESS_ACCOUNT) {
					changeMoney(account);
				}
				accountFound = true;
			}
		}

		if (!accountFound) {
			System.out.println("Account not found!");
		}
		System.out.println();
	}

	public static void changeMoney(Bank account) {
		System.out.println("1: Add money");
		System.out.println("2: Take money");
		System.out.print("What would you like to do: ");
		int decision = getChoice(1, 2);
		double amount = getAmount("How much money would you like to change by: ");

		if (decision == 1) {
			account.addMoney(amount);
		} else {
			if (amount > account.getMoney()) {
				System.out.println("Insufficient amount in account to be taken!");
			} else {
				account.removeMoney(amount);
			}
		}
		System.out.println();
	}

	public static void showFinalContent() {
		System.out.println("Final contents of the file:");

		for (Bank account : accounts) {
			System.out.println(account);
		}
	}

	public static void addContentToFile(File file) throws FileNotFoundException {
		PrintStream out = new PrintStream(file);

		for (Bank account : accounts) {
			out.println(account.getName());
			out.println(account.getMoney());
			out.println();
		}

		out.close();
	}
}