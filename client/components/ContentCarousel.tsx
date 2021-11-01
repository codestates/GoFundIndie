import Slider from "react-slick";
import styles from "../styles/components/carousel.module.scss";

export default function ContentCarousel() {
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    centerPadding: "0px",
  };
  return (
    <div className={styles.poster__wrapper}>
      <h2> Multiple items </h2>
      <Slider {...settings}>
        <div className={styles.poster__div}>
          {/* <img
            className="poster"
            width="200px"
            height="300px"
            src="https://mblogthumb-phinf.pstatic.net/MjAxODA0MDZfOSAg/MDAxNTIyOTgyMTg3NjAz.FtM_g10ePoSsqPsg6zRixh8Uh6PIew9ZqvH88r2Y3yMg.7dVwGlQ3WKmSoB9QVNTSIzCu72-axslnPMch19Ajhkwg.JPEG.calleddesign/5a602d4b007a6.jpg?type=w800"
          /> */}
          1
        </div>
        <div className={styles.poster__div}>2</div>
        <div>
          <h3>3</h3>
        </div>
        <div>
          <h3>4</h3>
        </div>
        <div>
          <h3>5</h3>
        </div>
        <div>
          <h3>6</h3>
        </div>
      </Slider>
    </div>
  );
}
