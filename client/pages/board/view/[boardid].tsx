import styles from "../../../styles/view_boardid.module.scss";
import InfoWrapper from "../../../components/boardInfos/InfoWrapper";
import { GetServerSideProps } from "next";
import Rating from "../../../components/boardInfos/Rating";
import Setaxios from "../../../fetching/Setaxios";
import Cookies from "js-cookie";

process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";

export default function BoarDetails({ film }: any) {
  let filmData;
  if (film !== null) {
    filmData = film.FindBoardId.data;
  } else {
    return <></>;
  }
  function Payment() {
    Setaxios.getAxios("pay/ready?amount=3000").then((res) => {
      const urlcomp: any = res.data;
      console.log(urlcomp);
      Cookies.set("tid", urlcomp.data.tid);
      const payment: Window | null = window.open(
        urlcomp.data.next_redirect_pc_url,
        "_blank",
        "width=600,height=500"
      );
      if (payment === null) return;
      payment.addEventListener("unload", () => {
        location.reload();
      });
    });
  }
  async function SwitchLikeBoard() {
    const query = `mutation SwitchLikeBoard($boardId: ID!){
      SwitchLikeBoard(boardId: $boardId){
        code
      }
    }`;
    Setaxios.postgraphql("graphql", query, 33)
      .then((res) => {
        const data: any = res.data;
        console.log(res);
        if (data.data.SwitchLikeBoard.code === 2000) {
          alert("성공적으로 담아두었습니다");
        }
      })
      .catch((err) => alert(err));
  }
  // 보드테이블에 평점계산해서 내보는게 없네
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
              <span>{filmData.genre.map((el: any) => el.name)}</span>
              <span className={styles.dot}>・</span>
              <span>{filmData.infoCountry}</span>
            </div>
            <div className={styles.bucket} onClick={SwitchLikeBoard}>
              <img src="/plusButton.png" alt="plus" />
              <div>담아둘래요</div>
            </div>
            <div className={styles.ratings}>
              <Rating />
              <button className={styles.donation} onClick={Payment}>
                <img src="/heart.png" />
                후원하기
              </button>
            </div>
          </div>
          <div className={styles.filmLink}>
            <div>지금 보고싶어요</div>
            <div>
              <a href={filmData.viewLink}>외부 링크로 연결하기...</a>
            </div>
          </div>
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
            profilePicture
            donation
            body
            spoiler
            like
            ratingChecked
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
