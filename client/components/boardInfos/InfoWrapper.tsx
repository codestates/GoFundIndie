import Cast from "./Cast";
import Comments from "./Comments";
import Stillcuts from "./Stillcuts";
import styles from "../../styles/components/boardInfos/infowrapper.module.scss";
import { useState } from "react";

export default function InfoWrapper({ filmData }: any) {
  console.log(filmData);
  const cast = filmData.casting;
  const stills = filmData.still;
  const comments = filmData.comment;
  const [componentChanger, setComponentChanger] = useState<boolean>(false);
  const InfoStyler = (e: any) => {
    const highlight = document.getElementsByClassName(styles.highlight)[0];
    if (highlight) {
      highlight.classList.remove(styles.highlight);
    }
    setComponentChanger(!componentChanger);
    e.target.classList.add(styles.highlight);
  };
  function strip_tags(str: any) {
    return str.replace(/(<([^>]+)>)/gi, "");
  }
  const defaultState = (
    <div>
      <div className={styles.desc}>
        <div>{filmData.title}</div>
        <span>{filmData.infoCreatedYear}</span>
        <span className={styles.dot}>・</span>
        <span>{filmData.genre.map((el: any) => el.name)}</span>
        <span className={styles.dot}>・</span>
        <span>{filmData.infoCountry}</span>
        <div>{filmData.infoTime}분</div>
        {filmData.infoStory ? (
          <div dangerouslySetInnerHTML={{ __html: filmData.infoStory }}></div>
        ) : null}
      </div>
      {cast.length !== 0 ? <Cast cast={cast} onFocus={false} /> : null}
      {stills.length !== 0 ? (
        <Stillcuts stills={stills} onFocus={false} />
      ) : null}
      <Comments comments={comments} />
    </div>
  );
  function InfoHandler() {
    const btn: any = document.getElementsByClassName(styles.highlight)[0];
    switch (btn.value) {
      case "default":
        return defaultState;
      case "cast":
        return <Cast cast={cast} onFocus={true} />;
      case "stillcut":
        return <Stillcuts stills={stills} onFocus={true} />;
      case "rating":
        return <Comments comments={comments} />;
    }
  }
  return (
    <div className={styles.infowrapper}>
      <div className={styles["menu-wrapper"]}>
        <button
          value="default"
          className={styles.highlight}
          onClick={InfoStyler}
        >
          기본 정보
        </button>
        <button value="cast" onClick={InfoStyler}>
          출연/제작
        </button>
        <button id="stills" value="stillcut" onClick={InfoStyler}>
          포토
        </button>
        <button value="rating" onClick={InfoStyler}>
          평점
        </button>
      </div>
      {typeof document !== "undefined"
        ? document.getElementsByClassName(styles.highlight)[0] === undefined
          ? defaultState
          : InfoHandler()
        : null}
    </div>
  );
}
