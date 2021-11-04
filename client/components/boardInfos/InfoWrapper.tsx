import Cast from "./Cast";
import Comments from "./Comments";
import Stillcuts from "./Stillcuts";
import styles from "../../styles/components/boardInfos/infowrapper.module.scss";
import { useState } from "react";

export default function InfoWrapper({ comments }: any) {
  const [componentChanger, setComponentChanger] = useState<boolean>(false);
  const InfoStyler = (e: any) => {
    const highlight = document.getElementsByClassName(styles.highlight)[0];
    if (highlight) {
      highlight.classList.remove(styles.highlight);
    }
    setComponentChanger(!componentChanger);
    e.target.classList.add(styles.highlight);
  };
  function InfoHandler() {
    const btn: any = document.getElementsByClassName(styles.highlight)[0];

    switch (btn.value) {
      case "default":
        return (
          <div>
            <Cast />
            <Stillcuts />
            <Comments comments={comments} />
          </div>
        );
      case "cast":
        return <Cast />;
      case "stillcut":
        return <Stillcuts />;
      case "rating":
        return <Comments comments={comments} />;
    }
  }
  return (
    <>
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
        <button value="stillcut" onClick={InfoStyler}>
          포토
        </button>
        <button value="rating" onClick={InfoStyler}>
          평점
        </button>
      </div>
      {console.log(typeof document)}
      {typeof document !== "undefined" ? (
        document.getElementsByClassName(styles.highlight)[0] === undefined ? (
          <div>
            <Cast />
            <Stillcuts />
            <Comments comments={comments} />
          </div>
        ) : (
          InfoHandler()
        )
      ) : null}
    </>
  );
}
