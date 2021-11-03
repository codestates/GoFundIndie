--Test Dummy Data
--User
INSERT INTO `user` (admin_role,banned,email,password,nickname) VALUES (true, false, "admin1@admin.com", "1234", "관리자 1호");
INSERT INTO `user` (admin_role,banned,email,password,nickname) VALUES (true, false, "admin2@admin.com", "1234", "관리자 2호");

INSERT INTO `user` (email,password,nickname,total_donation) VALUES ("test1@gofundindie.com", "1111", "김하나", 100);
INSERT INTO `user` (email,password,nickname,total_donation) VALUES ("test2@gofundindie.com", "2222", "이둘", 2000);
INSERT INTO `user` (email,password,nickname,total_donation) VALUES ("test3@gofundindie.com", "3333", "박셋", 300);
INSERT INTO `user` (email,password,nickname,total_donation) VALUES ("test4@gofundindie.com", "4444", "최넷", 0);
INSERT INTO `user` (email,password,nickname,total_donation) VALUES ("test5@gofundindie.com", "5555", "한다섯", 0);
INSERT INTO `user` (email,password,nickname,total_donation) VALUES ("askdjaskjd@gofundindie.com", "6666", "김여섯", 0);
INSERT INTO `user` (email,password,nickname,total_donation) VALUES ("qwerasdf4567@gofundindie.com", "7777", "조일곱", 17000);
INSERT INTO `user` (email,password,nickname,total_donation) VALUES ("mansoor@gofundindie.com", "8888", "만수르", 50000000);

INSERT INTO `user` (email,password,nickname) VALUES ("mansoor@gofundindie.com", "1234", "스포왕");

INSERT INTO `user` (email,password,nickname,ad_agree) VALUES ("mansoor@gofundindie.com", "1234", "광고 거부맨", false);
INSERT INTO `user` (email,password,nickname,ad_agree) VALUES ("mansoor@gofundindie.com", "1234", "광고 거부걸", false);

--Board
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_at, info_time, info_limit, info_story, info_subtitle) VALUES (1, 3, false,"테스트용 보드 입니다","제작사","배급사",null,null,"한국",now(),120,1,"테스트를 하기위한 더미데이터",true);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_at, info_time, info_limit, info_story, info_subtitle) VALUES (2, 4, false,"나는 전설이였다","codestates","codestates","https://mblogthumb-phinf.pstatic.net/MjAxOTEyMTVfMjc4/MDAxNTc2NDE0MTAwNjg1.cp_9N4gi8GOe7idQjx6pC1LUhK9EqpIs9uArKqZq6iUg.1vF6bTjG3vJW4mb_WagZ5gh0gfwjoo2bznBTEs-tyXkg.JPEG.nilsine11202/github.jpg?type=w800","https://www.youtube.com/","미국",now(),117,1,"<p>전설이였지만 지금은 전설이 아닌 그녀석</p>",true);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_at, info_time, info_limit, info_story, info_subtitle) VALUES (3, 5, false,"CORS 가 밉다","Soul of Asia",null,"https://upload.wikimedia.org/wikipedia/commons/thumb/e/e7/Empty_Star.svg/1200px-Empty_Star.svg.png","https://www.naver.com/","한국",now(),80,1,"<p>CORS가 밉다</p><p></p><p>CORS가 너무 밉다</p><p>CORS가 CORS가 CORS가</p>",false);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_at, info_time, info_limit, info_story, info_subtitle) VALUES (4, 6, false,"이터널스","마블","디즈니","https://upload.wikimedia.org/wikipedia/commons/thumb/6/63/Star%2A.svg/600px-Star%2A.svg.png",null,"미국",now(),120,1,"<p>마블 스튜디오의 <이터널스>는 수 천년에 걸쳐 그 모습을 드러내지 않고 살아온 불멸의 히어로들이</p>",true);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_at, info_time, info_limit, info_story, info_subtitle) VALUES (5, 7, false,"TEST BOARD","producer","distributor",null,null,"태국",now(),120,1,"<p>DUMMY DATA</p>",true);

