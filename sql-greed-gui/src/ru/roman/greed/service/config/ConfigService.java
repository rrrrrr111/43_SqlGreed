package ru.roman.greed.service.config;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import ru.roman.greed.service.config.reader.XmlReader;
import ru.roman.greed.service.config.writer.XmlWriter;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @author Roman 15.09.12 18:41
 */
public class ConfigService {
    private static final Log log = LogFactory.getLog(ConfigService.class);
    private static final String ALGORITHM = "PBEWithMD5AndDES";
    private static final byte[] SALT = {
            (byte) 0xf5, (byte) 0x53, (byte) 0x31, (byte) 0x29,
            (byte) 0xd2, (byte) 0xfc, (byte) 0x44, (byte) 0x7a,};


    public <T> T loadConfig(String fileName, XmlReader<T> reader) {

        FileInputStream is = null;
        try {
            is = new FileInputStream(fileName);
            final Document doc = readDocument(is);
            //log.trace("Loaded XML : \n" + docToString(doc));
            return reader.read(doc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public <T> T loadEncryptedConfig(String fileName, XmlReader<T> reader) {

        CipherInputStream is = null;
        FileInputStream fis = null;
        try {
            final Cipher cipher = initCipher(Cipher.DECRYPT_MODE);
            fis = new FileInputStream(fileName);
            is = new CipherInputStream(fis, cipher);
            final Document doc = readDocument(is);
            //log.trace("Loaded decrypted XML : \n" + docToString(doc));
            return reader.read(doc);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(fis);
        }
    }


    public <T> void saveConfig(T model, String fileName, XmlWriter<T> writer) {

        FileOutputStream os = null;
        try {
            os = new FileOutputStream(fileName);
            writeXml(model, writer, os);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    public <T> void saveEncryptedConfig(T model, String fileName, XmlWriter<T> writer) {

        FileOutputStream fos = null;
        CipherOutputStream os = null;
        try {
            final Cipher cipher = initCipher(Cipher.ENCRYPT_MODE);
            fos = new FileOutputStream(fileName);
            os = new CipherOutputStream(fos, cipher);
            writeXml(model, writer, os);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(fos);
        }
    }

    private <T> void writeXml(T model, XmlWriter<T> writer, OutputStream os) {
        try {
            final StreamResult streamResult = new StreamResult(os);
            final SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
            final TransformerHandler handler = tf.newTransformerHandler();
            final Transformer serializer = handler.getTransformer();
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            handler.setResult(streamResult);
            handler.startDocument();
            writer.write(handler, model);
            handler.endDocument();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(os);
        }
    }

    public String docToString(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (TransformerException ex) {
            throw new RuntimeException(ex);
        }
    }


    private Document readDocument(InputStream is) {
        try {
            final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            final Document doc = docBuilder.parse(is);
            doc.getDocumentElement().normalize();
            return doc;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    private Cipher initCipher(int mode) throws InvalidKeyException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException {
        final PBEKeySpec spec = new PBEKeySpec(ALGORITHM.toCharArray());
        final SecretKeyFactory fact = SecretKeyFactory.getInstance(ALGORITHM);
        final SecretKey secretKey = fact.generateSecret(spec);
        final PBEParameterSpec parameterSpec = new PBEParameterSpec(SALT, 100);
        final Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(mode, secretKey, parameterSpec);
        return cipher;
    }

}
