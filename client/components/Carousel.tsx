import { Swiper, SwiperSlide } from "swiper/react";
import SwiperCore, { Autoplay, Pagination, Navigation } from "swiper";
import styles from "../styles/components/carousel.module.scss";
import Link from "next/link";

SwiperCore.use([Autoplay, Pagination, Navigation]);
export default function Carousel() {
  return (
    <div className={styles["swiper-div"]}>
      <Swiper
        slidesPerView={2}
        spaceBetween={22}
        slidesPerGroup={1}
        centeredSlides={true}
        autoplay={{
          delay: 10000,
          disableOnInteraction: false,
        }}
        loop={true}
        loopFillGroupWithBlank={true}
        pagination={{
          clickable: true,
        }}
        navigation={true}
        className="mySwiper"
      >
        <SwiperSlide>
          <Link href="/board/view/33">
            <img
              className={styles.image}
              loading="lazy"
              draggable="false"
              src="https://user-images.githubusercontent.com/45745049/140465431-f7240c7a-f89c-477e-835a-14b4886cbf4a.png"
            />
          </Link>
        </SwiperSlide>
        <SwiperSlide>
          <Link href="/board/view/44">
            <img
              className={styles.image}
              loading="lazy"
              draggable="false"
              src="https://user-images.githubusercontent.com/45745049/141259717-57ac83de-b74d-4751-add3-82cc3b10bf23.png"
            />
          </Link>
        </SwiperSlide>
        <SwiperSlide>
          <Link href="/board/view/40">
            <img
              className={styles.image}
              loading="lazy"
              draggable="false"
              src="https://user-images.githubusercontent.com/45745049/141732126-d525a873-d9b9-44a1-91ce-9ff325427308.png"
            />
          </Link>
        </SwiperSlide>
      </Swiper>
    </div>
  );
}
