
public class Bank {
	private String name;
	private double money;

	public Bank(String name, double money) {
		this.name = name;
		this.money = money;
	}

	public String getName() {
		return name;
	}

	public double getMoney() {
		return money;
	}

	public void addMoney(double amount) {
		money += amount;
	}

	public void removeMoney(double amount) {
		money -= amount;
	}

	public String toString() {
		return name + " has $" + money;
	}
}