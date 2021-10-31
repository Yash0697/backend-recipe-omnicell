package com.omnicell.recipe;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

	@Query("select case when count(r)> 0 then true else false end from Recipe r where r.id IN (:ids)")
	 boolean recipesExists(@Param("ids") List<Integer> ids);                
}
