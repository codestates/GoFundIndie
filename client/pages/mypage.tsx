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
