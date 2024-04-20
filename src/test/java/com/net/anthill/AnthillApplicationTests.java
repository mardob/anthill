package com.net.anthill;

import com.net.anthill.controller.NoteController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SmokeTest {


	@Autowired
	private NoteController noteController;

	@Test
	void contextLoads() {
		assertThat(noteController).isNotNull();
	}

}
