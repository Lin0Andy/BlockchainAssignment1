import java.security.*;
import java.security.spec.ECGenParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Wallet {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private Map<String, TransactionOutput> UTXOs = new HashMap<>();

    public Wallet() {
        generateKeyPair();
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public double getBalance() {
        double total = 0;
        for (Map.Entry<String, TransactionOutput> entry : UTXOs.entrySet()) {
            TransactionOutput UTXO = entry.getValue();
            if (UTXO.isMine(publicKey)) {
                UTXOs.put(UTXO.getId(), UTXO);
                total += UTXO.getAmount();
            }
        }
        return total;
    }

    public Transaction sendFunds(PublicKey recipient, double amount) {
        if (getBalance() < amount) {
            System.out.println("# Not Enough funds to send transaction. Transaction Discarded.");
            return null;
        }

        ArrayList<TransactionInput> inputs = new ArrayList<>();
        double total = 0;

        for (Map.Entry<String, TransactionOutput> entry : UTXOs.entrySet()) {
            TransactionOutput UTXO = entry.getValue();
            total += UTXO.getAmount();
            inputs.add(new TransactionInput(UTXO.getId()));
            if (total > amount) break;
        }

        Transaction newTransaction = new Transaction(publicKey, recipient, amount);

        double change = total - amount;
        if (change > 0) {
            TransactionOutput changeUTXO = new TransactionOutput(publicKey, change, newTransaction.getTransactionId());
            newTransaction.getOutputs().add(changeUTXO);
            UTXOs.put(changeUTXO.getId(), changeUTXO);
        }

        TransactionOutput recipientUTXO = new TransactionOutput(recipient, amount, newTransaction.getTransactionId());
        newTransaction.getOutputs().add(recipientUTXO);
        UTXOs.put(recipientUTXO.getId(), recipientUTXO);

        newTransaction.generateSignature(privateKey);

        for (TransactionInput input : inputs) {
            UTXOs.remove(input.getTransactionOutputId());
        }

        return newTransaction;

    }


    private void generateKeyPair() {
        Security.addProvider(new BouncyCastleProvider());

        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");

            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public void addUTXO(TransactionOutput UTXO) {
        UTXOs.put(UTXO.getId(), UTXO);
    }
}
