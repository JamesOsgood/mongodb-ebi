package com.example.ebi;

public class SequenceDescription {

    public String sequence;
    public String hash;

    public SequenceDescription() {}

    public SequenceDescription(String sequence, String hash) {
        this.sequence = sequence;
        this.hash = hash;
    }

    @Override
    public String toString() {
        return String.format(
                "SequenceDescription[hash=%s]",
                hash);
    }

}

