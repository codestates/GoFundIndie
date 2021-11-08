import { useState } from "react";
import styles from "../../styles/components/boardInfos/cast.module.scss";
export default function Cast({ cast, onFocus }: any) {
  const [component, setComponent] = useState<boolean>(false);

  let testcast = [
    { ...cast[0] },
    { id: 35, name: "테스트", image: cast[0].image, position: 2 },
    { id: 36, name: "테스트", image: cast[0].image, position: 2 },
    { id: 37, name: "테스트", image: cast[0].image, position: 2 },
    { id: 38, name: "테스트", image: cast[0].image, position: 2 },
    { id: 39, name: "테스트", image: cast[0].image, position: 2 },
    { id: 40, name: "테스트", image: cast[0].image, position: 3 },
  ];
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
  return (
    <div className={styles["cast"]}>
      <div className={styles.head}>
        <div>출연진</div>
        {onFocus ? null : (
          <button
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
          {testcast.map((el) => {
            count++;
            if (!onFocus && count > 6) {
              return;
            }
            return (
              <div className={styles.infos} key={el.id}>
                <img src={el.image} />
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
