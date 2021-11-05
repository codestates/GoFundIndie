import styles from "../styles/mypage.module.scss";
export default function Mypage() {
  return (
    <div className={styles.mypage}>
      <div className={styles.header__blank__div} />
      <div className={styles["myinfo-wrapper"]}>
        <div className={styles.myinfo}>테스트</div>
      </div>
    </div>
  );
}
