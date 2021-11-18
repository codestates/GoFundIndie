import { useState } from "react";
import styles from "../../styles/components/boardInfos/cast.module.scss";
export default function Cast({ cast, onFocus }: any) {
  const [component, setComponent] = useState<boolean>(false);
  let count = 0;
  function Positioner(num: Number) {
    switch (num) {
      case 1:
        return <div>감독</div>;
      case 2:
        return <div>주연</div>;
      case 3:
        return <div>조연</div>;
    }
  }
  function Emptyimg(num: Number) {
    switch (num) {
      case 1:
        return <img src="/Director.jpg" />;
      case 2:
        return <img src="/Lead.jpg" />;
      case 3:
        return <img src="/supporting.jpg" />;
    }
  }

  return (
    <div className={styles["cast"]}>
      <div className={styles.head}>
        <div>출연진</div>
        {onFocus ? null : (
          <button
            className={styles.open}
            onClick={() => {
              setComponent(!component);
            }}
          >
            +펼치기
          </button>
        )}
      </div>
      {!component && !onFocus ? null : (
        <div className={styles["cast-wrapper"]}>
          {cast.map((el: any) => {
            count++;
            if (!onFocus && count > 6) {
              return;
            }
            return (
              <div className={styles.infos} key={el.id}>
                <div className={styles.imagebox}>
                  {el.image ? (
                    <img
                      src={el.image}
                      onError={(e) => {
                        const img: any = e.target;
                        const noimg: any = Emptyimg(el.position);
                        img.src = noimg.props.src;
                      }}
                    />
                  ) : (
                    Emptyimg(el.position)
                  )}
                </div>
                <div className={styles.name}>{el.name}</div>
                <div className={styles.role}>{Positioner(el.position)}</div>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
}