--Genre
INSERT INTO `genre` (name) VALUES ("드라마");
INSERT INTO `genre` (name) VALUES ("로멘스");
INSERT INTO `genre` (name) VALUES ("판타지");
INSERT INTO `genre` (name) VALUES ("서부");
INSERT INTO `genre` (name) VALUES ("공포");
INSERT INTO `genre` (name) VALUES ("스릴러");
INSERT INTO `genre` (name) VALUES ("코미디");
INSERT INTO `genre` (name) VALUES ("가족");
INSERT INTO `genre` (name) VALUES ("애니메이션");
INSERT INTO `genre` (name) VALUES ("범죄");
INSERT INTO `genre` (name) VALUES ("액션");
INSERT INTO `genre` (name) VALUES ("다큐멘터리");
INSERT INTO `genre` (name) VALUES ("SF");

--BoardGenre
INSERT INTO `board_genre` (board_id, genre_id) VALUES (1,1), (1,2), (1,3);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (2,2);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (3,3);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (4,4);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (4,5);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (4,6);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (5,1);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (5,7);

--BoardLike
INSERT INTO `board_like` (user_id, board_id) VALUES (3,1);
INSERT INTO `board_like` (user_id, board_id) VALUES (4,2);
INSERT INTO `board_like` (user_id, board_id) VALUES (5,3);
INSERT INTO `board_like` (user_id, board_id) VALUES (6,4);
INSERT INTO `board_like` (user_id, board_id) VALUES (7,1);
INSERT INTO `board_like` (user_id, board_id) VALUES (7,2);
INSERT INTO `board_like` (user_id, board_id) VALUES (7,3);
INSERT INTO `board_like` (user_id, board_id) VALUES (7,4);

--BoardReport
INSERT INTO `board_report` (user_id, board_id, body) VALUES (1,1,"test test test");
INSERT INTO `board_report` (user_id, board_id, body) VALUES (2,1,"test test test");
INSERT INTO `board_report` (user_id, board_id, body) VALUES (4,4,"이터널스는 독립영화가 아닌거같은데요?");
INSERT INTO `board_report` (user_id, board_id, body) VALUES (5,4,"이터널스는 독립영화가 아닌거같은데요?");
INSERT INTO `board_report` (user_id, board_id, body) VALUES (6,3,"노잼");

--Casting
INSERT INTO `casting` (name, `position`, image, board_id) VALUES ("김찬", 1, null, 1);
INSERT INTO `casting` (name, `position`, image, board_id) VALUES ("김욱", 2, null, 1);
INSERT INTO `casting` (name, `position`, image, board_id) VALUES ("조현", 3, null, 1);
INSERT INTO `casting` (name, `position`, image, board_id) VALUES ("조기", 1, null, 2);
INSERT INTO `casting` (name, `position`, image, board_id) VALUES ("이준", 2, null, 2);
INSERT INTO `casting` (name, `position`, image, board_id) VALUES ("이희", 3, null, 2);

