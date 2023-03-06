package n.batch.newBatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class NewBatchApplication {

	public static void main(String[] args) throws Exception{
		//SpringApplication.run(NewBatchApplication.class, args);
		System.exit(SpringApplication.exit(SpringApplication.run(NewBatchApplication.class, args)));
	}

}
