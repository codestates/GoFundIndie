import styles from "../styles/mypage.module.scss";
import { GetServerSideProps } from "next";
import Link from "next/link";
process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";
export default function Mypage({ userInfo, film }: any) {
  if (userInfo === null) {
    return <div className={styles.error}>로그인이 필요합니다</div>;
  }
  console.log(userInfo);
  const mybuckets = () => {
    return film.map((movie: any) => {
      return (
        <div key={movie.id} className={styles["information"]}>
          <div className={styles.posterimg}>
            <img src={movie.posterImg} />
          </div>
          <div className={styles.blankedinfos}>
            <div>{movie.title}</div>
            <div>{movie.infoCreatedYear}</div>
            <div>{movie.infoTime}</div>
            <div>{movie.infoCountry}</div>
            <Link href="/board/view/[boardid]" as={`/board/view/${movie.id}`}>
              상세페이지로
            </Link>
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
            <div className={styles["info-header"]}>내가 담아둔 영화</div>
            <div className={styles.dividingline} />
            {mybuckets()}
            {mybuckets()}
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
    FindBoards(Type: SEARCH_TYPES_MY, Limit: 4) {
      data {
        id
        title
        posterImg
        infoCountry
        infoCreatedYear
        infoTime
        infoLimit
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
    props: { userInfo: userData.data, film: film.data.FindBoards.data },
  };
};
