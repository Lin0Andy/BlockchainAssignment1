import java.security.*;

public class DigitalSignature {

    public static byte[] applyDigitalSignature(PrivateKey privateKey, String data) {
        try {
            Signature signature = Signature.getInstance("SHA256withECDSA");
            signature.initSign(privateKey);
            signature.update(data.getBytes());
            return signature.sign();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifyDigitalSignature(PublicKey publicKey, String data, byte[] signature) {
        try {
            Signature verifier = Signature.getInstance("SHA256withECDSA");
            verifier.initVerify(publicKey);
            verifier.update(data.getBytes());
            return verifier.verify(signature);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
