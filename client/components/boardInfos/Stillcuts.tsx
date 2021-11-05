import styles from "../../styles/components/boardInfos/stills.module.scss";

export default function Stillcuts({ stills, onFocus }: any) {
  let counter = 0;
  return (
    <>
      <div>갤러리</div>
      {!onFocus ? (
        <div className={styles.wrapper}>
          {stills.map((el: { image: string | undefined }) => {
            counter++;
            if (counter > 4) {
              return;
            }
            return (
              <div
                className={styles.reviewstill}
                onClick={
                  counter === 4
                    ? () => {
                        //TODO 클릭하면 상위컴포넌트 바뀌게 어떻게하지
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
        <div></div>
      )}
    </>
  );
}
