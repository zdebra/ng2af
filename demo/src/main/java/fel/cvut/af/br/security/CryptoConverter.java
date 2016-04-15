package fel.cvut.af.br.security;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.Stateless;
import javax.persistence.AttributeConverter;
import java.security.Key;


/**
 * Created by zb on 13.1.16.
 * Source: http://www.thoughts-on-java.org/how-to-use-jpa-type-converter-to/
 *
 * This converter is runned everytime JPA makes some action on User.password, defined inside resources/META-INF/orm.xml
 */
@Stateless
public class CryptoConverter implements AttributeConverter<String,String>{

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final byte[] KEY = "A1xsdrS1s4deGHsA".getBytes(); // 16 bytes

    @Override
    public String convertToDatabaseColumn(String ccNumber) {
        // do some encryption
        Key key = new SecretKeySpec(KEY, "AES");
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.ENCRYPT_MODE, key);
            String encoded = new String(Base64.encodeBase64(c.doFinal(ccNumber.getBytes())));
            return encoded;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        // do some decryption
        Key key = new SecretKeySpec(KEY, "AES");
        try {
            Cipher c = Cipher.getInstance(ALGORITHM);
            c.init(Cipher.DECRYPT_MODE, key);
            String decoded = new String(c.doFinal(Base64.decodeBase64(dbData.getBytes())));
            return decoded;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
