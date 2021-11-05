package com.IndieAn.GoFundIndie.Resolvers.Mutations;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.Genre;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.BoardGenreRepository;
import com.IndieAn.GoFundIndie.Resolvers.DTO.BoardGenre.LinkBoardGenreDTO;
import com.IndieAn.GoFundIndie.Resolvers.DTO.BoardGenre.WrappingLinkBoardGenreDTO;
import com.IndieAn.GoFundIndie.Service.BoardService;
import com.IndieAn.GoFundIndie.Service.GenreService;
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
public class BoardGenreMutation {
    private final BoardGenreRepository boardGenreRepository;

    private final UserService userService;
    private final BoardService boardService;
    private final GenreService genreService;

    public WrappingLinkBoardGenreDTO LinkBoardGenre(Long boardId, Long genreId, boolean CreateOrDisLink, DataFetchingEnvironment env) {
        if(boardId == null || genreId == null)
            // Path null : 4009
            return WrappingLinkBoardGenreDTO.builder().code(4009).build();

        try{
            GraphQLServletContext context = env.getContext();
            HttpServletRequest request = context.getHttpServletRequest();
            String accessToken = request.getHeader("accesstoken");

            // No accessToken in the Header : 4000
            if(accessToken == null)
                return WrappingLinkBoardGenreDTO.builder().code(4000).build();

            Map<String, Object> checkToken = userService.CheckToken(accessToken);

            if(checkToken.get("email") == null) {
                // Token invalid
                return WrappingLinkBoardGenreDTO.builder()
                        .code(Integer.parseInt(checkToken.get("code").toString())).build();
            } else {
                User user = userService.FindUserUseEmail(checkToken.get("email").toString());
                // User not found : 4400
                if(user == null) return WrappingLinkBoardGenreDTO.builder().code(4400).build();

                Board board = boardService.FindBoardId(boardId);
                // Board not found : 4401
                if(board == null) return WrappingLinkBoardGenreDTO.builder().code(4401).build();

                // Authorization denied : 4301
                if(!user.isAdminRole() && board.getUserId().getId() != user.getId())
                    return WrappingLinkBoardGenreDTO.builder().code(4301).build();

                Genre genre = genreService.FindGenreId(genreId);
                // Genre not found : 4404
                if(genre == null) return WrappingLinkBoardGenreDTO.builder().code(4404).build();

                if(CreateOrDisLink) boardGenreRepository.CreateLink(board, genre);
                else boardGenreRepository.DisLink(board.getId(), genre.getId());

                return WrappingLinkBoardGenreDTO.builder()
                        .code(2000)
                        .data(LinkBoardGenreDTO.builder()
                                .boardId(boardId).genreId(genreId).build())
                        .build();
            }

            // Test Code
//            Board board = boardService.FindBoardId(boardId);
//            // Board not found : 4401
//            if(board == null) return WrappingLinkBoardGenreDTO.builder().code(4401).build();
//
//            Genre genre = genreService.FindGenreId(genreId);
//            // Genre not found : 4404
//            if(genre == null) return WrappingLinkBoardGenreDTO.builder().code(4404).build();
//
//            if(CreateOrDisLink) boardGenreRepository.CreateLink(board, genre);
//            else boardGenreRepository.DisLink(board.getId(), genre.getId());
//
//            return WrappingLinkBoardGenreDTO.builder()
//                    .code(2000)
//                    .data(LinkBoardGenreDTO.builder()
//                            .boardId(boardId).genreId(genreId).build())
//                    .build();

        } catch (NullPointerException e) {
            return WrappingLinkBoardGenreDTO.builder().code(4000).build();
        }
    }
}
