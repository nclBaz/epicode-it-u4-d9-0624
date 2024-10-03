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
	}
}
