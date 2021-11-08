import styles from "../styles/mypage.module.scss";
import { GetServerSideProps } from "next";
process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";

export default function Mypage({ userInfo }: any) {
  if (userInfo === null) {
    return <div className={styles.error}>로그인이 필요합니다</div>;
  }
  return (
    <div className={styles.mypage}>
      <div className={styles.header__blank__div} />
      <div className={styles["myinfo-wrapper"]}>
        <div className={styles.myinfo}>
          <div className={styles["info-header"]}>유저 정보</div>
          <div className={styles.dividingline} />
          <div className={styles["information"]}>
            <div className={styles.blankarea} />
            <div className={styles.blankedinfos}>
              <div>{userInfo.email}</div>
              <div className={styles.password}>
                <div className={styles.text}>
                  <div>비밀번호 : **********</div>
                </div>
                <div className={styles.button}>
                  <button>비밀번호 변경</button>
                </div>
              </div>
              <div className={styles.nickname}>
                <div className={styles.text}>
                  <div>닉네임 : {userInfo.nickname}</div>
                </div>
                <div className={styles.button}>
                  <button>닉네임 변경</button>
                </div>
              </div>
              <div className={styles.cardinfoline} />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

//헤더에 엑세스토큰을 체크하네? 내가 헤더에 넣어야하나?
export const getServerSideProps: GetServerSideProps = async (context) => {
  const cookies: any = context.req.headers.cookie;
  if (cookies.length < 20) {
    return { props: { userInfo: null } };
  }
  const res = await fetch(`${process.env.NEXT_PUBLIC_SERVER_URL}/user`, {
    headers: {
      "Content-Type": "application/json",
      accesstoken: cookies?.slice(cookies?.search("accesstoken") + 12),
    },
  }).catch((err) => {
    return err;
  });

  const userData = await (await res).json();
  console.log(userData);
  return { props: { userInfo: userData.data } };
};





INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(36,2,true,"죽기 좋은 날","손정은","HONG FRAME",null,"https://youtu.be/JzBX1vz4ABY","한국","2018",null,13,1,"<p>남자친구의 배신으로 세상의 끝자락으로 몰린 고등학생 지은은</p><p>인터넷에서 만난 진호와 함께 다음 날 한 시 마포대교에서 투신을 하기로 계획한다.</p><p>2019년 제14회 부산국제어린이청소년영화제</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (36,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(37,2,true,"시간보관소",null,null,"https://i.ytimg.com/vi/_dApFdx_psg/hq720.jpg?sqp=-oaymwEcCOgCEMoBSFXyq4qpAw4IARUAAIhCGAFwAcABBg==&rs=AOn4CLDlgs_qUfenghs2bk3mvCVEczdieg","https://youtu.be/_dApFdx_psg","한국","2020",null,10,1,"<p>슬럼프에 빠진 어느 가난한 화가가 시간보관소에 시간을 맡기게 되면서 벌어지는 이야기.</p><p> </p><p>2020. 10. 각색, 연출</p><p>2020 아시아 골목 영화제 최우수상 수상</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (37,12);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(38,2,true,"감독님 연출하지 마세요","이대영",null,null,"https://youtu.be/BxZdEtW5wRk","한국","2017",null,13,2,"<p>민경은 단편영화 주인공을 맡아 촬영에 임한다.</p><p>시간이 지날수록 현장이 꼬이고 감독과 미세한 균열이 일기 시작한다.</p><p>(2017년 제16회 미쟝센 단편영화제)</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (38,9);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(39,2,true,"밀크","장유진",null,null,"https://youtu.be/zrsUszuLZ0I","한국","2018",null,23,1,"<p>태국 푸켓의 한 리조트에서 하우스 키퍼로 일하는 싸이(Sai).</p><p>가난한 아기엄마인 그녀는 손님으로 온 부잣집 아기엄마를 만나게 된다.</p>",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (39,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(40,2,true,"여름, 버스 ","조범식, 류진아",null,null,"https://youtu.be/-MliIE5PGrI","한국","2018",null,18,1,"<p>버스 안에서 일어나는 여름날의 소소하고 행복한 이야기</p><p>(2018년 제6회 서울구로국제어린이영화제)</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (40,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(41,2,true,"9월이 지나면","고형동",null,null,"https://youtu.be/ui1RZ1Agk2Q?list=PLNPi9xvM1NrMzz18cUGwAfvRdr3_OKo77","한국","2013",null,23,1,"<p>공모전 설계도 제출을 하루 앞두고 선영의 설계도가 사라진다. 선영은 지연을 의심하고, 승조는 지연을 감싸준다.</p><p>(2013년 12회 미쟝센 단편영화제)</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (41,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(42,2,true,"Curve","
Tim Egan",null,"https://t1.daumcdn.net/cfile/tistory/99A68C475EECB8240F","https://youtu.be/2dD3Fawk4y0","호주","2016",null,9,3,"<p>어둡고 가파른 비탈 한가운데서 깨어난 여인.</p><p>잡을 것조차 없는 그곳에서 손바닥의 마찰 만으로 몸을 지탱해야만 한다.</p><p>필사적으로 버티는 그녀에게 이번엔 발 밑 어둠속에서 섬뜩한 소리가 들려오는데...</p><p>(2016년 제18회 쇼트쇼츠국제단편영화제)</p>",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (42,2);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(43,2,true,"Alternative Math","David Maddox",null,"https://m.media-amazon.com/images/M/MV5BZjFhN2FhMTQtNTA2OS00MjUxLWIwN2UtOGY2ZWQ4NWRmOWE4XkEyXkFqcGdeQXVyMjI3MTE4MjU@._V1_.jpg","https://youtu.be/Zh3Yz3PiXZw","미국","2017",null,9,1,null,true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (43,9);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(44,2,true,"두개의 빛: 릴루미노","허진호",null,"http://t1.daumcdn.net/movie/f2498357e6717f12a7db19b031140b2dfea13f43","https://youtu.be/3y5zBY96Mio","한국","2017",null,31,1,"<p>시력을 차츰 잃어가고 있는 인수는 사진동호회에서 같은 시각장애를 가진 수영을 만난다. 잔뜩 움츠러든 자신과 달리 당당한 모습의 수영에게 호감을 느끼는 인수.</p><p>세 번의 출사, 다섯 번의 만남 속 그들은 서로의 빛이 될 수 있을까?</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (44,3);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(45,2,true,"사회수업","이용훈",null,null,"https://youtu.be/oIws_PYoDqE","한국","2017",null,19,1,"<p>대학생 신해는 고교에서 교생실습중이다. 학교에 결원이 생겨서 임용의 가능성이 있지만 그녀를 가로막는 난관은 만만치 않다. 학교에 넌더리가 난 신해, 하지만 교생실습기간은 아직 많이 남았다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (45,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(46,2,true,"야경꾼","노도연",null,null,"https://youtu.be/na2hyI_SAPM","한국","2015",null,13,3,"<p>종합설비센터에서 일하는 남자는 자신의 가게 맞은편 약국의 약사를 관찰한다.</p><p>수상한 남자가 퇴근하는 약사를 쫓아가는 것을 목격한 주인공은 그녀가 위험에 빠졌음을 깨닫고 따라나서지만 오히려 자신이 스토커로 오해를 받는 상황에 처한다.</p><p>(2015년 제14회 미쟝센 단편영화제)</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (46,5);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(47,2,true,"The Ballerina","Aaron Fradkin",null,"https://m.media-amazon.com/images/M/MV5BMWNmNTU1YWYtODM3NS00YjE4LWIwYTUtZjVkYzI1MTFiZjI5XkEyXkFqcGdeQXVyNjUzNTE5NjA@._V1_FMjpg_UX1000_.jpg","https://youtu.be/sTtmpFIaFqc","미국","2021",null,8,3,null,true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (47,2);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(48,2,true,"Other Side of the Box","Caleb J. Phillips",null,"https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/1cnb/image/flNpjRA9coKsncRV3cR_tLzbrd0.jpg","https://youtu.be/OrOYvVf6tIMM","미국","2018",null,15,3,"<p>늦은 밤, 젊은 커플이 오랜 친구로부터 정체불명의 선물을 받게되는데..</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (48,2);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(49,2,true,"The Wrong Rock","Michael Cawood",null,"https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1511402384l/36645014._SX318_.jpg","https://youtu.be/eDaNejmf-1A","미국","2018",null,13,1,null,true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (49,10);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(50,2,true,"언니","김인욱",null,"https://image.tmdb.org/t/p/w500/rG98Zhee2oPY9HysOx0wpRzpqLR.jpg","https://youtu.be/mqh_fWDDzig","한국","2017",null,21,2,"<p>아파트에 살고있는 두 자매. 청소년인 동생이 한밤에 집을 나가고, 언니 혼자 남게된 집안에 괴한이 들어온다.</p><p>동생을 납치한 괴한으로 부터 동생을 구하기위한 언니의 처절한 이야기가 펼쳐진다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (50,8);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(51,2,true,"원룸","이병진",null,null,"https://youtu.be/tyXxjw_SyxE","한국","2018",null,26,1,"<p>야근 때문에 자주 원룸을 비우는 일한은 원룸의 반나절 세입자를 구한다.</p><p>고교 자퇴생 준은 뮤지션의 꿈을 위해 서울로 상경하고 일한의 반나절 룸메이트가 된다.</p><p>두 사람의 예민한 반나절 동거에 준의 고향 친구 병두가 나타나는데...</p>",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (51,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(52,2,true,"가족","",null,null,"https://youtu.be/NXPsKHqkuEM","한국","2016",null,5,1,"<p>반려동물의 시각에서 인간의 이기적인 (혹은 이중적인) 모습을 판타지적으로 표현한 작품. 보는 이들이 영화를 통해 반려동물에 대한 인식과 책임을 다시 한 번 생각해보도록 한다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (52,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(53,2,true,"결백","김재식",null,null,"https://youtu.be/wjLtkqYleqs?list=PL9WxTUjNNa_G0fNdRQQ794Mf0g-7o1p8Y","한국","2015",null,24,1,"<p>나름 큰 사고 없이 수년째 고등학교 교직생활을 성실히 해온 ‘혜원’에게 큰 위기가 찾아온다. 그것은 평소 관리에 신경 썼던 시험답지인 OMR카드를 분실하면서 시작된다. OMR카드를 가져간 사람으로 ‘혜원’의 반 우등생 ‘나영’이 거론되고 전교 1등 ‘나영’이 왜 그것을 가져갔는지에 ‘혜원’은 더 큰 관심이 생긴다. 방과 후 ‘나영’을 따로 불러 그 이유를 물어보는데 ‘나영’이로부터 생각지도 못한 이야기들이 나온다. (2015년 제17회 부산독립영화제)</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (53,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(54,2,true,"철원에서","김혜정",null,null,"https://youtu.be/6OvDfaSJDIc?list=PL9WxTUjNNa_G0fNdRQQ794Mf0g-7o1p8Y","한국","2018",null,17,1,"<p>예술 강사인 남희는 강원도 철원으로 수업을 하러 떠나지만 어느 것 하나 순탄치가 않다. 일을 다 마친 남희는 집으로 돌아가기 위해 터미널로 향하지만 마지막 버스를 놓치게 된다. </p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (54,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(55,2,true,"인싸","차경훈",null,null,"https://www.youtube.com/watch?v=P2X93az89y8&t=2s","한국","2020",null,9,2,"<p>많은 사람들의 관심과 사랑을 받는 인스타 스타 세영이 모두들 세영이의 화려한 일상을 부러워 한다. 하지만 보이는 것과는 조금 달라 보이는데 ...</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (55,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(56,2,true,"사람하는 사랑","오선주",null,null,"https://www.youtube.com/watch?v=jjDZge2tVHA","한국","2019",null,23,1,"<p>무엇과 하는 무엇을 사랑이라 할까.</p><p>어떻게 해야 서로에게 닿을 수 있을까.</p>",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (56,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(57,2,true,"왜 독립영화 감독들은 DVD를 주지 않는가?","구교환",null,"http://t1.daumcdn.net/cfile/276B3D41535419CD2E","https://www.youtube.com/watch?v=j9QzZ5hwDhA","한국","2013",null,28,1,"<p>고기환(32세,남)은 다수의 독립영화에 출연한 배우다. 기환은 대부분의 독립영화 감독들로부터 자신의 출연작dvd를 받지 못했다. 직접 dvd를 받기 위해 과거 함께 작업했던 감독들과 재회하면서 기환은 뜻밖의 사실들을 알게 된다.</p>",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (57,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(58,2,true,"플라이 투 더 스카이","구교환, 이옥섭",null,"http://t1.daumcdn.net/movie/89d0cc6ba339a7761c1916ebe46a5e80e21564d2","https://www.youtube.com/watch?v=7y-eps3O-Ko","한국","2015",null,14,1,"<p>이태리에서 돌아온 성환이 교환과 재회한다. 성환은 한국에서 건설기계조종사 면허를 취득하려고 한다. 교환은 성환에게 우선 자동차 운전연습부터 시킨다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (58,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(59,2,true,"평행소설","최승현, 고민시",null,"http://t1.daumcdn.net/movie/de593f8c8311a34e1346dae076f00768a8920bee","https://www.youtube.com/watch?v=Guc4QrdHUKY","한국","2016",null,3,1,"<p>작가는 연필로 소설을 쓰고 있다. 그의 소설 속 주인공 여자는 남자를 만나 사랑에 빠지고, 멀어지고, 이별하고, 결국 약을 먹고 자살을 하게 되는 때 작가의 연필이 부러진다. 소설은 중단되고 작가는 연필을 깎기 시작한다. 소설속 주인공은 가까스로 먹은 약을 토해서 살아난다. 그녀는 죽고 싶지 않다. 그녀는 살기 위해 주변을 필사적으로 찾다 노트와, 선물받은 만년필을 꺼내고 작가에 대한 소설을 쓰기 시작한다.</p>",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (59,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(60,2,true,"Mouse-X","Justin Tagg",null,"https://refinedgeekery.files.wordpress.com/2014/11/mouse-x.jpg?w=470&h=264","https://www.youtube.com/watch?v=gJPlk6O8XMw","영국","2014",null,15,1,"<p>만약 타임머신이 있다면 무엇을 할까?</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (60,4);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(61,2,true,"Scrap to the Future","michael formanski",null,"https://search3.kakaocdn.net/argon/0x200_85_hr/KCUBSPpA9Mc","https://www.youtube.com/watch?v=d2xhV3krRLU","미국","2015",null,19,1,"<p>스크랩 투 더 퓨쳐는 스크래퍼 펑크스타일의 단편영화로 두 스크래퍼가 스크랩핑 메탈로 만들어진 미래에서 살아남는 이야기이다. 시간여행을 하는 기계를 찾아내 과거로부터 메탈덩어리들을 가져오다 현실의 붕괴가 시작되고 만다.</p>",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (61,12);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(62,2,true,"얼음꽃","이민영",null,"https://search1.kakaocdn.net/argon/0x200_85_hr/JdQeRKh04Ua","https://www.youtube.com/watch?v=Z-cEHs9OTc8","한국","2016",null,14,3,"<p>남편에게 버림받고 임신초기인 지현은 얼굴도 모르는 오프라인 자살 카페 모임에서 만난 사람들(병우, 슬철)과 죽을 장소로 향한다. 지현은 승철과 병우와 자살을 시도하는데 죽는 것도 쉽지 않다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (62,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(63,2,true,"도브맘","김아람",null,"https://www.indieground.kr/fileFolder/a6ac8334-3e1f-46ed-b820-c89d8e2b72ef_jpg",null,"한국","2021",null,29,1,"<p>김아람은 비둘기(Dove) 엄마(Mom)가 되길 자청한다.</p>",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (63,6);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(64,2,true,"가장 보통의 존재","김민지",null,"http://t1.daumcdn.net/movie/2cd6077b3c853a2456986e5d21b2397bda317421","https://www.moviebloc.com/detail/ct_11ea27879e34cd25a799025083dcaf84/ko","한국","2013",null,13,3,"<p>조금 여성스럽고 소심한 진우는 수진을 짝사랑하지만 그녀와 한마디도 못했다. 그러던 어느 날, 빈 강의실에 수진과 둘만 남게 된다. 진우의 친구 용철과 희준은 여성스러운 진우에 대해 온갖 상상을 하기 시작한다.</p>",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (64,3);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(65,2,true,"여름이 지나면","윤진",null,"https://search.pstatic.net/common?type=ofullfill&size=174x242&quality=85&direct=true&src=https%3A%2F%2Fs.pstatic.net%2Fmovie.phinf%2F20141201_274%2F1417418073576XvwMk_JPEG%2Fmovie_image.jpg%3Ftype%3Dw640_2","https://www.moviebloc.com/detail/ct_11ea2842cdedfceaa14b023f85d07bb2/ko","한국","2014",null,14,3,"<p>석호를 짝사랑하고 있는 슬기는 우연히 석호가 양호실에서 희선이의 가슴을 만지려는 걸 목격하게 된다. 석호의 비밀을 지켜주는 대신 슬기는 석호를 마음대로 부려먹기 시작하고 얼떨결에 데이트 아닌 데이트를 하게 된다.</p>",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (65,3);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(66,2,true,"죽어도 좋은날 ","변현아",null,"https://search.pstatic.net/common?type=ofullfill&size=174x242&quality=85&direct=true&src=https%3A%2F%2Fs.pstatic.net%2Fmovie.phinf%2F20120919_233%2F1348032448806WQFs2_JPEG%2Fmovie_image.jpg%3Ftype%3Dw640_2","https://www.moviebloc.com/detail/ct_11ea284b1689a8d7a14b023f85d07bb2/ko","한국","2012",null,16,2,"<p>또래에 비해 초경이 빨리 찾아온 11살 소녀, 유정. 자신이 죽어가고 있다고 오해하게 된 유정은 친한 친구 승준에게만 이 사실을 털어놓는다.</p>",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (66,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(67,2,true,"내 이름 송병준! 이렇게 강할 리가 없어!!","박재현",null,"http://t1.daumcdn.net/movie/cd2c58c2d37f4a2da7db8cebdf4384051560349739485","https://www.moviebloc.com/detail/ct_11ec253997d90b02a2e3025083dcaf84/ko","한국","2018",null,24,2,"<p>학교에서 조용히 지내는 오타쿠 송병준. 그에게는 그를 괴롭히는 용훈이 있다. 용훈은 병준이 만화부 선배에게 물려받은 소중한 시계를 부숴버린다. 분노에 가득 찬 병준. 만화부원들과 힘을 합쳐 무찌르기로 결정한다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (67,9);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(68,2,true,"Lips","Team DADA",null,null,"https://youtu.be/mB0FiOY1S3Q","한국","2018",null,6,1,"<p>소문은 언제 어디서 누가 시작했는지, 아무도 모른다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (68,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(69,2,true,"꽃잠","박해정",null,"https://search.pstatic.net/common?type=ofullfill&size=174x242&quality=85&direct=true&src=https%3A%2F%2Fs.pstatic.net%2Fmovie.phinf%2F20121120_295%2F1353392423987Afz6I_JPEG%2Fmovie_image.jpg%3Ftype%3Dw640_2","https://www.moviebloc.com/detail/ct_11ea26334e9e0d86a14b023f85d07bb2/ko","한국","2012",null,23,3,"<p>오랫동안 짝사랑해 오던 남자와 관계를 갖게 된 여자. 여자는 정서적 교감의 시작이라고 생각하지만, 여자 친구가 있는 남자는 그저 육체적인 욕망만을 채운다. 남자의 허울뿐인 섹스를 알게 되면서도 남자에 대한 자신의 욕망때문에 은밀한 관계를 유지해 가는 여자. 이 둘의 이율 배반적인 관계 속에서 수직적 결정에 대한 우위는 어떻게 흘러 갈 것인가.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (69,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(70,2,true,"수선화","박종철",null,"https://img1.daumcdn.net/thumb/C400x572/?fname=http%3A%2F%2Ft1.daumcdn.net%2Fcfile%2F147DC9364DB7CF1F0B","https://youtu.be/me0XmgTb8ig","한국","2010",null,33,1,"<p>종근은 인터넷으로 청바지를 산 뒤 밑단을 살리면서 기장을 줄이기 위해 동네 세탁소에 수선을 맡긴다.</p><p>그런데 수선이 끝난 바지는 재봉선이 뒤죽박죽 엉망으로 줄여져 있다.</p><p>세탁소 주인과 실랑이 끝에 종근은 5만 원 주고 산 바지로 15만 원을 받아낸다.</p><p>다시 같은 바지를 주문하여 다른 세탁소에 맡겨 보는데, 이번엔 주인과 더 큰 싸움만 일어날 뿐이다.</p><p>결국 전문 수선집을 알아내 바지를 맡기지만, 이곳 주인은 어이없게도 종근의 바지를 잃어버린다.</p><p>(2011년 제5회 시네마디지털서울 영화제)</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (70,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(71,2,true,"방문","신미래",null,null,"https://youtu.be/j3XW8gh_3cQ","한국","2016",null,18,2,"<p>아동학대를 받고 있다는 제보를 받은 사회복지사 동원은 어느 한가로운 시골마을로 조사를 나선다. 신고가 들어온 집을 방문한 동원은 아이들의 행동에 수상함을 느낀다. 무언가 미심쩍은 아이들에게 의구심을 품고 집으로 돌아가던 중 아이들의 다급한 연락을 받고 다시 그 집으로 돌아 가는데... 　</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (71,5);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(72,2,true,"더 앵글러","장승욱",null,"https://www.indieground.kr/fileFolder/p031.jpg",null,"한국","2018",null,13,1,"<p>노아는 매일 물고기를 잡으려고 한다, 이유도 잊은 채. 마을의 점등사 이보는 묵묵히 등불을 밝히며 그를 살핀다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (72,10);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(73,2,true,"레몬","이현지",null,null,"https://youtu.be/nvkhIuG7ias","한국","2017",null,7,1,"<p>롯데월드가 폭피하는 상상으로 사회적 불만을 해소한다는 내용의 단편 영화.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (73,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(74,2,true,"부탁","유재선",null,"http://t1.daumcdn.net/movie/8eb68ab6f006b25f7e3d6f8b4903573291d78835","https://www.moviebloc.com/detail/ct_11ea287720bb5b43a799025083dcaf84/ko","한국","20181",null,17,3,"<p>곤히 잠든 아들을 깨우는 아빠. 주섬주섬 100만 원이 든 봉투를 내밀며 자다가 봉창 두드리는 부탁을 한다. 평범한 듯 평범하지 않은 부자의 특별하고도 웃고픈 사정</p>",true);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (74,7);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(75,1,true,"실","이나연","필름다빈","https://www.indieground.kr/fileFolder/p049.png","https://play24.yes24.com/Main/Detail/S000009830","한국","2020",null,30,1,"<p>창신동 명선의 봉제 공장에 드나드는 사람들. 노동에 관한 저마다의 시선이 명선 주위를 맴돈다. 오랜 세월 함께해 온 이웃 현이 결국 창신동을 떠나게 되자, 명선은 고민에 빠진다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (75,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(76,1,true,"실버택배","김나연","필름다빈","https://www.indieground.kr/fileFolder/8cea0c8c-9a34-44f3-b293-8d3229ccbaf0_jpg",null,"한국","2020",null,26,2,"<p>70세 신정숙씨는 지하철 택배원이다.</p><p>그녀는 어느 날 범죄에 관련된 통장을 운반하게 되고 경찰 조사를 받게 된다.</p><p>하지만 경찰 앞에서 거짓말만 늘어놓는 그녀.</p><p>결국 기록 일지를 모두 불태워 증거인멸을 하게 되는데…</p><p>초고령 사회가 도래하면서 노후 파산에 빠지는 사람들이 점점 늘어나고 있다.</p><p>나름대로의 최선을 다해 살아온 최정숙씨 또한 노후 파산을 피해갈 수만은 없다.</p><p>과연 그녀는 눈앞에 놓인 문제를 어떻게 해결해 나갈까? 그녀가 원하는 인생의 막을 내릴 수 있을까?</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (76,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(77,1,true,"자매들의 밤","김보람","센트럴파크","https://www.indieground.kr/fileFolder/9740ac41-1786-4acf-a83a-e549551fd0b4_jpg",null,"한국","2020",null,22,2,"<p>어머니의 기일에 맞춰, 추모 예배를 드리기 위해 중년의 자매들이 첫째 혜정의 집에 모인다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (77,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(78,1,true,"조금 부족한 여자","허수영","한국독립애니메이션협회","https://www.indieground.kr/fileFolder/297b8a98-925f-4e42-aec8-7147fbfac1ce_jpg",null,"한국","2020",null,10,2,"<p>중요한 시험을 앞두고, 뜻대로 되지 않는 자신의 몸에 실망한 머리가 누워서 침을 뱉고 가출한다. 이때다 싶었는지 오른팔과 상체도 나머지 몸에서 벗어난다. 잔인하고 엽기적이지만 그들의 여정을 함께 하다 보면, 토막난 채로 살아가도 나쁠 것 같지 않다. 종종 있는 일이라고도 하니까, 뭐. 시험 따위 망치면 어때! 그러고 보니 나도 손과 머리가 분리된 것 같다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (78,10);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(79,1,true,"달팽이","김태양","","https://www.indieground.kr/fileFolder/b21dd63b-0446-4691-ac51-c9aea7e04e2b_jpg",null,"한국","2020",null,20,2,"<p>버스에서 잘못 내린 남자는 익숙한 길을 찾으려 거리를 걷는다. 그날 저녁, 우연히 같은 길을 다시 걷게 되는데, 무언가 달라졌다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (79,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(80,1,true,"두 개의 물과 한 개의 라이터","조희영","필름다빈","https://www.indieground.kr/fileFolder/p033.png","https://play24.yes24.com/Main/Detail/S000009821","한국","2020",null,30,1,"<p>어제 낮잠을 잔 지원은 평소와 다르게 이른 아침에 눈이 떠졌다. 책을 좀 보다가 창가에서 담배를 피우는데 저 멀리 숲의 나무들이 눈에 들어온다. 가만히 멈춰 있는 것 같던 나무의 나뭇잎들을 자세히 보니 살살 움직이고 있다. 산책을 간 숲에서 오래 전 친했던 친구이자, 같은 사람을 좋아했던 혜영을 마주친다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (80,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(81,1,true,"지리멸렬","한국영화아카데미","한국영화아카데미","http://t1.daumcdn.net/cfile/136DB110B12BC097E3",null,"한국","1994",null,30,2,"<p>'바퀴벌레'와 '골목 밖으로' '고통의 밤' '에필로그'라는 4의 에피소드로 이루어진 단편. 아침운동을 하면서 남의 문앞에 놓여있는 우유를 습관적으로 훔쳐먹는 신문사 논설위원, 만취해 길가에서 용변을 누려다가 경비원에게 들키게 되는 엘리트 검사, 그리고 도색잡지를 즐겨보다 여학생에게 들킬 뻔한 위기를 겪는 교수, 이들 세사람이 TV 프로그램에 출연하여 사회문제에 관한 대담을 나눈다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (81,9);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(82,1,true,"서울 7000","김홍준, 황주호","","http://t1.daumcdn.net/movie/44faed4d26e7496180eedfb1936c09121573584199590",null,"한국","1976",null,8,1,"<p>서울의 하루를 8mm카메라로 담은 다큐멘터리, 80년대 영화운동이 본격화 되기 전 순수 영화의 유형을 볼 수 있는 귀중한 작품</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (82,6);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(83,1,true,"작은 풀에도 이름 있으니","여성민우회","시네마달","https://www.indieground.kr/fileFolder/18293861-dcd5-4f4c-a167-99bc47eb77a7_jpg",null,"한국","1990",null,38,2,"<p>한국여성민우회와 여성영상집단 바리터가 공동기획제작한 16mm영화로 사무직 여성 노동자의 문제를 다루었다.</p><p>2부로 구성된 이 영화에서 1부는 회사 일과 가사노동 등 이중고에 시달리는 기혼사무직 여성 노동자를,</p><p>제2부는 미혼 사무직 여성 노동자들이 ‘직장의 꽃’에서 탈피하여 노동조합결성에 적극적으로 참여하면서 주인의식을 키워가는 과정을 그렸다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (83,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(84,1,true,"증발","김성민","","https://www.indieground.kr/fileFolder/4366fb67-e686-4975-b0bb-18f366c6eeb7_jpg",null,"한국","2019",null,115,1,"<p>20여 년을 하루같이 딸의 행방을 쫓고 있는 용진씨.</p><p>세상은 벌써 준원이를 잊은 것 같지만 아빠에겐 포기란 없다.</p><p>드디어 장기실종 전담수사팀이 생기고 새 제보자까지 등장!</p><p>수사는 큰 전환점을 맞고, 용진 씨와 가족들은</p><p>다시 한번 희망의 끈을 부여잡는데…</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (84,6);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(85,1,true,"누구는 알고 누구는 모르는","호우주의보","호우주의보","https://www.indieground.kr/fileFolder/p028.jpg","https://purplay.co.kr/service/detail.php?id=178","한국","2019",null,38,1,"<p>안치연 할머니는 어린 시절 한글 교육을 받지 못했다. 나는 할머니를 따라 노인 한글학교로 갔고 그곳엔 여학생만 있었다. 문자로 기록하지 못하고 기억으로 감당해 온 여성들의 시간. 그 시간은 어디에 있을까.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (85,6);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(86,1,true,"우리는 매일매일","영희야놀자","인디스토리","https://www.indieground.kr/fileFolder/f27d857a-9fd9-4840-bf20-5b665210bc05_jpg","https://www.indieartcinema.com/movie/detail?moviecd=016080","한국","2019",null,74,2,"<p>미투운동이 한창이던 어느 날, 옛 친구들이 떠올랐다</p><p>90년대 말 함께 페미니즘을 외쳤던 친구들은 지금 어떻게 살고 있을까.</p><p>삶터, 일터, 가족형태 모두 다른 친구들을 찾아가 던진 질문 하나.</p><p>˝한국사회에서 페미니스트로 산다는 건 뭘까?˝</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (86,6);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(87,1,true,"남매의 여름밤","오누필름","그린나래미디어","https://www.indieground.kr/fileFolder/bcde0c7b-4641-4f9c-baf7-eac0dde569bc_jpg","https://serieson.naver.com/v2/movie/408452?isWebtoonAgreePopUp=true","한국","2019",null,104,1,"<p>방학 동안, 아빠와 함께 할아버지 집에서 지내게 된 남매 옥주와 동주, 그렇게 오래된 2층 양옥집에서의 여름이 시작되고 한동안 못 만났던 고모까지 합세하면서 기억에 남을 온 가족의 이야기가 펼쳐진다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (87,7);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(88,1,true,"웰컴 투 X 월드","한태의","시네마달","https://www.indieground.kr/fileFolder/b5ffc4f0-accc-490a-96ea-ebbb2a817309_jpg","https://play24.yes24.com/Main/Detail/S000008946","한국","2019",null,81,1,"<p>엄마는 왜 아빠가 돌아가신 후에도</p><p>시월드에서 나오지 않는 걸까?</p><p>구로동 집에는 나, 엄마 그리고 친할아버지가 산다.</p><p>12년 전 아빠가 돌아가신 후에도</p><p>엄마는 시아버지를 모시고 산다.</p><p>희생하는 엄마를 보고 자란 나는 결혼이 싫다</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (88,7);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(89,1,true,"가끔 구름","박송열",null,"https://www.indieground.kr/fileFolder/p001.jpg",null,"한국","2018",null,70,2,"<p>영화감독의 꿈을 꾸며 시나리오를 쓰는 명훈. 매번 오디션에서 떨어지는 무명 배우 선희. 둘은 서로의 존재를 위안삼아 연애를 하고 결혼도 다짐한다. 어느 날 명훈의 시나리오는 프로듀서인 선배의 눈에 들어오고 영화제작 기회를 얻는다. 하지만 며칠 후, 선배는 돌연 명훈의 시나리오는 가능성이 없다고 말한다. 실망한 명훈은 자신의 꿈과 현실을 돌아본다. 명훈은 선희에게 취직을 하는게 좋겠다고 말한다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (89,3);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(90,1,true,"한강에게","박근영","인디스토리","https://www.indieground.kr/fileFolder/p019.jpg","https://play24.yes24.com/Main/Detail/S000005813","한국","2018",null,89,2,"<p>첫 시집을 준비하는 시인 ‘진아’. 오랜 연인 ‘길우’의 뜻밖의 사고 후 매일 비슷한 일상을 보내고 있다. 대학교에서 시 수업을 하고, 친구를 만나며 괜찮은 것 같지만 추억과 일상을 헤매며 써지지 않는 시를 붙잡고 있다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (90,3);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(91,1,true,"방문","명소희","시네마달","https://www.indieground.kr/fileFolder/p009.jpg","https://purplay.co.kr/off/movie_introduction.php?fvCode=purplayindie&mvId=94","한국","2018",null,81,2,"<p>가을에 막 접어들 무렵에는 꼭 악몽을 꾸었다. 서울에 올라온 지 4년. 춘천을 떠나오면 끝날 것 같았던 악몽은 계속되었다. 이 악몽에서 깨고 싶었다. 그 때 문득, 춘천이 생각났다. 엄마가 생각났다. 참 오랜만에 나는 다시 춘천으로 향했다. 하지만 그곳엔 여전히 4년 전과 똑같은 삶을 사는 엄마가 있었다. 머릿속에 오로지 ‘열심히’ 라는 단어밖에 모르는 엄마. 그런 엄마를 보는 것이 싫으면서도, 나는 계속 그녀의 삶을 지켜보고 다가간다. 아주 긴 시간을 돌아서 나는 ‘엄마와 나는 왜 이렇게 됐을까’ 라는 질문 앞에 선다. 그 질문에 답을 찾아가며 나는 ‘엄마’를 ‘엄마의 엄마’를 그리고 그들 안의 ‘나’를 마주한다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (91,6);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(92,1,true,"선화의 근황","김소형","센트럴파크","https://www.indieground.kr/fileFolder/p045.png",null,"한국","2018",null,21,2,"<p>선화는 어렵게 취업한 빵집에서 중학교 동창 진경을 만난다. 진경이가 빵집 내에서 따돌림 당하고 있다는 사실을 알게 된 선화는 갈등에 빠진다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (92,1);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(93,1,true,"그림자들의 섬","부산민주언론시민연합사업단미디토리","시네마달","https://www.indieground.kr/fileFolder/2fa34a9d-7953-4ee0-9b61-f9fbb2c995da_jpg","https://serieson.naver.com/v2/movie/158407?isWebtoonAgreePopUp=true","한국","2014",null,99,3,"<p>“마음을 잃지 않았으면 좋겠어요”</p><p>이 시대의 모든 그림자들을 위한 감동의 드라마</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (93,6);
INSERT INTO `board` (id, user_id, is_approve, title, producer, distributor, poster_img, view_link, info_country, info_created_year, info_created_date, info_time, info_limit, info_story, info_subtitle) VALUES(94,1,true,"내가 사는 세상","대구경북영화영상협동조합","인디스토리","https://www.indieground.kr/fileFolder/p005.jpg",null,"한국","2018",null,67,2,"<p>퀵서비스를 하고 있는 민규는 디제이가 되는 것이 꿈이다. 민규는 친한 형인 지홍이 운영하는 클럽에서의 첫 공연을 앞두고 있지만, 지홍은 친하다는 이유로 민규를 아는 동생 이상으로 대우해주지 않는다. 민규의 연인인 시은은 이러한 지홍과 그에 대해 아무 말 못하는 민규가 못 마땅하다. 한편 학교 선배가 운영하는 미술학원에서 입시반 강사를 하고 있는 시은 역시 정해진 일보다 더 많은 일을 요구하는 선배로 인해 학원 생활이 점점 힘들어 진다.</p>",false);
INSERT INTO `board_genre` (board_id, genre_id) VALUES (94,3);																							