import { useRouter } from "next/router";
import styles from "../../../styles/view_boardid.module.scss";
import Setaxios from "../../../fetching/Setaxios";
export default function BoarDetails() {
  const filmData = {
    title: "Alternative Math",
    producer: "",
    distributor: "",
    poster_img:
      "https://m.media-amazon.com/images/M/MV5BZjFhN2FhMTQtNTA2OS00MjUxLWIwN2UtOGY2ZWQ4NWRmOWE4XkEyXkFqcGdeQXVyMjI3MTE4MjU@._V1_.jpg",
    view_link: "https://www.youtube.com/watch?v=Zh3Yz3PiXZw&ab_channel=Ideaman",
    info_country: "미국",
    info_created_at: 2017,
    info_time: 9,
    info_limit: 0,
    info_story: "진실이 집단적으로 왜곡되는 현실을 기발하게 풍자한 단편",
    info_subtitle: true,
    info_genre: "코미디",
  };
  const router = useRouter();
  const { boardid } = router.query;
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
            <img className={styles.poster__img} src={filmData.poster_img} />
          </div>
        </div>
      </div>
      <div className={styles.content__wrapper}>
        <div className={styles.content}>
          <div className={styles.filminfo__wrapper}>
            <div className={styles.filminfo__title}>{filmData.title}</div>
            <div className={styles.filminfo__info}>
              <span className={styles.filminfo__info__text}>
                {filmData.info_created_at}
              </span>
              <span className={styles.dot}>・</span>
              <span className={styles.filminfo__info__text}>
                {filmData.info_genre}
              </span>
              <span className={styles.dot}>・</span>
              <span className={styles.filminfo__info__text}>
                {filmData.info_country}
              </span>
            </div>
          </div>
          <div className={styles.filminfo__details}>
            <div>지금 보고싶어요</div>
            <div>
              <a href={filmData.view_link}>외부 링크로 연결하기...</a>
            </div>
          </div>
          <div></div>
          <div>{filmData.info_story}</div>
          <div>
            <h1>영화 상세정보 ID : {boardid}</h1>
          </div>
        </div>
      </div>
    </div>
  );
}

// export async function getServerSideProps(context) {
//   const id = context.params.id;
//   endpoint = "/board/view/board?=" + id;
//   const res = Setaxios.getAxios(endpoint);
//   const data = res.data
//   return {
//     props: {
//       filmData: data,
//     }
//   }
// }
