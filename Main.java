import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Blockchain blockchain = new Blockchain(4);

        Map<Integer, Wallet> userWallets = new HashMap<>();
        userWallets.put(1, new Wallet());
        userWallets.get(1).addUTXO(new TransactionOutput(userWallets.get(1).getPublicKey(), 10, "0")); // Initial balance of 10

        userWallets.put(2, new Wallet());
        userWallets.get(2).addUTXO(new TransactionOutput(userWallets.get(2).getPublicKey(), 100, "0")); // Initial balance of 100

        userWallets.put(3, new Wallet());

        while (true) {
            System.out.println("\n-------- Menu --------");
            System.out.println("1. View Blockchain");
            System.out.println("2. View My Wallet");
            System.out.println("3. Make Money Transfer");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewBlockchain(blockchain);
                    break;
                case 2:
                    System.out.print("Enter your account number: ");
                    int userAccountNumber = scanner.nextInt();
                    viewWallet("User " + userAccountNumber, userWallets.get(userAccountNumber));
                    break;
                case 3:
                    makeMoneyTransfer(userWallets, blockchain);
                    break;
                case 4:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void viewBlockchain(Blockchain blockchain) {
        System.out.println("\n-------- Blockchain --------");
        blockchain.printBlockchain();
    }

    private static void viewWallet(String userName, Wallet wallet) {
        System.out.println("\n-------- " + userName + " Wallet Information --------");
        System.out.println("Public Key: " + CryptoUtil.getStringFromKey(wallet.getPublicKey()));
        System.out.println("Balance: " + wallet.getBalance());
    }

    private static void makeMoneyTransfer(Map<Integer, Wallet> userWallets, Blockchain blockchain) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter sender account number: ");
        int senderAccountNumber = scanner.nextInt();
        Wallet senderWallet = userWallets.get(senderAccountNumber);

        System.out.print("Enter recipient account number: ");
        int recipientAccountNumber = scanner.nextInt();
        Wallet recipientWallet = userWallets.get(recipientAccountNumber);

        System.out.print("Enter amount to transfer: ");
        double amount = scanner.nextDouble();

        Transaction newTransaction = senderWallet.sendFunds(recipientWallet.getPublicKey(), amount);

        if (newTransaction != null) {
            List<Transaction> transactions = Collections.singletonList(newTransaction);
            Block newBlock = new Block(blockchain.getLatestBlock().getIndex() + 1, blockchain.getLatestBlock().calculateHash(), transactions);
            blockchain.addBlock(newBlock);

            recipientWallet.addUTXO(new TransactionOutput(recipientWallet.getPublicKey(), amount, newTransaction.getTransactionId()));

            System.out.println("Money transfer successful!");
        } else {
            System.out.println("Money transfer failed. Check your balance.");
        }
    }

}
