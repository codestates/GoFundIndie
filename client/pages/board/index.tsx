import Link from "next/link";
import styles from "../../styles/boardlist.module.scss";

export default function Board() {
  const boardid = 32;
  return (
    <div>
      <div className={styles.header__blank__div} />
      <div className={styles.boardlist__wrapper}>
        <div>영화목록</div>
        <div>
          <Link href="/board/view/[boardid]" as={`/board/view/${boardid}`}>
            id = 32 영화 페이지로 이동
          </Link>
        </div>
        <div>
          <Link href="/board/view/[boardid]" as={`/board/view/33`}>
            id = 33 영화 페이지로 이동
          </Link>
        </div>
      </div>
    </div>
  );
}
