package com.IndieAn.GoFundIndie.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final HashMap<String, Object> body = new HashMap<>();

    @GetMapping(value = "/")
    public ResponseEntity<?> Test () {
        body.clear();
        body.put("Server", "Connected");
        return ResponseEntity.status(200).body(body);
    }
}
