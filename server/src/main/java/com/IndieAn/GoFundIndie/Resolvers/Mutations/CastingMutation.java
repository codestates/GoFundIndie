package com.IndieAn.GoFundIndie.Resolvers.Mutations;

import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import com.IndieAn.GoFundIndie.Repository.CastingRepository;
import com.IndieAn.GoFundIndie.Repository.UserRepository;
import com.IndieAn.GoFundIndie.Service.UserService;
import graphql.kickstart.servlet.context.GraphQLServletContext;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CastingMutation {
    private final BoardRepository boardRepository;
    private final CastingRepository castingRepository;
    private final UserRepository userRepository;

    private final UserService userService;

    String accessToken = null;
    Map<String, Object> checkToken = null;

    GraphQLServletContext context = null;
    HttpServletRequest request = null;

    // 캐스팅 임시
    public void CreateTempCasting(long id, DataFetchingEnvironment env) {
    }

    // 캐스팅 등록
    public void CompleteCasting(long id, DataFetchingEnvironment env) {
    }

    // 캐스팅 수정
    public void PutCasting(long id, DataFetchingEnvironment env) {
    }

    // 캐스팅 삭제
    public void DeleteCasting(long id, DataFetchingEnvironment env) {
    }
}