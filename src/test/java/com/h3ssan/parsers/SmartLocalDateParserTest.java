package com.h3ssan.parsers;

import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SmartLocalDateParserTest {

    @Test
    void tryInvalidDates() {
        assertThrows(IllegalArgumentException.class, () -> new SmartLocalDateParser(null).getLocalDate());
        assertThrows(DateTimeException.class, () -> new SmartLocalDateParser("").getLocalDate());
        assertThrows(DateTimeException.class, () -> new SmartLocalDateParser("1998").getLocalDate());
        assertThrows(DateTimeException.class, () -> new SmartLocalDateParser("10-10").getLocalDate());
        assertThrows(DateTimeException.class, () -> new SmartLocalDateParser("1").getLocalDate());
        assertThrows(DateTimeException.class, () -> new SmartLocalDateParser("1990-10/20").getLocalDate());

        assertThrows(DateTimeException.class, () -> new SmartLocalDateParser("1990/20/10").getLocalDate());
        assertThrows(DateTimeException.class, () -> new SmartLocalDateParser("10/20/1990").getLocalDate());
    }

    @Test
    void tryValidDates() {
        assertEquals(LocalDate.of(1990, 10, 20), new SmartLocalDateParser("1990-10-20").getLocalDate());
        assertEquals(LocalDate.of(1990, 10, 20), new SmartLocalDateParser("20-10-1990").getLocalDate());
    }
}