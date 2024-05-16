package com.net.anthill.integrationTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.net.anthill.AnthillApplication;
import com.net.anthill.dto.TicketDto;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testng.Assert;

import static com.net.anthill.integrationTest.constants.IntegrationTestsConstants.API_PREFIX;
import static com.net.anthill.integrationTest.constants.IntegrationTestsConstants.API_VERSION;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@SpringBootTest(classes = AnthillApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TicketIntegrationTest {
    @LocalServerPort
    private int port;

    TestRestTemplate restTemplate = new TestRestTemplate();

    ObjectMapper objectMapper = new ObjectMapper();


    HttpHeaders headers = new HttpHeaders();


    @Test
    public void testPostTicket() throws JSONException {
        String body = "{\n" +
                "    \"name\": \"Any status or severity possible at the6 ticket creation\",\n" +
                "    \"description\": \"There is a bug with POST to create ticket is possible to create TICKET in any STATUS or SEVERITY\",\n" +
                "    \"severity\": \"LOW\"\n" +
                "}";
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = getUserRestCall().exchange(
                createURLWithPort(API_PREFIX + API_VERSION + "tickets/"),
                HttpMethod.POST, entity, String.class);

        // String expected = "{\"id\":\"Course1\",\"name\":\"Spring\",\"description\":\"10 Steps\"}";
        System.out.println("response: " + response.getBody());
        //JSONAssert.assertEquals(expected, response.getBody(), false);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }


    @Test
    public void testGetTicketsPaginated() throws JSONException, JsonProcessingException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = getAdminUserRestCall().exchange(//add security
                createURLWithPort(API_PREFIX + API_VERSION + "tickets/?pageSize=10&page=0&sortDirection=desc"),
                HttpMethod.GET, entity, String.class);

    //    String expected = "{\"id\":\"Course1\",\"name\":\"Spring\",\"description\":\"10 Steps\"}";
        System.out.println("response: " + response.getBody());
        objectMapper.configure(DeserializationFeature. FAIL_ON_UNKNOWN_PROPERTIES, false);
        TicketDto data = objectMapper.readValue(response.getBody(), TicketDto.class);
        //JSONAssert.assertEquals(expected, response.getBody(), false);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testGetTicketsUnPaginated() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = getUserRestCall().exchange(//add security
                createURLWithPort(API_PREFIX + API_VERSION + "tickets/"),
                HttpMethod.GET, entity, String.class);

       // String expected = "{\"id\":\"Course1\",\"name\":\"Spring\",\"description\":\"10 Steps\"}";
        System.out.println("response: " + response.getBody());
        //JSONAssert.assertEquals(expected, response.getBody(), false);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void testGetTicketById() throws JSONException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = getUserRestCall().exchange(//add security
                createURLWithPort(API_PREFIX + API_VERSION + "tickets/1"),
                HttpMethod.GET, entity, String.class);

        // String expected = "{\"id\":\"Course1\",\"name\":\"Spring\",\"description\":\"10 Steps\"}";
        System.out.println("response: " + response.getBody());
        //JSONAssert.assertEquals(expected, response.getBody(), false);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }



    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }


    private TestRestTemplate getAdminUserRestCall(){
        return restTemplate.withBasicAuth("admin", "password");
    }

    private TestRestTemplate getUserRestCall(){
        return restTemplate.withBasicAuth("user", "password");
    }
}
