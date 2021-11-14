import { useState } from "react";
import styles from "../../styles/components/boardInfos/stills.module.scss";

export default function Stillcuts({ stills, onFocus }: any) {
  let counter = 0;
  const [pirctureModalOpen, setPictureModalOpen] = useState<boolean>(false);
  const [selectedImage, setSelectedImage] = useState<any>();
  const pictureModal = () => {
    return (
      <div key="picmodal">
        <div
          className={styles["screen-wrapper"]}
          onClick={(e) => {
            const screen = document.getElementById("screen");
            e.target === screen ? setPictureModalOpen(false) : null;
          }}
        >
          <div id="screen" className={styles["picturemodal-wrapper"]}>
            <div className={styles.picturemodal}>
              {selectedImage?.src ? (
                <img
                  draggable="false"
                  onClick={() => setPictureModalOpen(false)}
                  src={selectedImage.src}
                />
              ) : null}
            </div>
          </div>
        </div>
      </div>
    );
  };
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
                className={
                  counter === 4 && stills.length - 4 !== 0
                    ? styles.reviewstills
                    : styles.reviewstill
                }
                onClick={
                  counter === 4 && stills.length - 4 !== 0
                    ? () => {
                        document.getElementById("stills")?.click();
                      }
                    : (e) => {
                        setSelectedImage(e.target);
                        setPictureModalOpen(true);
                      }
                }
              >
                {counter === 4 && stills.length - 4 !== 0 ? (
                  <div>+{stills.length - 4}</div>
                ) : null}
                <img height="200px" draggable="false" src={el.image} />
              </div>
            );
          })}
        </div>
      ) : (
        <div className={styles.fullwrapper}>
          {stills.map((el: { image: string | undefined }) => {
            return (
              <div
                key={el.image}
                className={styles.displaystill}
                onClick={(e) => {
                  setSelectedImage(e.target);
                  setPictureModalOpen(true);
                }}
              >
                <img draggable="false" src={el.image} />
              </div>
            );
          })}
        </div>
      )}
      {pirctureModalOpen ? pictureModal() : null}
    </div>
  );
}
