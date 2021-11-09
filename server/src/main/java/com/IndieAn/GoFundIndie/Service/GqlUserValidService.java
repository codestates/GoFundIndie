package com.IndieAn.GoFundIndie.Service;

import com.IndieAn.GoFundIndie.Domain.Entity.User;
import graphql.kickstart.servlet.context.GraphQLServletContext;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GqlUserValidService {
    private final UserService userService;

    public int envValidCheck(DataFetchingEnvironment env) {
        try {
            GraphQLServletContext context = env.getContext();
            HttpServletRequest request = context.getHttpServletRequest();
            String accessToken = request.getHeader("accesstoken");

            // No token in the Header : 4000
            if(accessToken == null) return 4000;

            Map<String, Object> checkToken = userService.CheckToken(accessToken);

            if(checkToken.get("email") == null)
                return Integer.parseInt(checkToken.get("code").toString());
            else
                return 0;
        } catch (NullPointerException e) {
            return 4000;
        }
    }

    public User findUser(DataFetchingEnvironment env) {
        GraphQLServletContext context = env.getContext();
        HttpServletRequest request = context.getHttpServletRequest();

        return userService.FindUserUseEmail(
                userService.CheckToken(request
                                .getHeader("accesstoken"))
                        .get("email").toString());
    }
}
