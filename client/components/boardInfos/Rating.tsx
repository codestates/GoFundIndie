import styles from "../../styles/components/boardInfos/rating.module.scss";

export default function Rating() {
  const star = (num: string) => {
    return (
      <button id={num} className={styles.rating}>
        <img
          onMouseOver={(e) => {
            const img: any = e.target;
            img.src = "/star.png";
          }}
          onMouseLeave={(e) => {
            const img: any = e.target;
            img.src = "/emptyStar.png";
          }}
          src="/emptyStar.png"
        />
        <div
          onMouseOver={(e) => {
            const div: any = e.target;
            div.parentNode.childNodes[0].src = "/halfStar.png";
          }}
          onMouseLeave={(e) => {
            const div: any = e.target;
            div.parentNode.childNodes[0].src = "/emptyStar.png";
          }}
        ></div>
      </button>
    );
  };
  return (
    <div>
      <div>평가하기</div>
      {star("1")}
      {star("2")}
      {star("3")}
      {star("4")}
      {star("5")}
    </div>
  );
}
