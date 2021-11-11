import styles from "../styles/mypage.module.scss";
export default function Management() {
  return (
    <div className={styles.mypage}>
      <div className={styles.mapageblock}>
        <div className={styles.header__blank__div} />
        <div className={styles["myinfo-wrapper"]}>
          <div className={styles.myinfo}>
            <div className={styles["info-header"]}>승인 대기중 보드 리스트</div>
            <div className={styles.dividingline} />
            <div className={styles["information"]}>
              <div className={styles.blankarea} />
              <div className={styles.blankedinfos}>
                <div>
                  <div className={styles.password}>
                    <div className={styles.text}>
                      <div>보드 네임 샘플</div>
                    </div>
                    <div className={styles.button}>
                      <button>승인/취소 버튼 위치</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
