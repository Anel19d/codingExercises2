package com.platzi.javatests.util;

import com.platzi.javatests.movies.data.MovieRepository;
import com.platzi.javatests.movies.model.Movie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ExampleUtil {


    private MovieRepository repository;

        public Collection<Movie> findByTemplate(Movie template) {

            if (template.getId() != null) {
                Movie movie = repository.findById(template.getId());
                return movie != null ? Collections.singletonList(movie) : new ArrayList<>();
            }

            if (template.getMinutes() < 0) {
                throw new IllegalArgumentException("duration must be greater or equal than zero.");
            }

            List<Predicate<Movie>> filters = new ArrayList<>();

            if (template.getName() != null) {
                filters.add(movie -> movie.getName().toLowerCase().contains(template.getName().toLowerCase().trim()));
            }
            if (template.getMinutes() != null) {
                filters.add(movie -> movie.getMinutes() <= template.getMinutes());
            }
            if (template.getGenre() != null) {
                filters.add(movie -> movie.getGenre().equals(template.getGenre()));
            }

            return repository.findAll().stream()
                    .filter(movie -> filters.stream().allMatch(filter -> filter.test(movie)))
                    .collect(Collectors.toList());
        }

}
