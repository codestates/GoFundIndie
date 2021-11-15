import styles from "../styles/components/contentcarousel.module.scss";
import Link from "next/link";
export default function ContentCarousel({ film }: any) {
  if (film.data === null) return <></>;
  if (film.data.length <= 4) return <></>;
  let counter = 0;
  let sliderstack: any[] = [];

  const posterWrapper = (
    movie1: any,
    movie2: any,
    movie3: any,
    movie4: any
  ) => {
    sliderstack = [];
    return (
      <div key={movie1.id} className={styles.slide__wrapper}>
        {poster(movie1)}
        {poster(movie2)}
        {poster(movie3)}
        {poster(movie4)}
      </div>
    );
  };
  function poster(movie: any) {
    return (
      <div>
        <Link href="/board/view/[boardid]" as={`/board/view/${movie.id}`}>
          {movie.posterImg ? (
            <img src={movie.posterImg} draggable="false" loading="lazy" />
          ) : (
            <img src="/Poster.jpg" draggable="false" loading="lazy" />
          )}
        </Link>
        <div className={styles.title}>{movie.title}</div>
      </div>
    );
  }
  return (
    <div className={styles.carousel}>
      <div className={styles.category__wrapper}>
        <div className={styles.category}>{film.phrase}</div>
        <div id="posters" className={styles.poster__wrapper}>
          {film.data.map((movie: any) => {
            sliderstack.push(movie);
            if (sliderstack.length === 4) {
              return posterWrapper(
                sliderstack[0],
                sliderstack[1],
                sliderstack[2],
                sliderstack[3]
              );
            }
          })}
        </div>
        {film.data.length < 8 ? null : (
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
        )}

        {film.data.length < 8 ? null : (
          <button
            className={styles["btn-next"]}
            onClick={(e) => {
              if (counter !== 0 && counter + 1 >= film.data.length / 4) return;
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
        )}
      </div>
    </div>
  );
}
