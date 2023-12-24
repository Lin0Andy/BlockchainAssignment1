This thing demonstrates how blockchain works through the prism of simplifications. A wallet simulator, where the activities include “give money”, see the blockchain itself and look at your wallet.

<h1>Project Overview:</h3>
<p>This Java-based project aims to implement a simplified version of a blockchain—a decentralized and secure distributed ledger. The blockchain is a linked list of blocks, where each block contains a list of transactions. The implementation includes essential components such as transaction management, cryptographic hashing, digital signatures, and a basic proof-of-work mining algorithm.</p>


<h3>Blockchain Class:</h3>
- Represents the entire blockchain as a linked list of blocks.
- Provides methods for adding blocks, creating the genesis block, and verifying the integrity of the blockchain.

<h3>Block Class:</h3>
- Represents an individual block in the blockchain.
- Contains properties like index, previous hash, timestamp, Merkle root, nonce, and a list of transactions.
- Implements a proof-of-work mining algorithm to secure the blockchain.

<h3>Transaction Class:</h3>
- Represents a transaction between participants.
- Utilizes asymmetric encryption (ECDSA) for digital signatures to ensure the authenticity of transactions.
- Includes methods for generating and verifying digital signatures.

<h3>Wallet Class:</h3>
- Represents a user's wallet with a public-private key pair.
- Manages unspent transaction outputs (UTXOs) to calculate the user's balance.
- Allows users to send funds to others while ensuring transaction authenticity and balance verification.

<h3>MerkleTree Class:</h3>
- Implements a Merkle tree to efficiently verify the integrity of a large set of transactions in a block.

<h3>CryptoUtil and DigitalSignature Classes:</h3>
- Provide cryptographic utility functions such as hashing, key pair generation, and digital signature operations.

<br><br>

- Users are represented by wallets, each having a unique public-private key pair.
- Transactions are created by users and added to blocks in the blockchain.
- Blocks are mined using a proof-of-work algorithm, ensuring the security of the blockchain.
- The integrity of the blockchain is maintained through the use of cryptographic hashing and digital signatures.
- Users can view their wallet balances, view the blockchain, and make money transfers to others

