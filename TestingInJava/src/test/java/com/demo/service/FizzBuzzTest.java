package com.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
//TEST APLICANDO TDD
class FizzBuzzTest {
//    Si el número es divisible por 3, retorna “Fizz”
//    Si el número es divisible por 5, retorna “Buzz”
//    Si el número es divisible por 3 y por 5, retorna “FizzBuzz”


    @Test
    void when_the_number_is_divisible_by_three () {
        assertEquals("Fizz", FizzBuzz.fizzBuzz(9));
        assertEquals("Fizz", FizzBuzz.fizzBuzz(6));
    }

    @Test
    void when_the_number_is_divisible_by_five () {
        assertEquals("Buzz", FizzBuzz.fizzBuzz(5));
        assertEquals("Buzz", FizzBuzz.fizzBuzz(10));
    }

    @Test
    void when_the_number_is_divisible_by_three_and_five () {
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(15));//3 and 5
        assertEquals("FizzBuzz", FizzBuzz.fizzBuzz(30));//3 and 5
    }

    @Test
    void when_the_number_is_not_divisible_by_three_or_five () {
        assertEquals("2", FizzBuzz.fizzBuzz(2));
        assertEquals("16", FizzBuzz.fizzBuzz(16));
    }
}