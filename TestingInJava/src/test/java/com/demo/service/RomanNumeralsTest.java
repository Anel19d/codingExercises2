package com.demo.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
//TEST APLICANDO TDD
class RomanNumeralsTest {

    @Test
    void when_there_are_symbols_that_are_subtracted() {
        assertEquals("IV", RomanNumerals.arabicToRoman(4));
        assertEquals("IX", RomanNumerals.arabicToRoman(9));
        assertEquals("XIV", RomanNumerals.arabicToRoman(14));
        assertEquals("XIX", RomanNumerals.arabicToRoman(19));
        assertEquals("XXIV", RomanNumerals.arabicToRoman(24));
        assertEquals("XL", RomanNumerals.arabicToRoman(40));
    }
}