package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
class CashCardJsonTest {
	
	@Autowired
	private JacksonTester<CashCard> json;
	
	@Test
	void cashCardSerializationTest() throws IOException{
		CashCard cashCard = new CashCard(99L, 123.45);
		
		assertThat(json.write(cashCard)).isStrictlyEqualToJson("expected.json");
		
		assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id").isEqualTo(99);
		
		assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
		
		assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount").isEqualTo(123.45);
	}
	
	@Test
	void cashCardDeserializationTest() throws IOException{
		String request = """
				{
					"id" : 99,
					"amount" : 123.45
				}
				""";
		
		assertThat(json.parse(request)).isEqualTo(new CashCard(99L, 123.45));
		
		assertThat(json.parseObject(request).id()).isEqualTo(99);
		
		assertThat(json.parseObject(request).amount()).isEqualTo(123.45);
	}
}
