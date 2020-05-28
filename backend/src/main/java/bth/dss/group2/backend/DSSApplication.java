package bth.dss.group2.backend;

import javax.transaction.Transactional;

import bth.dss.group2.backend.service.DataPopulatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DSSApplication implements CommandLineRunner {

	@Autowired
	DataPopulatorService dataPopulatorService;

	public static void main(String[] args) {
		SpringApplication.run(DSSApplication.class, args);
	}

	@Transactional
	@Override
	public void run(String... args) throws Exception {
		dataPopulatorService.populate();
	}
}
