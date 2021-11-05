import styles from "../styles/components/contentcarousel.module.scss";

export default function ContentCarousel() {
  let counter = 0;
  const posters = (
    <div className={styles.slide__wrapper}>
      <div>
        <img
          width="282"
          src="https://an2-img.amz.wtchn.net/image/v2/63456fa9804b8ba4729c5e61e4d540cb.jpg?jwt=ZXlKaGJHY2lPaUpJVXpJMU5pSjkuZXlKaVlXTnJaM0p2ZFc1a0lqcDdJbklpT2pJMU5Td2laeUk2TWpVMUxDSmlJam95TlRWOUxDSmpjbTl3SWpwMGNuVmxMQ0pvWldsbmFIUWlPamN3TUN3aWNHRjBhQ0k2SWk5Mk1pOXpkRzl5WlM5cGJXRm5aUzh4TmpNeU56a3hPREk1TXpBeE16VTRNalE1SWl3aWNYVmhiR2wwZVNJNk9EQXNJbmRwWkhSb0lqbzBPVEI5Lkpxci1MZWF1Nmx3YTZLNVM4V2RUWkRhdDhqanNQWkUzQkloWXpaSnkyREU"
        />
      </div>
      <div>
        <img
          width="282"
          src="https://an2-img.amz.wtchn.net/image/v2/63456fa9804b8ba4729c5e61e4d540cb.jpg?jwt=ZXlKaGJHY2lPaUpJVXpJMU5pSjkuZXlKaVlXTnJaM0p2ZFc1a0lqcDdJbklpT2pJMU5Td2laeUk2TWpVMUxDSmlJam95TlRWOUxDSmpjbTl3SWpwMGNuVmxMQ0pvWldsbmFIUWlPamN3TUN3aWNHRjBhQ0k2SWk5Mk1pOXpkRzl5WlM5cGJXRm5aUzh4TmpNeU56a3hPREk1TXpBeE16VTRNalE1SWl3aWNYVmhiR2wwZVNJNk9EQXNJbmRwWkhSb0lqbzBPVEI5Lkpxci1MZWF1Nmx3YTZLNVM4V2RUWkRhdDhqanNQWkUzQkloWXpaSnkyREU"
        />
      </div>
      <div>
        <img
          width="282"
          src="https://an2-img.amz.wtchn.net/image/v2/63456fa9804b8ba4729c5e61e4d540cb.jpg?jwt=ZXlKaGJHY2lPaUpJVXpJMU5pSjkuZXlKaVlXTnJaM0p2ZFc1a0lqcDdJbklpT2pJMU5Td2laeUk2TWpVMUxDSmlJam95TlRWOUxDSmpjbTl3SWpwMGNuVmxMQ0pvWldsbmFIUWlPamN3TUN3aWNHRjBhQ0k2SWk5Mk1pOXpkRzl5WlM5cGJXRm5aUzh4TmpNeU56a3hPREk1TXpBeE16VTRNalE1SWl3aWNYVmhiR2wwZVNJNk9EQXNJbmRwWkhSb0lqbzBPVEI5Lkpxci1MZWF1Nmx3YTZLNVM4V2RUWkRhdDhqanNQWkUzQkloWXpaSnkyREU"
        />
      </div>
      <div>
        <img
          width="282"
          src="https://an2-img.amz.wtchn.net/image/v2/63456fa9804b8ba4729c5e61e4d540cb.jpg?jwt=ZXlKaGJHY2lPaUpJVXpJMU5pSjkuZXlKaVlXTnJaM0p2ZFc1a0lqcDdJbklpT2pJMU5Td2laeUk2TWpVMUxDSmlJam95TlRWOUxDSmpjbTl3SWpwMGNuVmxMQ0pvWldsbmFIUWlPamN3TUN3aWNHRjBhQ0k2SWk5Mk1pOXpkRzl5WlM5cGJXRm5aUzh4TmpNeU56a3hPREk1TXpBeE16VTRNalE1SWl3aWNYVmhiR2wwZVNJNk9EQXNJbmRwWkhSb0lqbzBPVEI5Lkpxci1MZWF1Nmx3YTZLNVM4V2RUWkRhdDhqanNQWkUzQkloWXpaSnkyREU"
        />
      </div>
    </div>
  );
  return (
    <div className={styles.carousel}>
      <div className={styles.category__wrapper}>
        <div className={styles.category}>내가 담아둔 영화</div>
        <div id="posters" className={styles.poster__wrapper}>
          {posters}
          {posters}
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
            if (counter === 4) return;
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

      <div className={styles.category__wrapper}>
        <div className={styles.category}>내가 담아둔 영화</div>
        <div id="posters" className={styles.poster__wrapper}>
          {posters}
          {posters}
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
            if (counter === 4) return;
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
//transform: translateX(-1200px);
