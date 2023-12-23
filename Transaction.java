import java.lang.constant.Constable;
import java.security.*;
import java.util.*;

public class Transaction {

    private String transactionId;
    private PublicKey sender;
    private PublicKey recipient;
    private double amount;
    private byte[] signature;

    private List<TransactionInput> inputs;
    private List<TransactionOutput> outputs;

    public Transaction(PublicKey sender, PublicKey recipient, double amount) {
        this.sender = sender;
        this.recipient = recipient;
        this.amount = amount;
        this.transactionId = calculateHash();
        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();

    }

    public String getTransactionId() {
        return transactionId;
    }

    public PublicKey getSender() {
        return sender;
    }

    public PublicKey getRecipient() {
        return recipient;
    }

    public double getAmount() {
        return amount;
    }

    public byte[] getSignature() {
        return signature;
    }

    public List<TransactionInput> getInputs() {
        return inputs;
    }

    public List<TransactionOutput> getOutputs() {
        return outputs;
    }

    private String calculateHash() {
        return CryptoUtil.applySHA256(
                CryptoUtil.getStringFromKey(sender) +
                        CryptoUtil.getStringFromKey(recipient) +
                        Double.toString(amount)
        );
    }

    public String getHash(){
        return calculateHash();
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = CryptoUtil.getStringFromKey(sender) + CryptoUtil.getStringFromKey(recipient) + Double.toString(amount);
        signature = CryptoUtil.applyECDSASig(privateKey, data);
    }

    public boolean verifySignature() {
        String data = CryptoUtil.getStringFromKey(sender) + CryptoUtil.getStringFromKey(recipient) + Double.toString(amount);
        return CryptoUtil.verifyECDSASig(sender, data, signature);
    }
}
