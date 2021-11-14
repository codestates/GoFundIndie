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
import com.IndieAn.GoFundIndie.Resolvers.DTO.GqlResponseCodeDTO;
import com.IndieAn.GoFundIndie.Service.GqlUserValidService;
import com.IndieAn.GoFundIndie.Service.UserService;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CastingMutation {
    private final BoardRepository boardRepository;
    private final CastingRepository castingRepository;
    private final UserRepository userRepository;

    private final UserService userService;
    private final GqlUserValidService gqlUserValidService;

    // TODO 보드가 승인나면 관리자만 할수 있게
    // 캐스팅 임시
    public WrappingCreateTempCastingDTO CreateTempCasting(long id, DataFetchingEnvironment env) {
        try {
            int code = gqlUserValidService.envValidCheck(env);

            if(code != 0) {
                // Token Invalid
                return WrappingCreateTempCastingDTO.builder().code(code).build();
            } else {
                Board board = boardRepository.findBoardId(id);
                // Can not find board_id : 4401
                if(board == null)
                    return WrappingCreateTempCastingDTO.builder().code(4401).build();

                User user = gqlUserValidService.findUser(env);
                if(user == null) return WrappingCreateTempCastingDTO.builder().code(4400).build();
                // No authorization : 4301
                else if(!user.isAdminRole() && board.getUserId().getId() != user.getId())
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
            int code = gqlUserValidService.envValidCheck(env);

            if(code != 0) {
                // Token Invalid
                return WrappingCreateTempCastingDTO.builder().code(code).build();
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
            int code = gqlUserValidService.envValidCheck(env);

            if(code != 0) {
                // Token Invalid
                return WrappingCreateTempCastingDTO.builder().code(code).build();
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
    public GqlResponseCodeDTO DeleteCasting(long id, DataFetchingEnvironment env) {
        try {
            int code = gqlUserValidService.envValidCheck(env);

            if(code != 0) {
                // Token Invalid
                return GqlResponseCodeDTO.builder().code(code).build();
            } else {
                Casting casting = castingRepository.findCastingById(id);
                if(casting == null)
                    return GqlResponseCodeDTO.builder().code(4403).build();

                castingRepository.RemoveCasting(casting);
                return GqlResponseCodeDTO.builder().code(2000).build();
            }

            // Test Code
//            castingRepository.RemoveCasting(castingRepository.findCastingById(id));
//            return OnlyCodeDTO.builder().code(2000).build();

        } catch (NullPointerException e) {
            return GqlResponseCodeDTO.builder().code(4000).build();
        }
    }
}