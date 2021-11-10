import styles from "../styles/components/contentcarousel.module.scss";
import Link from "next/link";
export default function ContentCarousel({ film, catchphrase }: any) {
  let counter = 0;
  function poster(film0: any, film1: any, film2: any, film3: any): any {
    if (!film0 || !film1 || !film2 || !film3) return;
    return (
      <div className={styles.slide__wrapper}>
        <div>
          <Link href="/board/view/[boardid]" as={`/board/view/${film0.id}`}>
            {film0.posterImg ? (
              // <img style={{ backgroundImage: `url("${film.posterImg}")` }} />
              <img src={film0.posterImg} />
            ) : (
              <img src="/noposter.png" />
            )}
          </Link>
        </div>
        <div>
          <Link href="/board/view/[boardid]" as={`/board/view/${film1.id}`}>
            {film1.posterImg ? (
              <img src={film1.posterImg} />
            ) : (
              <img src="/noposter.png" />
            )}
          </Link>
        </div>
        <div>
          <Link href="/board/view/[boardid]" as={`/board/view/${film2.id}`}>
            {film2.posterImg ? (
              <img src={film2.posterImg} />
            ) : (
              <img src="/noposter.png" />
            )}
          </Link>
        </div>
        <div>
          <Link href="/board/view/[boardid]" as={`/board/view/${film3.id}`}>
            {film3.posterImg ? (
              <img src={film3.posterImg} />
            ) : (
              <img src="/noposter.png" />
            )}
          </Link>
        </div>
      </div>
    );
  }
  return (
    <div className={styles.carousel}>
      <div className={styles.category__wrapper}>
        <div className={styles.category}>{catchphrase}</div>
        <div id="posters" className={styles.poster__wrapper}>
          {poster(film[0], film[1], film[2], film[3])}
          {poster(film[4], film[5], film[6], film[7])}
        </div>
        <button
          className={styles["btn-prev"]}
          onClick={(e) => {
            if (counter === 0) return;
            let eventtarget: any = e.target;
            const target: any = eventtarget.parentNode.childNodes[1];
            if (target === undefined) return;
            target.style.transition = "transform 0.4s ease-in-out";
            counter--;
            target.style.transform = `translateX(${-1200 * counter}px)`;
          }}
        >
          <img src="/prev.png" />
        </button>
        <button
          className={styles["btn-next"]}
          onClick={(e) => {
            if (counter === 1) return;
            let eventtarget: any = e.target;
            const target: any = eventtarget.parentNode.childNodes[1];
            if (target === undefined) return;
            target.style.transition = "transform 0.4s ease-in-out";
            counter++;
            target.style.transform = `translateX(${-1200 * counter}px)`;
          }}
        >
          <img src="/next.png" />
        </button>
      </div>
    </div>
  );
}
