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
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_time, info_limit, info_story, info_subtitle) VALUES (1, 3, false,"테스트용 보드 입니다","제작사","배급사",null,null,"한국","2021",120,1,"테스트를 하기위한 더미데이터",true);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_time, info_limit, info_story, info_subtitle) VALUES (2, 4, false,"나는 전설이였다","codestates","codestates","https://mblogthumb-phinf.pstatic.net/MjAxOTEyMTVfMjc4/MDAxNTc2NDE0MTAwNjg1.cp_9N4gi8GOe7idQjx6pC1LUhK9EqpIs9uArKqZq6iUg.1vF6bTjG3vJW4mb_WagZ5gh0gfwjoo2bznBTEs-tyXkg.JPEG.nilsine11202/github.jpg?type=w800","https://www.youtube.com/","미국","2021",117,1,"<p>전설이였지만 지금은 전설이 아닌 그녀석</p>",true);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_time, info_limit, info_story, info_subtitle) VALUES (3, 5, false,"CORS 가 밉다","Soul of Asia",null,"https://upload.wikimedia.org/wikipedia/commons/thumb/e/e7/Empty_Star.svg/1200px-Empty_Star.svg.png","https://www.naver.com/","한국","2021",80,1,"<p>CORS가 밉다</p><p></p><p>CORS가 너무 밉다</p><p>CORS가 CORS가 CORS가</p>",false);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_time, info_limit, info_story, info_subtitle) VALUES (4, 6, false,"이터널스","마블","디즈니","https://upload.wikimedia.org/wikipedia/commons/thumb/6/63/Star%2A.svg/600px-Star%2A.svg.png",null,"미국","2021",120,1,"<p>마블 스튜디오의 <이터널스>는 수 천년에 걸쳐 그 모습을 드러내지 않고 살아온 불멸의 히어로들이</p>",true);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_time, info_limit, info_story, info_subtitle) VALUES (5, 7, false,"TEST BOARD","producer","distributor",null,null,"태국","2021",120,1,"<p>DUMMY DATA</p>",true);

--Genre
INSERT INTO `genre` (name) VALUES ("드라마");
INSERT INTO `genre` (name) VALUES ("공포");
INSERT INTO `genre` (name) VALUES ("로멘스");
INSERT INTO `genre` (name) VALUES ("판타지");
INSERT INTO `genre` (name) VALUES ("스릴러");
INSERT INTO `genre` (name) VALUES ("다큐멘터리");
INSERT INTO `genre` (name) VALUES ("가족");
INSERT INTO `genre` (name) VALUES ("범죄");
INSERT INTO `genre` (name) VALUES ("코미디");
INSERT INTO `genre` (name) VALUES ("애니메이션");
INSERT INTO `genre` (name) VALUES ("액션");
INSERT INTO `genre` (name) VALUES ("SF");
--Genre END

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
insert into `comment_rating` (user_id, comment_id) values (3, 1);
insert into `comment_rating` (user_id, comment_id) values (4, 2);
insert into `comment_rating` (user_id, comment_id) values (5, 3);
insert into `comment_rating` (user_id, comment_id) values (6, 4);
insert into `comment_rating` (user_id, comment_id) values (7, 5);
insert into `comment_rating` (user_id, comment_id) values (8, 6);

--CommentReport
insert into `comment_report` (user_id, comment_id, body) values (3, 4, "test");
insert into `comment_report` (user_id, comment_id, body) values (4, 4, "test2");
insert into `comment_report` (user_id, comment_id, body) values (5, 5, "test3");

--Still
insert into `still` (image, board_id) values ("https://newsimg.hankookilbo.com/cms/articlerelease/2021/05/18/e1eb38d8-6e13-4297-abef-dee308831b81.jpg", 4);
insert into `still` (image, board_id) values ("https://pds.joongang.co.kr/news/component/htmlphoto_mmdata/201608/04/htm_2016080484837486184.jpg",4);
insert into `still` (image, board_id) values ("test1", 1), ("test2", 1), ("test3", 1);

