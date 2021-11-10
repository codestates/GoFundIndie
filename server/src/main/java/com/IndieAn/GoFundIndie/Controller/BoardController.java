package com.IndieAn.GoFundIndie.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final HashMap<String, Object> body = new HashMap<>();

}
