package br.com.julioces;

import static org.junit.Assert.*;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

public class JsonTest {

	@Test
	public void test() {
		String json = "{ \n";
		json += " \"access_token\": \"ya29.Gly4BMzh84th80NzAQg6zKIHhYSi2RQlpuegiUThrPtptq79KonXIqL5qGqz93751CgVByyxlXciZGYW52kn1OK2xAbs-ksQbFnZJfBhsL41_penRni05rS2y5G4PA\", \n";
		json += " \"token_type\": \"Bearer\", \n";
		json += " \"expires_in\": 3582, \n";
		json += " \"id_token\": \"eyJhbGciOiJSUzI1NiIsImtpZCI6IjM2MWMyZGJhYTdiOGYxODFjZGQwOGRjNmZlZTk1YTdkZDBkN2E2ZTYifQ.eyJhenAiOiI3NTA4NTcyNzY4OTgtMW5ybDNidDQwOW9vaG9rbDBkbHNuaTg5cTVydWlnMTIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiI3NTA4NTcyNzY4OTgtMW5ybDNidDQwOW9vaG9rbDBkbHNuaTg5cTVydWlnMTIuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDI1OTU0OTk1OTczNTI5OTE0NjMiLCJhdF9oYXNoIjoickpMQUh1cm9oOEVRQkdJbklTTVRaQSIsImlzcyI6Imh0dHBzOi8vYWNjb3VudHMuZ29vZ2xlLmNvbSIsImlhdCI6MTUwNDEzOTA4NSwiZXhwIjoxNTA0MTQyNjg1LCJuYW1lIjoiSnVsaW8gQ2VzYXIiLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDYuZ29vZ2xldXNlcmNvbnRlbnQuY29tLy1pOG9oZWZBeWRYRS9BQUFBQUFBQUFBSS9BQUFBQUFBQVhiVS9UZXdQcHpmeUw5VS9zOTYtYy9waG90by5qcGciLCJnaXZlbl9uYW1lIjoiSnVsaW8iLCJmYW1pbHlfbmFtZSI6IkNlc2FyIiwibG9jYWxlIjoicHQifQ.AHDxcVcs_t5Y1l5oUoqra0A666CEikAPN6wZti5d16Okf2CqZtUdlScE9KTfnb7EhwYAwwrRnpI14JgZ7s1WZtMkcxjZBi6AeLs2TjsJBYBmCCHnWCUIl-pA4hIWGOYxRnTfyPjU1L_JD0mlMlUyMN9Bj3w4JYltHRasqcg_kmv_2JUTYhOWilKXygbJF_4KVoRTtMDm9WaHIEIqDceD_ihpxEvrbg7XJcU-JPAPLdZWzPVFCuXMq3fbcZB_TJfyRxWmiV8ExCqXoKMfHTgjimSP2sNsqolgF_E1xOBki4aMPiVDwz4_32gVytB88lkbe2abtb0AaPoefzYnFBvphA\" \n";
		json += " }";
		
		try {
			System.out.println(json);
			JsonNode rootNode = new ObjectMapper().readTree(json);
			System.out.println( rootNode.get("access_token").getTextValue() );
			System.out.println( rootNode.get("token_type").getTextValue() );
			System.out.println( rootNode.get("expires_in").getIntValue() );
			System.out.println( rootNode.get("id_token").getTextValue() );
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
