package com.multimerchant_haze.rest.v1.app.utils.cryptoFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Created by zorzis on 1/18/2017.
 *
 * found here: http://netnix.org/2015/04/19/aes-encryption-with-hmac-integrity-in-java/
 */
public class SymmetricCryptoProvider
{

    private static final Logger logger = LoggerFactory.getLogger(SymmetricCryptoProvider.class);
    private String className =  this.getClass().getSimpleName();



    private static final String SecretKeyPasswordForAuthentication =
            "4Um8egX8QJ@Khq8!QmwfDp233*@@2Eb5TA$9jQ84%3nrmm43Mq73x7" +
                    "&E8663g%xmZGxY49c4c$ZzCK5T4u2J5s5zWdr8r6326K49G84Pe2An^" +
                    "WRV*65$mZQv8&22YV7RnySkM7E2q72MDHhG6S4pRWA7252e#29362fC" +
                    "E9%KT5jn5E@q3&v86qh23a3G8zrV43526HVB*C7U52qyf2Y9eqEUm6v" +
                    "CruQ5f9463M3EF559zPFk8M3aMr4s5@A44B79";

    private final static int PBKDF2Iterations = 100000;

    private final static char[] b64CharMap = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'
    };

    private static byte[] b64ByteMap = new byte[128];
    static {
        for (int i = 0; i < b64ByteMap.length; i++)
        {
            b64ByteMap[i] = -1;
        }
        for (int i = 0; i < 64; i++)
        {
            b64ByteMap[b64CharMap[i]] = (byte)i;
        }
    };

    public SymmetricCryptoProvider()
    {

    }

    public char[] encodeBase64(byte[] in)
    {
        int iLen = in.length;
        int oDataLen = (iLen * 4 + 2) / 3;
        int oLen = ((iLen + 2) / 3) * 4;
        char[] out = new char[oLen];
        int ip = 0;
        int op = 0;

        while (ip < iLen)
        {
            int i0 = in[ip++] & 0xff;
            int i1 = ip < iLen ? in[ip++] & 0xff : 0;
            int i2 = ip < iLen ? in[ip++] & 0xff : 0;
            int o0 = i0 >>> 2;
            int o1 = ((i0 & 3) << 4) | (i1 >>> 4);
            int o2 = ((i1 & 0xf) << 2) | (i2 >>> 6);
            int o3 = i2 & 0x3F;

            out[op++] = b64CharMap[o0];
            out[op++] = b64CharMap[o1];
            out[op] = op < oDataLen ? b64CharMap[o2] : '=';
            op++;
            out[op] = op < oDataLen ? b64CharMap[o3] : '=';
            op++;
        }
        return out;
    }

    public byte[] decodeBase64(char[] in)
    {
        int iLen = in.length;

        if (iLen % 4 != 0)
        {
            return null;
        }
        while (iLen > 0 && in[iLen - 1] == '=')
        {
            iLen--;
        }

        int oLen = (iLen * 3) / 4;
        byte[] out = new byte[oLen];
        int ip = 0;
        int op = 0;

        while (ip < iLen)
        {
            int i0 = in[ip++];
            int i1 = in[ip++];
            int i2 = ip < iLen ? in[ip++] : 'A';
            int i3 = ip < iLen ? in[ip++] : 'A';

            if (i0 > 127 || i1 > 127 || i2 > 127 || i3 > 127)
            {
                return null;
            }

            int b0 = b64ByteMap[i0];
            int b1 = b64ByteMap[i1];
            int b2 = b64ByteMap[i2];
            int b3 = b64ByteMap[i3];

            if (b0 < 0 || b1 < 0 || b2 < 0 || b3 < 0)
            {
                return null;
            }

            int o0 = (b0 << 2) | (b1 >>> 4);
            int o1 = ((b1 & 0xf) << 4) | (b2 >>> 2);
            int o2 = ((b2 & 3) << 6) | b3;

            out[op++] = (byte)o0;

            if (op < oLen)
            {
                out[op++] = (byte)o1;
            }
            if (op < oLen)
            {
                out[op++] = (byte)o2;
            }
        }
        return out;
    }

    /**
     *
     * @param password
     * @param salt
     * @param iterations
     * @param length
     * @return
     * @throws Exception
     *
     *
     * The function takes a password (p), a salt (s) an iteration count (i) and a key output length (l) in bits.
     * It then uses PBKDF2 using an SHA-1 HMAC to generate a key.
     * Unfortunately Java 7 doesn’t support PBKDF2WithHmacSHA256 and it isn’t recommended to generate
     * a larger output key than your hashing algorithm (i.e. 160 bits when using SHA-1)
     * as it will result in multiple executions of the function at a computational cost to the defender.
     * It will return a key of size (l) in bits.
     */
    private byte[] deriveKey(String password, byte[] salt, int iterations, int length) throws Exception
    {
        logger.debug("Inside  -- deriveKey -- method...");
        PBEKeySpec ks = new PBEKeySpec(password.toCharArray(), salt, iterations, length);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

        return skf.generateSecret(ks).getEncoded();
    }


    /**
     *
     * @param stringToBeEncrypted
     * @param passwordForEncryption
     * @return
     * @throws Exception
     *
     *
     * We start off by creating a 160 bit salt to derive our encryption key using “PBKDF2WithHmacSHA1”
     * with 100,000 iterations (160 bits because SHA-1 generates 160 bit hashes).
     * This generates a 128 bit encryption key that we use for SymmetricCryptoProvider in CTR mode.
     * We pass through an empty IV as we never use the same key twice
     * but always generate a new salt and key before encrypting.
     * Once we have our ciphertext (es) we then create a new 160 bit salt
     * to derive our HMAC key using the same process – the only difference being
     * we generate a 160 bit key (in theory HMAC-SHA-256 benefits from a 256 bit key,
     * but Java is lacking in being able to produce that – Java 8 supports “PBKDF2WithHmacSHA256”).
     * We then perform a HMAC operation over the ciphertext using our key.
     * If anyone was to fiddle with our encrypted string or the HMAC then the validation would fail
     * and we would know someone has tampered with it.
     * The final step is to combine all of them together – the encryption salt (esalt), the HMAC salt (hsalt),
     * the ciphertext (es) and the actual HMAC (hmac) into a single byte array that we encode using Base64
     * and return as a String.
     */
    public String encrypt(String stringToBeEncrypted, String passwordForEncryption) throws Exception
    {
        logger.debug("Inside  -- encrypt -- method...");

        SecureRandom r = SecureRandom.getInstance("SHA1PRNG");

        // At first we create a new encryption key based on our provided Password
        byte[] encryptionSalt = new byte[20];
        r.nextBytes(encryptionSalt);
        byte[] dek = deriveKey(passwordForEncryption, encryptionSalt, PBKDF2Iterations, 128);

        logger.debug("Encryption -- Derived Key Produced as EncryptionKey is " + new String(encodeBase64(dek)));

        // we then encrypt the String value we want using the generated key from above based on our provided password
        SecretKeySpec eks = new SecretKeySpec(dek, "AES");
        Cipher c = Cipher.getInstance("AES/CTR/NoPadding");
        c.init(Cipher.ENCRYPT_MODE, eks, new IvParameterSpec(new byte[16]));
        byte[] cipherText = c.doFinal(stringToBeEncrypted.getBytes(StandardCharsets.UTF_8));

        logger.debug("Encryption -- Ciphered Text Produced is " + new String(encodeBase64(cipherText)));

        // we then create a new key to hash the cipheredText
        byte[] hmacSalt = new byte[20];
        r.nextBytes(hmacSalt);
        byte[] dhk = deriveKey(passwordForEncryption, hmacSalt, PBKDF2Iterations, 160);

        logger.debug("Encryption -- Derived Key Produced to MAC CipheredText is " + new String(encodeBase64(dhk)));

        SecretKeySpec hks = new SecretKeySpec(dhk, "HmacSHA256");
        Mac m = Mac.getInstance("HmacSHA256");
        m.init(hks);
        byte[] hmac = m.doFinal(cipherText);

        logger.debug("Encryption -- HMAC produced from CipheredText " + new String(encodeBase64(hmac)));

        byte[] os = new byte[40 + cipherText.length + 32];
        System.arraycopy(encryptionSalt, 0, os, 0, 20);
        System.arraycopy(hmacSalt, 0, os, 20, 20);
        System.arraycopy(cipherText, 0, os, 40, cipherText.length);
        System.arraycopy(hmac, 0, os, 40 + cipherText.length, 32);

        logger.debug("Encryption -- Encrypted then MACed String produced is: " + new String(encodeBase64(os)));

        return new String(encodeBase64(os));
    }


    /**
     *
     * @param encryptedString
     * @param passwordForDectyption
     * @return
     * @throws Exception
     *
     * The decryption routine is the opposite of the encryption routine – we start off by decoding the Base64 string
     * and then recovering the encryption salt (esalt), the HMAC salt (hsalt), the ciphertext (es) and the actual HMAC
     * (hmac) into separate byte arrays. We then regenerate the HMAC key using the HMAC salt
     * (you need to ensure you match the number of PBKDF2 iterations on encryption and decryption).
     * If the computed HMAC matches the recovered HMAC then no one has tampered with our data.
     * We then regenerate our encryption key using PBKDF2 from the recovered encryption salt
     * and finally decrypt the ciphertext and return the plaintext. If any errorhandling occur
     * (i.e.HMAC verification failed, invalid password or something wrong with the encoded string)
     * then an Exception is thrown.
     */
    public String decrypt(String encryptedString, String passwordForDectyption) throws Exception
    {
        logger.debug("Inside  -- decrypt -- method...");

        logger.debug("Decryption -- Encrypted then MACed String is: " + encryptedString);

        byte[] os = decodeBase64(encryptedString.toCharArray());


        if (os.length > 72)
        {
            byte[] esalt = Arrays.copyOfRange(os, 0, 20);
            byte[] hsalt = Arrays.copyOfRange(os, 20, 40);
            byte[] es = Arrays.copyOfRange(os, 40, os.length - 32);
            byte[] hmac = Arrays.copyOfRange(os, os.length - 32, os.length);

            // we create the HMAC again
            byte[] dhk = deriveKey(passwordForDectyption, hsalt, PBKDF2Iterations, 160);

            logger.debug("Decryption -- Derived Key Produced to reconstruct HMAC from SymmetricKey + hmac Salt recovered from encrypted String is: " + new String(encodeBase64(dhk)));

            // we recreate the HMAC for the chipheredText using the SecretKey password
            SecretKeySpec hks = new SecretKeySpec(dhk, "HmacSHA256");
            Mac m = Mac.getInstance("HmacSHA256");
            m.init(hks);
            byte[] chmac = m.doFinal(es);

            logger.debug("Decryption -- Recreated HMAC is: " + new String(encodeBase64(chmac)));

            logger.debug("Decryption -- Old HMAC is: " + new String(encodeBase64(hmac)));

            // we check the match of the newly created HMAC with the old one from the provided String
            if (MessageDigest.isEqual(hmac, chmac))
            {

                // Only if the HMAC match then we decrypt the ciphered text with our data
                byte[] dek = deriveKey(passwordForDectyption, esalt, PBKDF2Iterations, 128);

                logger.debug("Decryption -- Derived Key produced to decrypt ciphered text is: " + new String(encodeBase64(dek)));

                SecretKeySpec eks = new SecretKeySpec(dek, "AES");
                Cipher c = Cipher.getInstance("AES/CTR/NoPadding");
                c.init(Cipher.DECRYPT_MODE, eks, new IvParameterSpec(new byte[16]));
                byte[] s = c.doFinal(es);

                logger.debug("Decryption -- Decrypted CipheredText is: " + new String(s, StandardCharsets.UTF_8));

                return new String(s, StandardCharsets.UTF_8);
            }

        }
        throw new Exception();
    }

    /**
     *
     * @param valueToBeHashed
     * @return
     * @throws Exception
     *
     * this is based on what I have already talked about when generating keys for SymmetricCryptoProvider encryption.
     * We start off by generating a random salt which matches the length
     * of our HMAC algorithm (e.g. 160 bits for SHA-1).
     * We then generate a 160 bit key from the salt and our password using PBKDF2 and 100,000 iterations.
     * Finally we add the salt to the hash and encode using Base64.
     */
    public String generateHash(String valueToBeHashed) throws Exception
    {
        logger.debug("Inside  -- generateHash -- method...");

        SecureRandom r = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[20]; r.nextBytes(salt);
        byte[] hash = deriveKey(valueToBeHashed, salt, PBKDF2Iterations, 160);
        byte[] os = new byte[20 + 20];
        System.arraycopy(salt, 0, os, 0, 20);
        System.arraycopy(hash, 0, os, 20, 20);

        logger.debug("GenerateHash -- Decrypted CipheredText is: " + new String(encodeBase64(os)));

        return new String(encodeBase64(os));
    }


    /**
     *
     * @param valueToBeCheckedWithOldHashedValue
     * @param storedHash
     * @return
     * @throws Exception
     *
     * To authenticate a user we reverse the process – we obtain the hash from the database
     * and pass it with the candidate password to a function.
     * This obtains the previously generated salt and hash from the Base64 encoded string.
     * It then re-generates the key using the candidate password and salt
     * via PBKDF2 (the number of iterations have to match).
     * Finally it compares the hash from the database with the hash it has generated
     * from the candidate password – if they match then the user has provided the correct password.
     * Please avoid using “Arrays.equals()” to compare cryptographic hashes
     * as they open you up to timing based attacks – you should be using a time constant comparison function,
     * which takes the same amount of time regardless of outcome.
     * Comparison functions which return as soon as they have found a difference allow an attacker
     * to guess hashes one character at a time. Java provides “MessageDigest.isEqual()” for this purpose,
     * but be aware that it was vulnerable to the very same timing attacks before it was fixed in Java 6 (Update 17).
     *
     */
    public boolean authenticateHashes(String valueToBeCheckedWithOldHashedValue, String storedHash) throws Exception
    {
        logger.debug("Inside  -- authenticateHashes -- method...");

        logger.debug("authenticateHashes -- Provided value to produce a new hash is: " + valueToBeCheckedWithOldHashedValue);
        logger.debug("authenticateHashes -- Based64 Encoded String to provide the Old Hash is : " + storedHash);

        byte[] os = decodeBase64(storedHash.toCharArray());

        if (os.length == 40)
        {
            logger.debug("--Split Base64Encoded String to 2 parts: salt And hash");

            byte[] salt = Arrays.copyOfRange(os, 0, 20);
            logger.debug("authenticateHashes -- Salt recovered from Base64Encoded String is: " + new String(encodeBase64(salt)));

            byte[] hash = Arrays.copyOfRange(os, 20, 40);
            logger.debug("authenticateHashes -- Old Hash String recovered from Base64Encoded String is: " + new String(encodeBase64(hash)));

            byte[] phash = deriveKey(valueToBeCheckedWithOldHashedValue, salt, PBKDF2Iterations, 160);
            logger.debug("authenticateHashes -- New Hash String is: " + new String(encodeBase64(phash)));


            logger.debug("authenticateHashes -- Two hashes are equal = " + MessageDigest.isEqual(hash, phash));

            return MessageDigest.isEqual(hash, phash);
        }
        return false;
    }

    public final String getSecretKeyPasswordForAuthentication()
    {
        return SecretKeyPasswordForAuthentication;
    }

}

