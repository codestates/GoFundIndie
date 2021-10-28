import { useRouter } from "next/router";
import styles from "../../../styles/view_boardid.module.scss";

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
    <div>
      <div className={styles.header__img__wrapper}>
        <img
          className={styles.header__img}
          src={filmData.poster_img}
          width="500"
          height="500"
        />
      </div>
      <div className={styles.poster__img__div}>
        <img className={styles.poster__img} src={filmData.poster_img} />
      </div>
      <div className={styles.filminfo__wrapper}>
        <div className={styles.filminfo__title}>{filmData.title}</div>
        <div className={styles.filminfo__someinfo}>
          <span className={styles.filminfo__someinfo__text}>
            {filmData.info_created_at}
          </span>
          <span className={styles.filminfo__someinfo__text}>
            {filmData.info_genre}
          </span>
          <span className={styles.filminfo__someinfo__text}>
            {filmData.info_country}
          </span>
        </div>
      </div>
      <div className={styles.filminfo__details}>
        <div>기본 정보</div>
        <div>영화</div>
        <div>
          <a href={filmData.view_link}>링크</a>
        </div>
        {/* <iframe
          width="80%"
          height="700px"
          src={"https://www.youtube.com/embed/Zh3Yz3PiXZw"}
          title="YouTube video player"
          frameBorder="0"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
          allowFullScreen
        ></iframe> */}
      </div>
      <div>{filmData.info_story}</div>
      <div>
        <h1>영화 상세정보 ID : {boardid}</h1>
      </div>
    </div>
  );
}
