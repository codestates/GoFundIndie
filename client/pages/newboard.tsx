import styles from "../styles/mypage.module.scss";
export default function Newboard() {
  return (
    <div className={styles.mypage}>
      <div className={styles.mapageblock}>
        <div className={styles.header__blank__div} />
        <div className={styles["myinfo-wrapper"]}>
          <div className={styles.myinfo}>
            <div className={styles["info-header"]}>영화 등록하기</div>
            <div className={styles.dividingline} />
            <div className={styles["information"]}>
              <table className={styles.inputtable}>
                <tbody>
                  <tr>
                    <th className={styles.thstyle}>[필수] 성명</th>
                    <td className={styles.tdstyle}>
                      <input />
                    </td>
                  </tr>
                  <tr>
                    <th>[필수] 성명</th>
                    <td>
                      <input />
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
