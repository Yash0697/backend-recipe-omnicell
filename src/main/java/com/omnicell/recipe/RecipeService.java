package com.omnicell.recipe;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class RecipeService {

	@Value("${recipe.api}")
	private String recipeApi;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	RecipeRepository recipeRepository;
	
	public List<Recipe> getAndSaveAllRecipes() throws JsonMappingException, JsonProcessingException, JSONException {
		ResponseEntity<String> response = null;
		List<Integer> ids = new ArrayList<Integer>();
		HttpMethod method = HttpMethod.GET;
		List<Recipe> recipes = new ArrayList<Recipe>();
		ObjectMapper objectMapper = new ObjectMapper();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);
		try {
			response = restTemplate.exchange(recipeApi, method, requestEntity, String.class);
		} catch (HttpClientErrorException e) {
		}
		JSONArray jsonArray = new JSONArray(response.getBody());
		for(int i = 0; i < jsonArray.length(); i++) {
			Recipe recipe = objectMapper.readValue(jsonArray.get(i).toString(), Recipe.class);
			System.out.println(jsonArray.get(i).toString());
			ids.add(recipe.getId());
			recipes.add(recipe);
		}
		if(!recipeRepository.recipesExists(ids))
			recipeRepository.saveAll(recipes);
		return recipeRepository.findAll();
	}
	
	public Recipe getRecipe(int id) {
		return recipeRepository.findById(id).get();
	}
	public String getRecipeImage(int id) {
		return recipeRepository.findById(id).get().getImage();
	}
	
	public void saveRecipe(Recipe recipe) {
		recipeRepository.save(recipe);
	}
	
	
}
