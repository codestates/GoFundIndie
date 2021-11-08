import styles from "../../styles/components/boardInfos/stills.module.scss";

export default function Stillcuts({ stills, onFocus }: any) {
  let counter = 0;
  return (
    <div className={styles["still-wrapper"]}>
      <div className={styles.head}>갤러리</div>
      {!onFocus ? (
        <div className={styles.wrapper}>
          {stills.map((el: { image: string | undefined }) => {
            counter++;
            if (counter > 4) {
              return;
            }
            return (
              <div
                key={el.image}
                className={styles.reviewstill}
                onClick={
                  counter === 4
                    ? () => {
                        //TODO 클릭하면 상위컴포넌트 바뀌게하기
                      }
                    : undefined
                }
              >
                {counter === 4 ? <div>+{stills.length - 4}</div> : null}
                <img height="200px" src={el.image} />
              </div>
            );
          })}
        </div>
      ) : (
        <div className={styles.fullwrapper}>
          {stills.map((el: { image: string | undefined }) => {
            return (
              <div key={el.image} className={styles.displaystill}>
                <img src={el.image} />
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
}
