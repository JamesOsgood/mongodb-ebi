package com.example.ebi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Criteria;

@SpringBootApplication
public class AccessingDataMongodbApplication implements CommandLineRunner {

	@Autowired
	private SequenceRepository repository;
	@Autowired
	private MongoOperations operations;

	public static void main(String[] args) {
		SpringApplication.run(AccessingDataMongodbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// repository.deleteAll();

		Sequence seq2 = repository.findBySequenceId("ERZ1022724.4");
		System.out.println(seq2);
		seq2.sequenceId = "Hello";

		//search a document that doesn't exist
		Query query = new Query();
		query.addCriteria(Criteria.where("sequenceId").is(seq2.sequenceId));

		Update update = new Update();
		update.inc("dup_count");

		update.setOnInsert("sequenceId", seq2.sequenceId);
		update.setOnInsert("seq", seq2.seq);
		update.setOnInsert("description", seq2.description);

		operations.upsert(query, update, Sequence.class);

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
		System.out.println("--------------------------------");
		System.out.println(repository.findBySequenceId("ERZ1022724.3"));
		System.out.println("--------------------------------");

		// System.out.println("Customers found with findByLastName('Smith'):");
		// System.out.println("--------------------------------");
		// for (Customer customer : repository.findByLastName("Smith")) {
		// 	System.out.println(customer);
		// }

	}
}
