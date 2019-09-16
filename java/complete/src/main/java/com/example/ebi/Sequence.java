package com.example.ebi;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sequences_sample")
public class Sequence {

    // @Id
    @Field("sequence_id")
    public String sequenceId;
    public SequenceDescription seq;
    public String description;
    public String dup_count;

    public Sequence(String sequenceId) 
    {
        this.sequenceId = sequenceId;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, description='%s', seq_hash=%s]",
                sequenceId, description, seq.hash);
    }

}

