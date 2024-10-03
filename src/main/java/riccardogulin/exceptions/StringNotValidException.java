package riccardogulin.exceptions;

public class StringNotValidException extends Exception {
	// Se estendo Exception questa sarà un'eccezione di tipo CHECKED
	public StringNotValidException(String str) {
		super("La stringa inserita: " + str + ", non è valida!"); // Nel costruttore del padre inserisco quello che sarà impostato come messaggio di errore
	}
}