INSERT INTO `casting` (name, `position`, image, board_id) VALUES ("클로이 자오", 1, null, 4);
INSERT INTO `casting` (name, `position`, image, board_id) VALUES ("젬마 찬", 2, null, 4);
INSERT INTO `casting` (name, `position`, image, board_id) VALUES ("리처드 매든", 2, null, 4);
INSERT INTO `casting` (name, `position`, image, board_id) VALUES ("쿠마일 난지아니", 2, null, 4);
INSERT INTO `casting` (name, `position`, image, board_id) VALUES ("리아 맥휴", 2, null, 4);
INSERT INTO `casting` (name, `position`, image, board_id) VALUES ("로렌 리들로프", 2, null, 4);
INSERT INTO `casting` (name, `position`, image, board_id) VALUES ("마동석", 2, "https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/201608/04/htm_2016080484837486184.jpg", 4);
INSERT INTO `casting` (name, `position`, image, board_id) VALUES ("셀마 헤이엑", 3, null, 4);
INSERT INTO `casting` (name, `position`, image, board_id) VALUES ("안젤리나 졸리", 3, null, 4);
INSERT INTO `casting` (name, `position`, image, board_id) VALUES ("브라이언 타이리 헨리", 3, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/5a/Brian_Tyree_Henry_by_Gage_Skidmore.jpg/500px-Brian_Tyree_Henry_by_Gage_Skidmore.jpg", 4);

--Comment
insert into `comment` (rating, user_id, board_id, body, spoiler) VALUES (5, 11, 2, "할아버지가 범인임 할아버지가 범인임", true);
insert into `comment` (rating, user_id, board_id, body, spoiler) VALUES (5, 11, 3, "할아버지가 범인임 할아버지가 범인임", true);
insert into `comment` (rating, user_id, board_id, body, spoiler) VALUES (5, 11, 4, "할아버지가 범인임 할아버지가 범인임", true);

insert into `comment` (rating, user_id, board_id, donation, body) VALUES (5, 3, 4, 0, "5점 드립니다");
insert into `comment` (rating, user_id, board_id, donation, body) VALUES (4, 4, 4, 0, "4점 드립니다");
insert into `comment` (rating, user_id, board_id, donation, body) VALUES (3, 5, 4, 0, "3점 드립니다");
insert into `comment` (rating, user_id, board_id, donation, body) VALUES (2, 6, 4, 0, "2점 드립니다");
insert into `comment` (rating, user_id, board_id, donation, body) VALUES (1, 7, 4, 0, "1점 드립니다");
insert into `comment` (rating, user_id, board_id, donation, body) VALUES (0, 8, 4, 0, "0점 드립니다");
insert into `comment` (rating, user_id, board_id, donation, body) VALUES (1, 9, 4, 0, "1점 드립니다");
insert into `comment` (rating, user_id, board_id, donation, body) VALUES (2, 10, 4, 0, "2점 드립니다");
insert into `comment` (rating, user_id, board_id, donation, body) VALUES (5, 3, 2, 100, "영화 재밌어서 돈 드립니다");
insert into `comment` (rating, user_id, board_id, donation, body) VALUES (5, 4, 2, 2000, "좋은 영화 많이 만들어 주세요");

insert into `comment` (rating, user_id, board_id) values (1,3,3);
insert into `comment` (rating, user_id, board_id) values (1,4,3);
insert into `comment` (rating, user_id, board_id) values (1,5,3);
insert into `comment` (rating, user_id, board_id) values (1,6,5);
insert into `comment` (rating, user_id, board_id) values (1,7,5);
insert into `comment` (rating, user_id, board_id) values (1,8,5);

insert into `comment` (rating, user_id, board_id) values (1,1,1), (2,2,1), (3,3,1), (4,4,1);

--CommentRating
insert into `comment_rating` (`like`, `dislike`, user_id, comment_id) values (true, false, 3, 1);
insert into `comment_rating` (`like`, `dislike`, user_id, comment_id) values (true, false, 4, 2);
insert into `comment_rating` (`like`, `dislike`, user_id, comment_id) values (true, false, 5, 3);
insert into `comment_rating` (`like`, `dislike`, user_id, comment_id) values (true, false, 6, 4);
insert into `comment_rating` (`like`, `dislike`, user_id, comment_id) values (true, false, 7, 5);
insert into `comment_rating` (`like`, `dislike`, user_id, comment_id) values (true, false, 8, 6);

--CommentReport
insert into `comment_report` (user_id, comment_id, body) values (3, 4, "test");
insert into `comment_report` (user_id, comment_id, body) values (4, 4, "test2");
insert into `comment_report` (user_id, comment_id, body) values (5, 5, "test3");

--Still
insert into `still` (image, board_id) values ("https://newsimg.hankookilbo.com/cms/articlerelease/2021/05/18/e1eb38d8-6e13-4297-abef-dee308831b81.jpg", 4);
insert into `still` (image, board_id) values ("https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/201608/04/htm_2016080484837486184.jpg",4);
insert into `still` (image, board_id) values ("test1", 1), ("test2", 1), ("test3", 1);