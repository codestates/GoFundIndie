import styles from "../styles/Home.module.scss";
import Carousel from "../components/Carousel";
import "swiper/css/bundle";
import ContentCarousel from "../components/ContentCarousel";
export default function Page() {
  return (
    <div>
      <div className={styles.home__div}>
        <div className={styles.header__blank__div} />
        <Carousel />
        <div className={styles.home__div__wrapper}>
          <ContentCarousel />
        </div>
      </div>
    </div>
  );
}
