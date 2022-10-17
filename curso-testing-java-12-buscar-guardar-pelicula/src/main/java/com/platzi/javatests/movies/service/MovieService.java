package com.platzi.javatests.movies.service;

import com.platzi.javatests.movies.data.MovieRepository;
import com.platzi.javatests.movies.model.Genre;
import com.platzi.javatests.movies.model.Movie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MovieService {

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Collection<Movie> findMoviesByGenre(Genre genre) {

        return movieRepository.findAll().stream()
                .filter(movie -> movie.getGenre() == genre).collect(Collectors.toList());
    }

    public Collection<Movie> findMoviesByLength(int length) {

        return movieRepository.findAll().stream()
                .filter(movie -> movie.getMinutes() <= length).collect(Collectors.toList());
    }

    public Collection<Movie> findMoviesByName(String name) {

        return movieRepository.findAll().stream()
                .filter(movie -> movie.getName().toLowerCase().contains(name))
                .collect(Collectors.toList());
    }

    public Collection<Movie> findMoviesByTemplate(Movie template) {

        List<Predicate<Movie>> filters = new ArrayList<>();

        if (template.getId() != null) {
            Movie movie = movieRepository.findById(template.getId());
            return movie != null ? Collections.singletonList(movie) : new ArrayList<>();
        }
        if (template.getMinutes() < 0) {
            throw new IllegalArgumentException("duration must be greater or equal than zero.");
        }
        if (template.getName() != null) {
            filters.add(movie -> movie.getName().toLowerCase().contains(template.getName().toLowerCase().trim()));
        }
        if (template.getMinutes() != null) {
            filters.add(movie -> movie.getMinutes() <= template.getMinutes());
        }
        if (template.getGenre() != null) {
            filters.add(movie -> movie.getGenre().equals(template.getGenre()));
        }

        return movieRepository.findAll().stream()
                .filter(movie -> filters.stream().allMatch(filter -> filter.test(movie)))
                .collect(Collectors.toList());
    }

}
