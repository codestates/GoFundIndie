package com.IndieAn.GoFundIndie.Resolvers.Mutations;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.BoardReportRepository;
import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.BoardReport.CreateBoardReportDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.GqlResponseCodeDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.GqlResponseCodeIdDTO;
import com.IndieAn.GoFundIndie.Service.GqlUserValidService;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardReportMutation {
    private final BoardReportRepository boardReportRepository;
    private final BoardRepository boardRepository;

    private final GqlUserValidService gqlUserValidService;

    public GqlResponseCodeDTO ReportBoard(CreateBoardReportDTO dto, DataFetchingEnvironment env) {
        try {
            // request body wrong : 4006
            if(dto == null) return GqlResponseCodeDTO.bad(4006);

            int code = gqlUserValidService.envValidCheck(env);

            if(code == 0) {
                User user = gqlUserValidService.findUser(env);
                if(user == null) return GqlResponseCodeDTO.bad(4400);

                Board board = boardRepository.findBoardId(dto.getBoardId());
                if(board == null) return GqlResponseCodeDTO.bad(4401);

                if(boardReportRepository.ReportNew(user, board, dto.getBody())) {
                    return GqlResponseCodeDTO.ok();
                } else {
                    // already reported : 4005
                    return GqlResponseCodeDTO.bad(4005);
                }
            } else {
                // token invalid
                return GqlResponseCodeDTO.bad(code);
            }
        } catch (IllegalArgumentException e) {
            // request body wrong : 4006
            return GqlResponseCodeDTO.bad(4006);
        } catch (NullPointerException e) {
            // token empty : 4000
            return GqlResponseCodeDTO.bad(4000);
        }
    }

    public GqlResponseCodeDTO DeleteReport(Long boardReportId, DataFetchingEnvironment env) {
        try {
            int code = gqlUserValidService.envValidCheck(env);

            if(code == 0) {
                if(boardReportId == null) return GqlResponseCodeDTO.bad(4009);

                User user = gqlUserValidService.findUser(env);
                if(user == null) return GqlResponseCodeDTO.bad(4400);
                else if(!user.isAdminRole()) return GqlResponseCodeDTO.bad(4300);

                if(boardReportRepository.DeleteReport(boardReportId)) {
                    return GqlResponseCodeDTO.ok();
                } else {
                    // can not find report_id : 4406
                    return GqlResponseCodeDTO.bad(4406);
                }
            } else {
                return GqlResponseCodeDTO.bad(code);
            }
        } catch (NullPointerException e) {
            return GqlResponseCodeDTO.bad(4000);
        }
    }
}
