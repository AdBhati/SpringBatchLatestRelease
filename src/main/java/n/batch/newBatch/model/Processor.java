package n.batch.newBatch.model;

import jakarta.persistence.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import java.sql.SQLOutput;
import java.util.UUID;


public class Processor implements ItemProcessor<User,User> {


   private static final Logger log = LoggerFactory.getLogger(Processor.class);



    @Override
    public User process(User user) throws Exception {

        final UUID Id = user.getId();
        final String firstName = user.getFirstName().toUpperCase();
        final String lastName = user.getLastName().toUpperCase();

        final User transformedPerson = new User (Id, firstName , lastName);


        log.info("Converting (" + user + ") into (" + transformedPerson + ")");

        System.out.println("Converting (" + user + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }

    }


