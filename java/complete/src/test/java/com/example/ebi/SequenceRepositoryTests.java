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

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.ebi.Sequence;
import com.example.ebi.SequenceRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SequenceRepositoryTests {

    @Autowired
    SequenceRepository repository;

    // Customer dave, oliver, carter;

    @Before
    public void setUp() {

        // repository.deleteAll();

        // dave = repository.save(new Customer("Dave", "Matthews"));
        // oliver = repository.save(new Customer("Oliver August", "Matthews"));
        // carter = repository.save(new Customer("Carter", "Beauford"));
    }

    @Test
    public void findsBySequenceId() {

        Sequence result = repository.findBySequenceId("ERZ1022724.3");
    }
}