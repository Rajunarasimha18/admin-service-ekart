package com.raju.admin.common;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


@Service
public class SequenceGeneratorService {

    @Autowired
    private MongoOperations mongoOperations;

 
    public synchronized long generateSequence(String seqName) {
        // Find and increment the sequence value atomically
        DatabaseSequence counter = mongoOperations.findAndModify(
                Query.query(Criteria.where("_id").is(seqName)),
                new Update().inc("seq", 1),
                options().returnNew(true).upsert(true),
                DatabaseSequence.class
        );
        
        // Return the incremented value or start from 1 if sequence doesn't exist
        return counter != null ? counter.getSeq() : 1;
    }
}
