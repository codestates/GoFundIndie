import { GetServerSideProps } from "next";
import { ChangeEvent, useState } from "react";
import Setaxios from "../fetching/Setaxios";
import styles from "../styles/mypage.module.scss";
export default function Newboard({ board }: any) {
  const [movieData, setMovieData] = useState({
    boardId: board.CreateTempBoard.data.id,
    title: "",
    infoCountry: "",
    infoCreatedYear: "",
    infoTime: 0,
    infoStory: "",
    producer: "",
    infoLimit: 0,
    viewLink: "",
    infoSubtitle: true,
  });
  const handleInputValue =
    (key: string) => (e: ChangeEvent<HTMLInputElement>) => {
      setMovieData({ ...movieData, [key]: e.target.value });
    };
  const entitleMovie = () => {
    const query = `mutation CompleteBoard ($board: boardInput!) {
        CompleteBoard (board: $board) {
            code
            data {
                id
            }
        }
    }`;
    Setaxios.postGraphql(query, { board: movieData })
      .then((res) => {
        const data: any = res.data;
      })
      .catch((err) => alert(err));
  };
  return (
    <div className={styles.mypage}>
      <div className={styles.mapageblock}>
        <div className={styles.header__blank__div} />
        <div className={styles["myinfo-wrapper"]}>
          <div className={styles.myinfo}>
            <div className={styles["info-header"]}>영화 등록하기</div>
            <div className={styles.dividingline} />
            <div className={styles["information"]}>
              <table className={styles.inputtable}>
                <tbody>
                  <tr>
                    <th className={styles.thstyle}>[필수] 영화 제목</th>
                    <td className={styles.tdstyle}>
                      <input
                        onChange={handleInputValue("title")}
                        className={styles.input}
                      />
                    </td>
                  </tr>
                  <tr>
                    <th className={styles.thstyle}>[필수] 상영 시간</th>
                    <td className={styles.tdstyle}>
                      <input
                        onChange={handleInputValue("infoTime")}
                        className={styles.input}
                      />
                    </td>
                  </tr>
                  <tr>
                    <th className={styles.thstyle}>[필수] 동영상 주소</th>
                    <td className={styles.tdstyle}>
                      <input
                        onChange={handleInputValue("viewLink")}
                        placeholder="유튜브 주소나 기타 외부링크 주소"
                        className={styles.input}
                      />
                    </td>
                  </tr>
                  <tr>
                    <th className={styles.thstyle}>[필수] 연령 제한</th>
                    <td className={styles.tdstyle}>
                      <input
                        onChange={handleInputValue("infoLimit")}
                        className={styles.input}
                      />
                    </td>
                  </tr>
                  <tr>
                    <th className={styles.thstyle}>[필수] 국가</th>
                    <td className={styles.tdstyle}>
                      <input
                        onChange={handleInputValue("infoCountry")}
                        className={styles.input}
                      />
                    </td>
                  </tr>
                  <tr>
                    <th className={styles.thstyle}>[필수] 한국어 자막 지원</th>
                    <td className={styles.tdstyle}>
                      <input
                        type="checkbox"
                        onChange={handleInputValue("infoSubtitle")}
                        className={styles.input}
                      />
                    </td>
                  </tr>
                  <tr>
                    <th className={styles.thstyle}>[필수] 제작연도</th>
                    <td className={styles.tdstyle}>
                      <input
                        onChange={handleInputValue("infoLimit")}
                        className={styles.input}
                      />
                    </td>
                  </tr>
                  <tr>
                    <th className={styles.thstyle}>[필수] 영화 줄거리</th>
                    <td className={styles.tdstyle}>
                      <input
                        onChange={handleInputValue("infoStory")}
                        className={styles.input}
                      />
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div className={styles.button_wrapper}>
              <button onClick={entitleMovie} className={styles.moviebutton}>
                등록하기
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
export const getServerSideProps: GetServerSideProps = async (context) => {
  const cookies: any = context.req.headers.cookie;

  const query = `
  mutation CreateTempBoard {
      CreateTempBoard {
          code
          data {
              id
          }
      }
  }`;

  const res = await fetch(`${process.env.NEXT_PUBLIC_SERVER_URL}/graphql`, {
    method: "POST",
    body: JSON.stringify({ query }),
    headers: {
      "Content-Type": "application/json",
      accesstoken: cookies?.slice(cookies?.search("accesstoken") + 12),
    },
  }).catch((err) => {
    return err;
  });
  if (res.code === "ECONNREFUSED") return { props: { film: null } };
  const board = await (await res).json();
  if (!board) return { props: {} };

  return {
    props: {
      board: board.data,
    },
  };
};
