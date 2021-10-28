package com.IndieAn.GoFundIndie.Controller;

import com.IndieAn.GoFundIndie.Resolvers.User.UserGraphQLDTO;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {
    private final EntityManager em;

    private final HashMap<String, Object> body = new HashMap<>();

    @GetMapping(value = "/")
    public ResponseEntity<?> Test () {
        User user = em.find(User.class, 1L);
        String dt = UserGraphQLDTO.from(user).getNickname();
        body.clear();
        body.put("message", dt);
        return ResponseEntity.status(200).body(body);
    }
}
