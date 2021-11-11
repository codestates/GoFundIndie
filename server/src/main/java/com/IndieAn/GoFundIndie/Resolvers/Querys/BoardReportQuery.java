package com.IndieAn.GoFundIndie.Resolvers.Querys;

import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.BoardReportRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.BoardReport.WrappingBoardReportGraphQLDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.BoardReport.WrappingBoardReportsGraphqlDTO;
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
public class BoardReportQuery {
    private final BoardReportRepository boardReportRepository;

    private final GqlUserValidService gqlUserValidService;

    public WrappingBoardReportGraphQLDTO FindBoardReport(Long id, DataFetchingEnvironment env) {
        try {
            if(id == null) return WrappingBoardReportGraphQLDTO.bad(4009);

            int code = gqlUserValidService.envValidCheck(env);

            if(code == 0) {
                try {
                    User user = gqlUserValidService.findUser(env);
                    if(user == null) return WrappingBoardReportGraphQLDTO.bad(4400);
                    else if(!user.isAdminRole())
                        return WrappingBoardReportGraphQLDTO.bad(4300);
                } catch (NullPointerException e) {
                    return WrappingBoardReportGraphQLDTO.bad(4400);
                }

                return WrappingBoardReportGraphQLDTO.builder()
                        .code(2000)
                        .data(boardReportRepository.FindReport(id))
                        .build();
            } else {
                return WrappingBoardReportGraphQLDTO.bad(code);
            }
        } catch (NullPointerException e) {
            return WrappingBoardReportGraphQLDTO.bad(4000);
        }
    }

    public WrappingBoardReportsGraphqlDTO FindBoardReports(DataFetchingEnvironment env) {
        try {
            int code = gqlUserValidService.envValidCheck(env);

            if(code == 0) {
                try {
                    User user = gqlUserValidService.findUser(env);
                    if(user == null) WrappingBoardReportGraphQLDTO.bad(4400);
                    else if(!user.isAdminRole())
                        return WrappingBoardReportsGraphqlDTO.bad(4300);
                } catch (NullPointerException e) {
                    return WrappingBoardReportsGraphqlDTO.bad(4400);
                }

                return WrappingBoardReportsGraphqlDTO.builder()
                        .code(2000)
                        .data(boardReportRepository.FindReports())
                        .build();
            } else {
                return WrappingBoardReportsGraphqlDTO.bad(code);
            }
        } catch (NullPointerException e) {
            return WrappingBoardReportsGraphqlDTO.bad(4000);
        }
    }
}
