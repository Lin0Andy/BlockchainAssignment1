import java.util.List;
import java.util.ArrayList;

public class Block {

    private int index;
    private String previousHash;
    private long timestamp;
    private String merkleRoot;
    private int nonce;
    private MerkleTree merkleTree;
    private List<Transaction> transactions = new ArrayList<>();

    public Block(int index, String previousHash, List<Transaction> transactions) {
        this.index = index;
        this.previousHash = previousHash;
        this.timestamp = System.currentTimeMillis();
        this.transactions.addAll(transactions);
        this.merkleRoot = calculateMerkleRoot();
        this.nonce = 0;
        List<String> transactionHashes = new ArrayList<>();
        for (Transaction transaction : transactions) {
            transactionHashes.add(transaction.getHash());
        }
        merkleTree = new MerkleTree(transactionHashes);
        this.merkleRoot = merkleTree.getMerkleRoot();
    }

    public int getIndex() {
        return index;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getMerkleRoot() {
        return merkleRoot;
    }

    public int getNonce() {
        return nonce;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String calculateHash() {
        return CryptoUtil.applySHA256(
                index + previousHash + timestamp + merkleRoot + nonce + transactions.toString());
    }

    public void mineBlock(int difficulty) {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while (!calculateHash().substring(0, difficulty).equals(target)) {
            nonce++;
        }
        System.out.println("Block mined: " + calculateHash());
    }

    private String calculateMerkleRoot() {
        if (transactions.isEmpty()) {
            return "";
        }

        List<String> tree = new ArrayList<>();
        for (Transaction transaction : transactions) {
            tree.add(transaction.getHash());
        }

        int levelOffset = 0;
        for (int levelSize = transactions.size(); levelSize > 1; levelSize = (levelSize + 1) / 2) {
            for (int left = 0; left < levelSize; left += 2) {
                int right = Math.min(left + 1, levelSize - 1);
                tree.add(CryptoUtil.applySHA256(tree.get(levelOffset + left) + tree.get(levelOffset + right)));
            }
            levelOffset += levelSize;
        }

        return tree.get(tree.size() - 1);
    }

}
