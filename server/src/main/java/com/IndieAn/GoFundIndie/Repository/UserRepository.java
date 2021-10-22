package com.IndieAn.GoFundIndie.Repository;

import com.IndieAn.GoFundIndie.Domain.DTO.UserSignUpDTO;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserRepository {
    private final EntityManager entityManager;

    @Autowired
    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // DB User 테이블에 모든 유저 정보를 리턴한다.
    public List<User> FindUserList() {
        return entityManager.createQuery("SELECT u FROM User as u",  User.class).getResultList();
    }

    // DB User 테이블에 매개변수 userSignUp과 id의 데이터를 사용하여 유저 정보를 저장한다.
    public User CreateUser(UserSignUpDTO userSignUpDTO) {
        User user = new User();
        user.setEmail(userSignUpDTO.getEmail());
        user.setPassword(userSignUpDTO.getPassword());
        user.setNickname(userSignUpDTO.getNickname());
        // 프로필이 null이 아니라면 프로필을 설정해준다.(프로필 링크)
        if(userSignUpDTO.getProfilePic() != null) {
            user.setProfilePicture(userSignUpDTO.getProfilePic());
        }
        entityManager.persist(user);

        entityManager.flush();
        entityManager.close();

        return user;
    }
}