--Board Dummy v1
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(11,1,true,"범죄도시","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","한국","2020","11/11",20,1,"더미 데이터 입니다",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (11,3);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(12,2,false,"2박 3일","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","한국","2020","11/11",30,2,"더미 데이터 입니다",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (12,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(13,3,false,"기념비적 기념품","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","한국","2020","11/11",40,3,"더미 데이터 입니다",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (13,2);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(14,4,false,"4학년 보경이","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","한국","2020","11/11",50,4,"더미 데이터 입니다",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (14,3);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(15,5,false,"열다섯","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","미국","2020","11/11",60,1,"더미 데이터 입니다",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (15,4);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(16,6,false,"내 이름 송병준","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","태국","2020","11/11",70,2,"더미 데이터 입니다",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (16,5);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(17,7,false,"관계없는 우주","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","중국","2020","11/11",20,3,"더미 데이터 입니다",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (17,6);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(18,1,false,"이상","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","일본","2020","11/11",30,4,"더미 데이터 입니다",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (18,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(19,2,false,"백천","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","콜롬비아","2020","11/11",40,1,"더미 데이터 입니다",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (19,2);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(20,3,false,"해미를 찾아서","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","더미데이터","2020","11/11",50,2,"더미 데이터 입니다",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (20,3);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(21,4,true,"산성비","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","넣는게","2020","11/11",60,3,"더미 데이터 입니다",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (21,4);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(22,5,false,"죽부인의 뜨거운 밤","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","일이다","2020","11/11",70,4,"더미 데이터 입니다",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (22,5);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(23,6,false,"그녀의 족발이 그립다","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","영국","2020","11/11",21,1,"더미 데이터 입니다",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (23,6);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(24,7,true,"52Hz","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","영국","2020","11/11",22,2,"더미 데이터 입니다",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (24,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(25,1,false,"약속","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","한국","2020","11/11",34,3,"더미 데이터 입니다",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (25,2);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(26,2,true,"로티셰","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","한국","2020","11/11",120,4,"더미 데이터 입니다",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (26,3);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(27,3,true,"메모리 숍","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","한국","2020","11/11",100,1,"더미 데이터 입니다",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (27,4);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(28,4,false,"보조출연자","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","한국","2020","11/11",110,2,"더미 데이터 입니다",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (28,4);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(29,5,true,"별의 소녀","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","한국","2020","11/11",113,3,"더미 데이터 입니다",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (29,5);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(30,6,true,"인형의 집","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","한국","2020","11/11",118,4,"더미 데이터 입니다",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (30,5);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(31,7,false,"이반의 무개념 나들이","Dummy","Data","Test","https://pedia.watcha.com/ko-KR","크로아티아","2020","11/11",200,1,"더미 데이터 입니다",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (31,1);

-- 준희님 더미데이터
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES (32, 7, true,"Alternative Math","producer","distributor","https://m.media-amazon.com/images/M/MV5BZjFhN2FhMTQtNTA2OS00MjUxLWIwN2UtOGY2ZWQ4NWRmOWE4XkEyXkFqcGdeQXVyMjI3MTE4MjU@._V1_.jpg", "https://www.youtube.com/watch?v=Zh3Yz3PiXZw&ab_channel=Ideaman","미국","2021","11/03",120,1,"진실이 집단적으로 왜곡되는 현실을 기발하게 풍자한 단편",true);

insert into `comment` (rating, user_id, board_id, donation, body) VALUES (5, 3, 32, 0, "5점 드립니다");
insert into `comment` (rating, user_id, board_id, donation, body) VALUES (4, 4, 32, 0, "4점 드립니다");
insert into `comment` (rating, user_id, board_id, donation, body) VALUES (3, 5, 32, 0, "3점 드립니다");