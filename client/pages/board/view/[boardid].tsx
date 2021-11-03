import styles from "../../../styles/view_boardid.module.scss";
import { GetServerSideProps } from "next";
import InfoWrapper from "../../../components/boardInfos/InfoWrapper";
process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";

export default function BoarDetails({ film }: any) {
  console.log(film.FindBoardId.data);
  let filmData;
  if (film !== null) {
    filmData = film.FindBoardId.data;
  }
  if (film === null) {
    return <div></div>;
  }

  return (
    <div className={styles["board-detail__wrapper"]}>
      <div className={styles.header__img__wrapper}>
        <div className={styles.header__img}>
          <img src="https://static.news.zumst.com/images/78/2020/08/20/205fe8f1090f4467a908266d3c0af460.jpg" />
        </div>
      </div>
      <div className={styles.poster__img__div}>
        <div className={styles["poster__img-positional"]}>
          <div className={styles["poster__img-wrapper"]}>
            <img className={styles.poster__img} src={filmData.posterImg} />
          </div>
        </div>
      </div>
      <div className={styles.content__wrapper}>
        <div className={styles.content}>
          <div className={styles.filminfo__wrapper}>
            <div className={styles.filminfo__title}>{filmData.title}</div>
            <div className={styles.filminfo__info}>
              <span className={styles.filminfo__info__text}>
                {filmData.infoCreatedAt}
              </span>
              <span className={styles.dot}>・</span>
              <span className={styles.filminfo__info__text}>
                {filmData.genre}
              </span>
              <span className={styles.dot}>・</span>
              <span className={styles.filminfo__info__text}>
                {filmData.infoCountry}
              </span>
            </div>
          </div>
          <div className={styles.filminfoStory}>
            <div>지금 보고싶어요</div>
            <div>
              <a href={filmData.viewLink}>외부 링크로 연결하기...</a>
            </div>
          </div>
          <div></div>
          <div>{filmData.infoStory}</div>
          <InfoWrapper comments={filmData.comment} />
        </div>
      </div>
    </div>
  );
}

export const getServerSideProps: GetServerSideProps = async (context) => {
  // const res = Setaxios.getAxios("/board/view/board?=" + 1).then((res) => {
  // const res = Setaxios.getAxios("/board/view/board?=" + 1);
  // const res = await Setaxios.getAxios("check?type=email&query=q");

  // const res = await fetch("https://localhost:8080/check?type=email&query=q");
  if (context.params === undefined) return { props: {} };
  const query = `{
    FindBoardId(id: ${context.params.boardid}) {
      data {
        id
        userId
        isApprove
        title
        producer
        distributor
        posterImg
        viewLink
        infoCountry
        infoCreatedAt
        infoTime
        infoLimit
        infoStory
        infoSubtitle
        createdAt
        genre {
          name
        }
        casting {
          name
          position
          image
        }
        still {
          image
        }
        comment {
          rating
          userNickname
        }
      } 
    }
}`;
  // const res = await fetch("https://localhost:8080/graphql", {
  //   method: "POST",
  //   body: JSON.stringify({ query }),
  //   headers: {
  //     "Content-Type": "application/json",
  //   },
  // });
  const res = await fetch(`${process.env.NEXT_PUBLIC_SERVER_URL}/graphql`, {
    method: "POST",
    body: JSON.stringify({ query }),
    headers: {
      "Content-Type": "application/json",
    },
  });
  const film = await (await res).json();
  if (film === null) return { props: {} };
  return {
    props: {
      film: film.data,
    },
  };
};
