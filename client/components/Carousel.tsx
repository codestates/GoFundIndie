import { Swiper, SwiperSlide } from "swiper/react";
import SwiperCore, { Autoplay, Pagination, Navigation } from "swiper";
import styles from "../styles/components/carousel.module.scss";

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
          <img src="http://image.kmib.co.kr/online_image/2020/1112/611811110015211020_1.jpg" />
        </SwiperSlide>
        <SwiperSlide>
          <img src="http://image.kmib.co.kr/online_image/2020/1112/611811110015211020_1.jpg" />
        </SwiperSlide>
        <SwiperSlide>
          <img src="http://image.kmib.co.kr/online_image/2020/1112/611811110015211020_1.jpg" />
        </SwiperSlide>
        <SwiperSlide>
          <img src="http://image.kmib.co.kr/online_image/2020/1112/611811110015211020_1.jpg" />
        </SwiperSlide>
        <SwiperSlide>
          <img src="http://image.kmib.co.kr/online_image/2020/1112/611811110015211020_1.jpg" />
        </SwiperSlide>
        <SwiperSlide>
          <img src="http://image.kmib.co.kr/online_image/2020/1112/611811110015211020_1.jpg" />
        </SwiperSlide>
        <SwiperSlide>
          <img src="http://image.kmib.co.kr/online_image/2020/1112/611811110015211020_1.jpg" />
        </SwiperSlide>
        <SwiperSlide>
          <img src="http://image.kmib.co.kr/online_image/2020/1112/611811110015211020_1.jpg" />
        </SwiperSlide>
        <SwiperSlide>
          <img src="http://image.kmib.co.kr/online_image/2020/1112/611811110015211020_1.jpg" />
        </SwiperSlide>
      </Swiper>
    </div>
  );
}
