package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static java.lang.String.format;

@RestController
public class DemoController {

    private static final int TWO = 2;
    private static final String EMPTY_STRING = "";
    private static final int ONE = 1;
    public static final String ERROR_MSG = "Minimum %d characters are required.";

    @GetMapping("/remove")
    public ResponseEntity<String> removeFirstAndLast(@RequestParam(name = "original") String original) {
        if (original.length() < TWO) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(format(ERROR_MSG, TWO));
        }

        if (original.length() == TWO) {
            return ResponseEntity.ok(EMPTY_STRING);
        }

        String result = original.substring(ONE, original.length() - ONE);
        return ResponseEntity.ok(result);
    }
}

