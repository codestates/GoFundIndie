package com.IndieAn.GoFundIndie.Controller;

<<<<<<< HEAD
import com.IndieAn.GoFundIndie.Resolvers.User.UserGraphQLDTO;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
=======
>>>>>>> 4b52c78 (rollback)
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
import javax.persistence.EntityManager;
=======
>>>>>>> 4b52c78 (rollback)
import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {
<<<<<<< HEAD
    private final EntityManager em;

=======
>>>>>>> 4b52c78 (rollback)
    private final HashMap<String, Object> body = new HashMap<>();

    @GetMapping(value = "/")
    public ResponseEntity<?> Test () {
<<<<<<< HEAD
        User user = em.find(User.class, 1L);
        String dt = UserGraphQLDTO.from(user).getNickname();
        body.clear();
        body.put("message", dt);
=======
        body.clear();
        body.put("message", "hello");
>>>>>>> 4b52c78 (rollback)
        return ResponseEntity.status(200).body(body);
    }
}
