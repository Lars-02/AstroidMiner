package memento;

import java.io.*;
import java.util.Base64;

public class Memento<T extends Restorable<S>, S extends Serializable> {
    private final String backup;
    private final T restorable;

    public Memento(T restorable) {
        this.restorable = restorable;

        String serializedObject = "";

        try {
            var byteArrayOutputStream = new ByteArrayOutputStream();
            var objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.writeObject(restorable.serializableState());
            objectOutputStream.close();

            serializedObject = Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.backup = serializedObject;
    }

    public void restore() {
        try {
            var data = Base64.getDecoder().decode(backup);
            var objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));

            @SuppressWarnings("unchecked")
            var state = (S) objectInputStream.readObject();

            restorable.restore(state);

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
}
