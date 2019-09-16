package com.example.ebi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccessingDataMongodbApplication implements CommandLineRunner {

	@Autowired
	private SequenceRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataMongodbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// repository.deleteAll();

		// // save a couple of customers
		// repository.save(new Sequence("id_1"));
		// repository.save(new Customer("Bob", "Smith"));

		// fetch all Sequences
		System.out.println("Sequences found with findAll():");
		System.out.println("-------------------------------");
		for (Sequence seq : repository.findAll()) {
			System.out.println(seq);
		}
		System.out.println();

		// fetch an individual customer
		System.out.println(repository.findBySequenceId("ERZ1022724.3"));

		// System.out.println("Customers found with findByLastName('Smith'):");
		// System.out.println("--------------------------------");
		// for (Customer customer : repository.findByLastName("Smith")) {
		// 	System.out.println(customer);
		// }

	}
}
