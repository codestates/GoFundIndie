package com.IndieAn.GoFundIndie.Resolvers.Mutations;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.Casting;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import com.IndieAn.GoFundIndie.Repository.CastingRepository;
import com.IndieAn.GoFundIndie.Repository.UserRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.CreateCastingCompleteDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.CreateTempCastingDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.PutCastingDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.Casting.WrappingCreateTempCastingDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.OnlyCodeDTO;
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

    // TODO 보드가 승인나면 관리자만 할수 있게

    // 캐스팅 임시
    public WrappingCreateTempCastingDTO CreateTempCasting(long id, DataFetchingEnvironment env) {
        try {
            context = env.getContext();
            request = context.getHttpServletRequest();
            accessToken = request.getHeader("accesstoken");

            // No token in the Header : 4000
            if(context == null || request == null || accessToken == null)
                return WrappingCreateTempCastingDTO.builder().code(4000).build();

            checkToken = userService.CheckToken(accessToken);

            if(checkToken.get("email") == null){
                // Token Invalid
                return WrappingCreateTempCastingDTO.builder()
                        .code(Integer.parseInt(checkToken.get("code").toString())).build();
            } else {
                Board board = boardRepository.findBoardId(id);
                // Can not find board_id : 4401
                if(board == null)
                    return WrappingCreateTempCastingDTO.builder().code(4401).build();

                User user = userService.FindUserUseEmail(checkToken.get("email").toString());
                // No authorization : 4301
                if(!user.isAdminRole() && board.getUserId().getId() != user.getId())
                    return WrappingCreateTempCastingDTO.builder().code(4301).build();

                return WrappingCreateTempCastingDTO.builder().code(2000)
                        .data(CreateTempCastingDTO.builder()
                                .id(castingRepository.RegisterTempCasting(board)).build())
                        .build();
            }

            // Test Code
//            return WrappingCreateTempCastingDTO.builder().code(2000)
//                    .data(CreateTempCastingDTO.builder().id(
//                            castingRepository.RegisterTempCasting(
//                                    boardRepository.findBoardId(id)
//                            )
//                    ).build())
//                    .build();

        } catch (NullPointerException e) {
            // No token : 4000
            return WrappingCreateTempCastingDTO.builder().code(4000).build();
        }
    }

    // 캐스팅 등록
    public WrappingCreateTempCastingDTO CompleteCasting(CreateCastingCompleteDTO dto, DataFetchingEnvironment env) {
        try {
            context = env.getContext();
            request = context.getHttpServletRequest();
            accessToken = request.getHeader("accesstoken");

            // No token in the Header : 4000
            if(context == null || request == null || accessToken == null)
                return WrappingCreateTempCastingDTO.builder().code(4000).build();

            checkToken = userService.CheckToken(accessToken);

            if(checkToken.get("email") == null){
                // Token Invalid
                return WrappingCreateTempCastingDTO.builder()
                        .code(Integer.parseInt(checkToken.get("code").toString())).build();
            } else {
                Casting casting = castingRepository.findCastingById(dto.getCastingId());
                if(casting == null)
                    return WrappingCreateTempCastingDTO.builder().code(4403).build();

                return WrappingCreateTempCastingDTO.builder().code(2000)
                        .data(CreateTempCastingDTO.builder().id(
                                castingRepository.CompleteCasting(casting, dto)
                        ).build())
                        .build();
            }

            // Test Code
//            return WrappingCreateTempCastingDTO.builder().code(2000)
//                    .data(CreateTempCastingDTO.builder()
//                            .id(
//                                    castingRepository.CompleteCasting(castingRepository.findCastingById(dto.getCastingId()), dto)
//                            ).build())
//                    .build();

        } catch (NullPointerException e) {
            return WrappingCreateTempCastingDTO.builder().code(4000).build();
        }
    }

    // 캐스팅 수정
    public WrappingCreateTempCastingDTO PutCasting(PutCastingDTO dto, DataFetchingEnvironment env) {
        try {
            context = env.getContext();
            request = context.getHttpServletRequest();
            accessToken = request.getHeader("accesstoken");

            // No token in the Header : 4000
            if(context == null || request == null || accessToken == null)
                return WrappingCreateTempCastingDTO.builder().code(4000).build();

            checkToken = userService.CheckToken(accessToken);

            if(checkToken.get("email") == null){
                // Token Invalid
                return WrappingCreateTempCastingDTO.builder()
                        .code(Integer.parseInt(checkToken.get("code").toString())).build();
            } else {
                Casting casting = castingRepository.findCastingById(dto.getCastingId());
                if(casting == null)
                    return WrappingCreateTempCastingDTO.builder().code(4403).build();

                return WrappingCreateTempCastingDTO.builder().code(2000)
                        .data(CreateTempCastingDTO.builder().id(
                                castingRepository.PutCasting(casting, dto)
                        ).build())
                        .build();
            }

            // Test Code
//            return WrappingCreateTempCastingDTO.builder().code(2000)
//                    .data(CreateTempCastingDTO.builder()
//                            .id(
//                                    castingRepository.PutCasting(castingRepository.findCastingById(dto.getCastingId()), dto)
//                            ).build())
//                    .build();

        } catch (NullPointerException e) {
            return WrappingCreateTempCastingDTO.builder().code(4000).build();
        }
    }

    // 캐스팅 삭제
    public OnlyCodeDTO DeleteCasting(long id, DataFetchingEnvironment env) {
        try {
            context = env.getContext();
            request = context.getHttpServletRequest();
            accessToken = request.getHeader("accesstoken");

            // No token in the Header : 4000
            if(context == null || request == null || accessToken == null)
                return OnlyCodeDTO.builder().code(4000).build();

            checkToken = userService.CheckToken(accessToken);

            if(checkToken.get("email") == null){
                // Token Invalid
                return OnlyCodeDTO.builder()
                        .code(Integer.parseInt(checkToken.get("code").toString())).build();
            } else {
                Casting casting = castingRepository.findCastingById(id);
                if(casting == null)
                    return OnlyCodeDTO.builder().code(4403).build();

                castingRepository.RemoveCasting(casting);
                return OnlyCodeDTO.builder().code(2000).build();
            }

            // Test Code
//            castingRepository.RemoveCasting(castingRepository.findCastingById(id));
//            return OnlyCodeDTO.builder().code(2000).build();

        } catch (NullPointerException e) {
            return OnlyCodeDTO.builder().code(4000).build();
        }
    }
}