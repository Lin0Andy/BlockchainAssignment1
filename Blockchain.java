import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private List<Block> chain;
    private int difficulty;

    public Blockchain(int difficulty) {
        this.chain = new ArrayList<>();
        this.difficulty = difficulty;
        createGenesisBlock();
    }

    private void createGenesisBlock() {
        List<Transaction> transactions = new ArrayList<>();
        Block genesisBlock = new Block(0, "0", transactions);
        genesisBlock.mineBlock(difficulty);
        chain.add(genesisBlock);
    }

    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public void addBlock(Block newBlock) {
        newBlock.mineBlock(difficulty);
        chain.add(newBlock);
    }

    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block currentBlock = chain.get(i);
            Block previousBlock = chain.get(i - 1);

            if (!currentBlock.calculateHash().equals(currentBlock.calculateHash())) {
                System.out.println("Block hash mismatch at index " + i);
                return false;
            }

            if (!currentBlock.getPreviousHash().equals(previousBlock.calculateHash())) {
                System.out.println("Previous hash mismatch at index " + i);
                return false;
            }
        }

        return true;
    }


    public void printBlockchain() {
        for (Block block : chain) {
            System.out.println("Block #" + block.getIndex() +
                    "\nHash: " + block.calculateHash() +
                    "\nPrevious Hash: " + block.getPreviousHash() +
                    "\nTimestamp: " + block.getTimestamp() +
                    "\nMerkle Root: " + block.getMerkleRoot() +
                    "\nNonce: " + block.getNonce() +
                    "\nTransactions: " + block.getTransactions() +
                    "\n----------------------");
        }
    }
}
