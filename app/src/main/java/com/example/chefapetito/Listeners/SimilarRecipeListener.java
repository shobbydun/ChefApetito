package com.example.chefapetito.Listeners;

import com.example.chefapetito.Models.SimilarRecipeResponse;

import java.util.List;

public interface SimilarRecipeListener {

    void didFetch(List<SimilarRecipeResponse> response, String message);
        void didError(String message);
}
