import styles from "../styles/mypage.module.scss";
import { GetServerSideProps } from "next";
import Link from "next/link";

process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";

export default function Mypage({ userInfo, film }: any) {
  if (userInfo === null) {
    return <div className={styles.error}>로그인이 필요합니다</div>;
  }
  function strip_tags(str: any) {
    return str.replace(/(<([^>]+)>)/gi, "");
  }
  const mybuckets = () => {
    if (film.data.FindLikeBoards.data.length === 0) return <></>;
    return film.data.FindLikeBoards.data.map((movie: any) => {
      let story = strip_tags(movie.infoStory);
      return (
        <div key={movie.id} className={styles["information"]}>
          <div className={styles.posterimg}>
            <img src={movie.posterImg} />
          </div>
          <div className={styles.blankedinfos}>
            <div className={styles.title}>
              <span>{movie.title}</span>
              <span className={styles.link}>
                <Link
                  href="/board/view/[boardid]"
                  as={`/board/view/${movie.id}`}
                >
                  영화 상세페이지로
                </Link>
              </span>
            </div>
            <div>
              <span>{movie.infoCreatedYear}</span>
              <span className={styles.dot}>・</span>
              <span>{movie.genre[0].name}</span>
              <span className={styles.dot}>・</span>
              <span>{movie.infoCountry}</span>
            </div>
            <div>러닝타임 {movie.infoTime}분</div>
            <div>{movie.infoCountry}</div>
            <p>{story}</p>
          </div>
        </div>
      );
    });
  };

  const mydonations = () => {
    if (film.data.FindDonationBoards.data.length === 0) return <></>;
    return film.data.FindDonationBoards.data.map((movie: any) => {
      console.log(movie);
      return (
        <div key={movie.id} className={styles["information"]}>
          <div className={styles.posterimg}>
            <img src={movie.posterImg} />
          </div>
          <div className={styles.blankedinfos}>
            <div className={styles.title}>
              <span>{movie.title}</span>
              <span className={styles.link}>
                <Link
                  href="/board/view/[boardid]"
                  as={`/board/view/${movie.id}`}
                >
                  영화 상세페이지로
                </Link>
              </span>
            </div>
            <div>
              {movie.donationCreatedAt.slice(
                0,
                movie.donationCreatedAt.length - 5
              )}
            </div>
            <div className={styles.pay}>
              <div className={styles.logo}>
                <div className={styles.border}>
                  <img src="/pay_logo.png" />
                </div>
              </div>
              <div className={styles.donation}>
                <div className={styles.amount}>₩{movie.donationAmount}</div>
              </div>
            </div>
          </div>
        </div>
      );
    });
  };
  return (
    <div className={styles.mypage}>
      <div className={styles.mapageblock}>
        <div className={styles.header__blank__div} />
        <div className={styles["myinfo-wrapper"]}>
          <div className={styles.myinfo}>
            <div className={styles["info-header"]}>유저 정보</div>
            <div className={styles.dividingline} />
            <div className={styles["information"]}>
              <div className={styles.blankarea} />
              <div className={styles.blankedinfos}>
                <div>
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
                </div>
              </div>
            </div>
            <div className={styles["info-header"]}>내가 후원한 영화</div>
            <div className={styles.dividingline} />
            {mydonations()}
            <div className={styles["info-header"]}>내가 담아둔 영화</div>
            <div className={styles.dividingline} />
            {mybuckets()}
          </div>
        </div>
      </div>
    </div>
  );
}

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

  const query = `{
    FindLikeBoards(Limit : 4) {
      data {
        id
        title
        posterImg
        infoCountry
        infoCreatedYear
        infoTime
        infoLimit
        infoStory
        genre{
            name
        }
      }
    }
    FindDonationBoards(Limit : 4){
        data{
            id
            title
            donationAmount
            donationCreatedAt
        }
    }
}`;

  const res2 = await fetch(`${process.env.NEXT_PUBLIC_SERVER_URL}/graphql`, {
    method: "POST",
    body: JSON.stringify({ query }),
    headers: {
      "Content-Type": "application/json",
      accesstoken: cookies?.slice(cookies?.search("accesstoken") + 12),
    },
  }).catch((err) => {
    return err;
  });
  const film = await (await res2).json();
  const userData = await (await res).json();
  return {
    props: { userInfo: userData.data, film: film },
  };
};
