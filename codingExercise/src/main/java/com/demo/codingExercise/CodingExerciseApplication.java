package com.demo.codingExercise;

import com.demo.codingExercise.data.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
public class CodingExerciseApplication {

	public static void main(String[] args) throws ParseException {

		SpringApplication.run(CodingExerciseApplication.class, args);

	}
}
