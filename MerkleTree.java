import java.util.ArrayList;
import java.util.List;

public class MerkleTree {

    private List<String> tree;

    public MerkleTree(List<String> transactions) {
        tree = new ArrayList<>(transactions);
        buildMerkleTree();
    }

    private void buildMerkleTree() {
        int levelOffset = 0;
        for (int levelSize = tree.size(); levelSize > 1; levelSize = (levelSize + 1) / 2) {
            for (int left = 0; left < levelSize; left += 2) {
                int right = Math.min(left + 1, levelSize - 1);
                tree.add(CryptoUtil.applySHA256(tree.get(levelOffset + left) + tree.get(levelOffset + right)));
            }
            levelOffset += levelSize;
        }
    }

    public String getMerkleRoot() {
        if (tree.isEmpty()) {
            return null;
        }
        return tree.get(tree.size() - 1);
    }

    public List<String> getMerkleTree() {
        return new ArrayList<>(tree);
    }
}
