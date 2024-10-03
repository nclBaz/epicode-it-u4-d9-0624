package riccardogulin.files;

import org.apache.commons.io.FileUtils;
import riccardogulin.entities.User;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FilesMain {
	public static void main(String[] args) {
		File file = new File("src/info.txt");


		try {
			User aldo = new User("Aldo", "Baglio", 20, "New York");
			FileUtils.writeStringToFile(file, aldo.getName() + " " + aldo.getSurname() + System.lineSeparator(), StandardCharsets.UTF_8, true);
			// true significa che voglio che al file vengano aggiunti i contenuti non sovrascritti
			// System.lineSeparator() è un "a capo" smart nel senso che controlla su che sistema operativo siamo per usare il carattere giusto

			String fileContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
			System.out.println(fileContent);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// FileUtils.readFileToString();
	}
}
