package phytechExerciseClient.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

import phytechExerciseClient.dto.ItemDto;

public class PhytechExerciseClientAppl {

	private static final String url = "http://localhost:8080/";
	

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		RestTemplate restTemplate = new RestTemplate();
		

		do {
			System.out.println("Please choose your option");
			System.out.println("1. Get all items");
			System.out.println("2. Select item and get the amount");
			System.out.println("3. Buy an item");
			System.out.println("4. Exit");
			switch (br.readLine()) {
			case "1":
				List<ItemDto> items = getAllItems(restTemplate);
				for (ItemDto dto : items) {
					System.out.println(dto.getDescription());
				}
				break;
			case "2":
				List<ItemDto> items2 = getAllItems(restTemplate);
				for (ItemDto dto : items2) {
					System.out.println(dto.getDescription());
				}
				System.out.println("Please enter the item name");
				String itemName = br.readLine();
				ItemDto dto = items2.stream().filter(d -> d.getDescription().equals(itemName)).findFirst()
						.orElseThrow();
				UriComponentsBuilder builder2 = UriComponentsBuilder.fromHttpUrl(url).path("amount").queryParam("itemId", dto.getId());
				RequestEntity<String> requestEntity = new RequestEntity<String>(HttpMethod.GET, builder2.build().toUri());
				ResponseEntity<Integer> responseEntity = restTemplate.exchange(requestEntity, Integer.class);
				System.out.printf("Amount of " + itemName + " in stock is %d", responseEntity.getBody());
				break;
			case "3":
				List<ItemDto> items3 = getAllItems(restTemplate);
				for (ItemDto dto2 : items3) {
					System.out.println(dto2.getDescription());
				}
				System.out.println("Please enter item name, which you want to buy");
				String itemName2 = br.readLine();
				ItemDto dto2 = items3.stream().filter(d -> d.getDescription().equals(itemName2)).findFirst()
						.orElseThrow();
				UriComponentsBuilder builder3 = UriComponentsBuilder.fromHttpUrl(url).path("sell").queryParam("itemId", dto2.getId());
				RequestEntity<String> requestEntity2 = new RequestEntity<String>(HttpMethod.POST, builder3.build().toUri());
				ResponseEntity<ItemDto> responseEntity2 = restTemplate.exchange(requestEntity2, ItemDto.class);
				System.out.printf("You bought item %d for %d", responseEntity2.getBody().getDescription(), responseEntity2.getBody().getPrice());
				break;
			default:
				return;
			}
			

		} while (!br.readLine().equals("4"));

	}

	private static List<ItemDto> getAllItems(RestTemplate restTemplate) {
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).path("items");
		RequestEntity<String> requestEntity = new RequestEntity<String>(HttpMethod.GET,
				builder.build().toUri());
		ResponseEntity<List<ItemDto>> responseEntity = restTemplate.exchange(requestEntity,
				new ParameterizedTypeReference<List<ItemDto>>() {
				});
		return responseEntity.getBody();
		
	}

}
