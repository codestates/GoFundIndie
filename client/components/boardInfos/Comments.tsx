import styles from "../../styles/components/boardInfos/comments.module.scss";

export default function Comments({ comments }: { comments: Array<string> }) {
  if (comments === null || comments.length === 0) {
    return (
      <div className={styles.head}>
        코멘트
        <div className={styles.nocomments}>
          <div className={styles.message}>등록된 코멘트가 없습니다</div>
        </div>
      </div>
    );
  }
  return (
    <div className={styles["comments-wrapper"]}>
      <div className={styles.head}>코멘트</div>
      {comments.map((comment: any) => {
        return (
          <div key={comment.userNickname}>
            <div className={styles["comment-block"]}>
              <div className={styles["comment"]}>
                <div className={styles["comment-header"]}>
                  <div className={styles["fl-st"]}>
                    <div className={styles["image-mask"]}>
                      <img src="https://static.remove.bg/remove-bg-web/194d453110e760e94498dbb94c5cfb329903342c/assets/start-1abfb4fe2980eabfbbaaa4365a0692539f7cd2725f324f904565a9a744f8e214.jpg" />
                    </div>
                    <div className={styles.username}>
                      {comment.userNickname}
                    </div>
                  </div>
                  <div className={styles["fl-ed"]}>
                    <div className={styles["rating-container"]}>
                      <img src="/star.png" />
                      <div className={styles.rating}>{comment.rating}</div>
                    </div>
                  </div>
                </div>
              </div>
              <div className={styles["comment-line"]} />
              <div className={styles["comment"]}>
                <div className={styles["comment-body"]}>
                  <div>{comment.body}</div>
                </div>
              </div>
            </div>
          </div>
        );
      })}
    </div>
  );
}
