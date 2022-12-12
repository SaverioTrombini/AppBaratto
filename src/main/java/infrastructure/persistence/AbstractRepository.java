package infrastructure.persistence;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class AbstractRepository<T> {

	private T t;
	private final String filename;

	public AbstractRepository(String filename) throws ClassNotFoundException, IOException {
		this.filename = filename;
		File f = new File(filename);
		f.getParentFile().mkdir();
		if (f.exists()) {
			t = load();
		} else {
			t = build();
		}
	}

	protected abstract T build();

	@SuppressWarnings("unchecked")
	protected T load() throws IOException, ClassNotFoundException {
		try (ObjectInputStream inputStream = new ObjectInputStream(
				new BufferedInputStream(new FileInputStream(filename)))) {
			return (T) inputStream.readObject();
		}
	}

	protected void save() {
		File f = new File(filename);
		try (ObjectOutputStream outputStream = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(f)))) {
			outputStream.writeObject(t);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public T getT() {
		return t;
	}
}
