package com.example.chefapetito.Listeners;

import com.example.chefapetito.Models.RecipeDetailsResponse;

public interface RecipeDetailListener {
    void didFetch(RecipeDetailsResponse response, String message);
    void didError(String message);
}
