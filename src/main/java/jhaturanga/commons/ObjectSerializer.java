package jhaturanga.commons;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;

/**
 * An Object Serializer/Deserializer.
 */
public final class ObjectSerializer {

    private ObjectSerializer() {
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

    /**
     * Read the object from Base64 string.
     * 
     * @param path - the serialized file path
     * @return the deserialized object
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Optional<Object> loadFromFile(final String path) {
        try {
            final String content = Files.readString(Paths.get(path));
            return Optional.of(fromString(content));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Write the object to a file.
     * 
     * @param object - the object to serialize
     * @param path   - the path of file
     * @throws IOException
     */
    public static void saveToFile(final Serializable object, final String path) {
        try (FileWriter file = new FileWriter(path)) {
            file.write(toString(object));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
