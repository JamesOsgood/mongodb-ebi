package com.example.ebi;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SequenceRepository extends MongoRepository<Sequence, String> {

    public Sequence findBySequenceId(String sequence_id);

}
