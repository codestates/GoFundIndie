import styles from "../../styles/components/boardInfos/rating.module.scss";
import { useState } from "react";
import Setaxios from "../../fetching/Setaxios";

export default function Rating({ boardid }: { boardid: Number }) {
  console.log(boardid);
  const [commentModalOpen, setCommentModalOpen] = useState<boolean>(false);
  const [bodyMessage, setBodymessage] = useState("");
  const [rate, setRate] = useState<number>(0);
  const star = (num: string) => {
    return (
      <button
        id={num}
        onClick={(e) => {
          const star: any = e.target;
          console.log(star.tagName === "DIV");
          if (star.tagName === "DIV" && star.parentNode.id !== 1) {
            setRate(Number(star.parentNode.id) - 0.5);
          } else if (star.tagName === "IMG" && star.parentNode.id !== 1) {
            setRate(Number(star.parentNode.id));
          }
          setCommentModalOpen(true);
        }}
        className={styles.rating}
      >
        <img
          onMouseOver={(e) => {
            const img: any = e.target;
            if (img.parentNode.id !== 1) {
              for (let i = 0; i < img.parentNode.id; i++) {
                let div: any = document.getElementById(`${i + 1}`);
                if (div === null) return;
                div.childNodes[0].src = "/star.png";
              }
            }
            img.src = "/star.png";
          }}
          onMouseLeave={(e) => {
            const img: any = e.target;
            img.src = "/emptyStar.png";
            if (img.parentNode.id !== 1) {
              for (let i = 0; i < img.parentNode.id; i++) {
                let div: any = document.getElementById(`${i + 1}`);
                if (div === null) return;
                div.childNodes[0].src = "/emptyStar.png";
              }
            }
          }}
          src="/emptyStar.png"
        />
        <div
          onMouseOver={(e) => {
            const div: any = e.target;
            if (div.parentNode.id !== 1) {
              for (let i = 0; i < div.parentNode.id; i++) {
                let divP: any = document.getElementById(`${i + 1}`);
                if (divP === null) return;
                divP.childNodes[0].src = "/star.png";
              }
            }
            div.parentNode.childNodes[0].src = "/halfStar.png";
          }}
          onMouseLeave={(e) => {
            const div: any = e.target;
            if (div.parentNode.id !== 1) {
              for (let i = 0; i < div.parentNode.id; i++) {
                let divP: any = document.getElementById(`${i + 1}`);
                if (divP === null) return;
                divP.childNodes[0].src = "/emptyStar.png";
              }
            }
            div.parentNode.childNodes[0].src = "/emptyStar.png";
          }}
        ></div>
      </button>
    );
  };

  function SubmitComment() {
    console.log(rate, bodyMessage);
    //TODO://spoiler체크박스
    Setaxios.postAxios("comment", {
      rating: rate * 2,
      boardId: Number(boardid),
      donation: 0,
      commentBody: bodyMessage,
    })
      .then((res) => {
        const response: any = res.data;
        if (response.code === 2000) {
          setCommentModalOpen(false);
          location.reload();
        }
      })
      .catch((err) => {
        if (err.response.data.code === 4004) {
          alert("이미 코멘트를 작성하셨습니다");
        }
      });
  }

  const commentModal = () => {
    return (
      <div key="modal">
        <div
          className={styles["screen-wrapper"]}
          onClick={(e) => {
            const screen = document.getElementById("screen");
            e.target === screen ? setCommentModalOpen(false) : null;
          }}
        >
          <div id="screen" className={styles["commentmodal-wrapper"]}>
            <div className={styles.commentmodal}>
              <div className={styles.commentmodal__form}>
                <textarea
                  maxLength={255}
                  onChange={(e) => setBodymessage(e.target.value)}
                  placeholder="작품에 대한 평가를 자유롭게 공유해주세요"
                  className={styles.input__textarea}
                ></textarea>
                <button onClick={SubmitComment}>작성하기</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  };

  return (
    <>
      <div className={styles.stars}>
        {star("1")}
        {star("2")}
        {star("3")}
        {star("4")}
        {star("5")}
      </div>
      {commentModalOpen ? commentModal() : null}
    </>
  );
}
