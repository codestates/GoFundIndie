import styles from "../../styles/components/boardInfos/rating.module.scss";

export default function Rating() {
  return (
    <div>
      <div>평가하기</div>
      <button className={styles.rating}>
        <img src="/emptyStar.png" />
        <div></div>
      </button>
    </div>
  );
}
