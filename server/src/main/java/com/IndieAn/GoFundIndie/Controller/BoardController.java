package com.IndieAn.GoFundIndie.Controller;

<<<<<<< HEAD
<<<<<<< HEAD
import com.IndieAn.GoFundIndie.Resolvers.User.UserGraphQLDTO;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
=======
>>>>>>> 4b52c78 (rollback)
=======
import com.IndieAn.GoFundIndie.Domain.DTO.UserDTO;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
>>>>>>> d56cda2 (Temp save)
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
<<<<<<< HEAD
import javax.persistence.EntityManager;
=======
>>>>>>> 4b52c78 (rollback)
=======
import javax.persistence.EntityManager;
>>>>>>> d56cda2 (Temp save)
import java.util.HashMap;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {
<<<<<<< HEAD
<<<<<<< HEAD
    private final EntityManager em;

=======
>>>>>>> 4b52c78 (rollback)
=======
    private final EntityManager em;

>>>>>>> d56cda2 (Temp save)
    private final HashMap<String, Object> body = new HashMap<>();

    @GetMapping(value = "/")
    public ResponseEntity<?> Test () {
<<<<<<< HEAD
<<<<<<< HEAD
        User user = em.find(User.class, 1L);
        String dt = UserGraphQLDTO.from(user).getNickname();
        body.clear();
        body.put("message", dt);
=======
        body.clear();
        body.put("message", "hello");
>>>>>>> 4b52c78 (rollback)
=======
        User user = em.find(User.class, 1L);
        String dt = UserDTO.from(user).getNickname();
        body.clear();
        body.put("message", dt);
>>>>>>> d56cda2 (Temp save)
        return ResponseEntity.status(200).body(body);
    }
}
