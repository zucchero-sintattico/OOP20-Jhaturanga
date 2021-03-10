package jhaturanga.commons.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

/**
 * Usage sample serializing SomeClass instance.
 */
public final class ToStringSample {

    private ToStringSample() {

    }

    /**
     * Read the object from Base64 string.
     * 
     * @param serializedObject - the serialized Object
     * @return the deserialized object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object fromString(final String serializedObject) throws IOException, ClassNotFoundException {
        final byte[] data = Base64.getDecoder().decode(serializedObject);
        final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        final Object o = ois.readObject();
        ois.close();
        return o;
    }

    /**
     * Write the object to a Base64 string.
     * 
     * @param object - the object to serialize
     * @return the serialized object
     * @throws IOException
     */
    public static String toString(final Serializable object) throws IOException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }
}
