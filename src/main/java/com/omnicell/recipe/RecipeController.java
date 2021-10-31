package com.omnicell.recipe;

import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class RecipeController {

	@Autowired
	RecipeService recipeService;
	
	@GetMapping("/")
	public ResponseEntity<List<Recipe>> getRecipes() {
		List<Recipe> recipes = null;
		try {
		     recipes = recipeService.getAndSaveAllRecipes();
		} catch (JsonProcessingException | JSONException e) {
			e.printStackTrace();
		}
		return new ResponseEntity<List<Recipe>>(recipes, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Recipe> getRecipe(@PathVariable("id") int id) {
		Recipe recipe = recipeService.getRecipe(id);
		return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
	}
	@GetMapping("/{id}/show")
	public ResponseEntity<String> getRecipeImage(@PathVariable("id") int id) {
		String recipeImage = recipeService.getRecipeImage(id);
		return new ResponseEntity<String>(recipeImage, HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
		recipeService.saveRecipe(recipe);
		
		return new ResponseEntity<Recipe>(recipe, HttpStatus.OK);
	}
}
