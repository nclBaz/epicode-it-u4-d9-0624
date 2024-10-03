package riccardogulin;

import com.github.javafaker.Faker;
import riccardogulin.entities.User;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Application {

	public static void main(String[] args) {
		Supplier<User> usersSupplier = () -> {
			Faker faker = new Faker(Locale.ITALY);
			Random rndm = new Random();
			return new User(faker.lordOfTheRings().character(), faker.name().lastName(), rndm.nextInt(1, 100), faker.lordOfTheRings().location());
		};

		List<User> randomUsers = new ArrayList<>();

		for (int i = 0; i < 100; i++) {
			randomUsers.add(usersSupplier.get());
		}

		// ****************************************** COLLECTORS ***************************************
		// 1. Raggruppiamo gli user per città
		Map<String, List<User>> usersByCity = randomUsers.stream().filter(user -> user.getAge() > 18).collect(Collectors.groupingBy(user -> user.getCity()));
		usersByCity.forEach((city, usersList) -> System.out.println("Città: " + city + ", " + usersList));

		// 2. Raggruppiamo gli user per età
		Map<Integer, List<User>> usersByAge = randomUsers.stream().collect(Collectors.groupingBy(user -> user.getAge()));
		usersByAge.forEach((age, usersList) -> System.out.println("Età: " + age + ", " + usersList));

		// 3. Concateniamo tutti i nomi e cognomi degli user, ottenendo qualcosa tipo "Aldo Baglio, Giovanni Storti, Giacomo Poretti, ..."
		String nomiECognomi = randomUsers.stream().map(user -> user.getName() + " " + user.getSurname()).collect(Collectors.joining(", "));
		System.out.println(nomiECognomi);

		// 4. Concateniamo tutte le età
		String età = randomUsers.stream().map(user -> "" + user.getAge()).collect(Collectors.joining(", "));
		System.out.println(età);

		// 5. Calcoliamo la somma delle età
		int sum = randomUsers.stream().collect(Collectors.summingInt(user -> user.getAge()));
		System.out.println("La somma delle età è: " + sum);

		// 6. Calcoliamo la media delle età
		double average = randomUsers.stream().collect(Collectors.averagingInt(user -> user.getAge()));
		System.out.println("La media delle età è: " + average);

		// 7. Raggruppiamo gli user per città e calcoliamo le medie delle età per ognuna di esse
		Map<String, Double> averageAgePerCity = randomUsers.stream().collect(Collectors.groupingBy(user -> user.getCity(), Collectors.averagingInt(user -> user.getAge())));
		averageAgePerCity.forEach((city, averageAge) -> System.out.println("Città: " + city + ", " + averageAge));

		// 8. Raggruppiamo per città e calcoliamo varie statistiche come media età, somma età, età minima, età massima, ecc
		Map<String, IntSummaryStatistics> statsPerCity = randomUsers.stream().collect(Collectors.groupingBy(user -> user.getCity(), Collectors.summarizingInt(user -> user.getAge())));
		statsPerCity.forEach((city, stats) -> System.out.println("Città: " + city + ", " + stats));

		// ****************************************** COMPARATORS ***************************************
		System.out.println("****************************************** COMPARATORS ***************************************");
		// 1. Ordiniamo la lista utenti per età (Ordine crescente)
		List<User> usersSortedByAge = randomUsers.stream().sorted(Comparator.comparingInt(user -> user.getAge())).toList();
		usersSortedByAge.forEach(user -> System.out.println(user));

		// 2. Ordiniamo la lista utenti per età (Ordine decrescente)
		System.out.println("Ordiniamo la lista utenti per età (Ordine decrescente)");
		List<User> usersSortedByAgeDesc = randomUsers.stream().sorted(Comparator.comparingInt(User::getAge).reversed()).toList();
		// Se si utilizza la reversed non ci è permesso di utilizzare la lambda ma devo utilizzare la forma compatta User::getAge
		usersSortedByAgeDesc.forEach(user -> System.out.println(user));

		// 3. Ordiniamo la lista utenti per cognome (Ordine crescente)
		System.out.println("Ordiniamo la lista utenti per cognome (Ordine crescente)");
		List<User> usersSortedBySurname = randomUsers.stream().sorted(Comparator.comparing(User::getSurname)).toList();
		usersSortedBySurname.forEach(user -> System.out.println(user));

		// ****************************************** LIMIT ***************************************
		System.out.println("****************************************** LIMIT ***************************************");
		// 1. Otteniamo i 5 user più vecchi, tramite il sorted li ordino per età decrescente, poi tramite limit ne tengo solo i primi 5.
		// Opzionalmente posso usare skip() per anche decidere di saltare tot elementi per ottenere ad esempio gli elementi dal 96esimo al 100esimo
		List<User> fiveOldUsers = randomUsers.stream().sorted(Comparator.comparingInt(User::getAge).reversed()).skip(95).limit(5).toList();
		System.out.println(fiveOldUsers);

		// ****************************************** MAP TO ***************************************
		// 1. Calcolo della somma delle età tramite reduce
		System.out.println("1. Calcolo della somma delle età tramite reduce");
		int totalAge = randomUsers.stream().map(User::getAge).reduce(0, (acc, age) -> acc + age);
		System.out.println("Somma delle età tramite reduce: " + totalAge);

		// 2. Calcolo della somma delle età tramite mapToInt
		System.out.println("2. Calcolo della somma delle età tramite mapToInt");
		int totalAge2 = randomUsers.stream().mapToInt(user -> user.getAge()).sum();
		System.out.println("Somma delle età tramite mapToInt: " + totalAge2);

		// 3. Calcolo della media delle età tramite mapToInt
		System.out.println("3. Calcolo della media delle età tramite mapToInt");
		OptionalDouble average2 = randomUsers.stream().mapToInt(user -> user.getAge()).average();
		if (average2.isPresent()) System.out.println("La media è: " + average2.getAsDouble());
			// Se la media c'è (la lista ha più di zero elementi) ci facciamo tornare un double
		else System.out.println("Non è possibile calcolare la media perché la lista è vuota");

		// 4. Calcolo dell'età maggiore tramite mapToInt
		System.out.println("4. Calcolo dell'età maggiore tramite mapToInt");
		OptionalInt maxAge = randomUsers.stream().mapToInt(user -> user.getAge()).max();
		if (maxAge.isPresent()) System.out.println("L'età massima è: " + maxAge.getAsInt());
		else System.out.println("Non è possibile trovare il più vecchio perché la lista è vuota");

		// 5. Ottenimento di statistiche sulle età tramite mapToInt
		System.out.println("5. Ottenimento di statistiche sulle età tramite mapToInt");
		IntSummaryStatistics statistics = randomUsers.stream().mapToInt(user -> user.getAge()).summaryStatistics();
		System.out.println(statistics);
	}
}
