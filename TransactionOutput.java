import java.security.PublicKey;

public class TransactionOutput {

    private String id;
    private PublicKey recipient;
    private double amount;
    public TransactionOutput(PublicKey recipient, double amount, String parentTransactionId) {
        this.recipient = recipient;
        this.amount = amount;
        this.id = CryptoUtil.applySHA256(
                CryptoUtil.getStringFromKey(recipient) +
                        Double.toString(amount) +
                        parentTransactionId
        );
    }

    public String getId() {
        return id;
    }

    public PublicKey getRecipient() {
        return recipient;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isMine(PublicKey publicKey) {
        return publicKey.equals(recipient);
    }
}
