package com.example.ebi;
/*
 * Copyright 2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import static org.assertj.core.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.omg.CORBA.UnknownUserExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ebi.Sequence;
import com.example.ebi.SequenceRepository;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Criteria;

import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SequenceRepositoryTests {

    @Autowired
    SequenceRepository repository;

    @Autowired
	  private MongoOperations operations;

    @Before
    public void setUp() {
    }

    // @Ignore 
    @Test
    public void importData() throws IOException, 
                                    UnsupportedEncodingException, 
                                    NoSuchAlgorithmException
    {
        repository.deleteAll();  
        Reader reader = Files.newBufferedReader(Paths.get("/Users/james/code/github/JamesOsgood/mongodb-ebi/java/complete/data/sample_data.csv"));
        BufferedReader csvReader = new BufferedReader(reader);
        MessageDigest messageDigest = MessageDigest.getInstance("md5");
        String row = csvReader.readLine();
        while ((row = csvReader.readLine()) != null) 
        {
            String[] data = row.split(",");
            // File is _id,sequence_id,meta,description,seq
            int index = 1;
            String sequenceId = data[index++];
            String meta = data[index++];
            String description = data[index++];
            String seq = data[index++];
            SequenceDescription sd = new SequenceDescription();

            messageDigest.reset();
            messageDigest.update(seq.getBytes("UTF8"));
            
            final byte[] resultByte = messageDigest.digest();
            sd.hash = encodeHexString(resultByte);
            sd.sequence = seq;

            Sequence sequence = new Sequence(sequenceId, sd, description);
            sequence.meta = meta;
            repository.insert(sequence);
      }
    }

    @Ignore 
    @Test
    public void findBySequenceId() {

        Sequence result = repository.findBySequenceId("ERZ1022724.3");
		    System.out.println(result);
    }

    // @Ignore 
    @Test
    public void findBySequenceHash() {

      Query query = new Query();
      String hash = "d0ae32f818a2f384c567c6518bee376a";
      query.addCriteria(Criteria.where("seq.hash").is(hash));

      Sequence sequence = operations.findOne(query, Sequence.class);
      System.out.println(sequence);
    }

    @Ignore 
    @Test
    public void insertSequenceIgnoreDuplicates() 
    {
      // For the exercise, find a sequence to clone
      Sequence seq2 = repository.findBySequenceId("ERZ1022724.1");
      System.out.println(seq2);
      
      // Fake it to be a new one....
      seq2.sequenceId = "NewSeq";
      seq2.seq.hash = "new_hash";

      // Write code so that we just update dup_count = dup_count + 1 if the hash exists
      // Or else insert a new document

      Query query = new Query();
      query.addCriteria(Criteria.where("seq.hash").is(seq2.seq.hash));

      Update update = new Update();
      update.inc("dup_count");

      update.setOnInsert("sequenceId", seq2.sequenceId);
      update.setOnInsert("seq", seq2.seq);
      update.setOnInsert("description", seq2.description);
      update.setOnInsert("meta", seq2.meta);

      operations.upsert(query, update, Sequence.class);
    }

  public static String byteToHex(byte num) {
      char[] hexDigits = new char[2];
      hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
      hexDigits[1] = Character.forDigit((num & 0xF), 16);
      return new String(hexDigits);
  }
  
  public static String encodeHexString(byte[] byteArray) {
    StringBuffer hexStringBuffer = new StringBuffer();
    for (int i = 0; i < byteArray.length; i++) {
        hexStringBuffer.append(byteToHex(byteArray[i]));
    }
    return hexStringBuffer.toString();
}}