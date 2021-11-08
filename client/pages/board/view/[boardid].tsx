import styles from "../../../styles/view_boardid.module.scss";
import { GetServerSideProps } from "next";
import InfoWrapper from "../../../components/boardInfos/InfoWrapper";
process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";

export default function BoarDetails({ film }: any) {
  console.log(film);
  let filmData;
  if (film !== null) {
    filmData = film.FindBoardId.data;
  }
  if (film === null) {
    return <></>;
  }

  return (
    <div className={styles["board-detail__wrapper"]}>
      <div className={styles.header__img__wrapper}>
        <div className={styles.header__img}>
          {filmData.still[0] ? <img src={filmData.still[0].image} /> : null}
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
              <span>{filmData.infoCreatedYear}</span>
              <span className={styles.dot}>・</span>
              <span>{filmData.genre}</span>
              <span className={styles.dot}>・</span>
              <span>{filmData.infoCountry}</span>
            </div>
            <div className={styles.bucket}>
              <img src="/plusButton.png" alt="plus" />
              <div>담아둘래요</div>
            </div>
          </div>
          <div className={styles.filmLink}>
            <div>지금 보고싶어요</div>
            <div>
              <a href={filmData.viewLink}>외부 링크로 연결하기...</a>
            </div>
          </div>
          <div></div>
          <InfoWrapper
            cast={filmData.casting}
            stills={filmData.still}
            comments={filmData.comment}
          />
        </div>
      </div>
    </div>
  );
}

export const getServerSideProps: GetServerSideProps = async (context) => {
  if (context.params === undefined) return { props: {} };
  //TODO:query를 따로 관리하기
  const query = `{
    FindBoardId(id: ${context.params.boardid}) {
      data {
        id
        isApprove
        title
        producer
        distributor
        posterImg
        viewLink
        infoCountry
        infoCreatedYear
        infoCreatedDate
        infoTime
        infoLimit
        infoStory
        infoSubtitle
        createdAt
        commentAmount
        likeAmount
        genre {
            id
            name
        }
        casting {
            id
            name
            position
            image
        }
        still {
            id
            image
        }
        comment {
            id
            rating
            userNickname
        }
      }
    }
}`;

  const res = await fetch(`${process.env.NEXT_PUBLIC_SERVER_URL}/graphql`, {
    method: "POST",
    body: JSON.stringify({ query }),
    headers: {
      "Content-Type": "application/json",
    },
  }).catch((err) => {
    return err;
  });

  const film = await (await res).json();

  if (film === null) return { props: {} };
  return {
    props: {
      film: film.data,
    },
  };
};
