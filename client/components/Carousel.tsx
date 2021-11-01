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
          <img src="https://spnimage.edaily.co.kr/images/Photo/files/NP/S/2018/11/PS18110700246.jpg" />
        </SwiperSlide>
        <SwiperSlide>
          <img src="https://flexible.img.hani.co.kr/flexible/normal/640/427/imgdb/original/2020/1209/20201209502932.jpg" />
        </SwiperSlide>
        <SwiperSlide>
          <img src="http://image.kmib.co.kr/online_image/2020/1112/611811110015211020_1.jpg" />
        </SwiperSlide>
        <SwiperSlide>
          <img src="http://www.gametoc.co.kr/news/photo/201506/29076_56647_1959.jpg" />
        </SwiperSlide>
        <SwiperSlide>
          <img src="https://blog.kakaocdn.net/dn/qbif5/btqyU8kkI49/sjLws3sE2ynKbpBPrBqjpK/img.jpg" />
        </SwiperSlide>
        <SwiperSlide>
          <img src="http://www.nbnnews.co.kr/news/photo/202108/603164_602854_620.jpg" />
        </SwiperSlide>
        <SwiperSlide>
          <img src="http://ojsfile.ohmynews.com/STD_IMG_FILE/2018/0108/IE002268886_STD.jpg" />
        </SwiperSlide>
        <SwiperSlide>
          <img src="https://t1.daumcdn.net/cfile/tistory/99AF27375B3421762F" />
        </SwiperSlide>
        <SwiperSlide>
          <img src="https://newsimg.sedaily.com/2016/12/02/1L53AQW6K8_1.jpg" />
        </SwiperSlide>
      </Swiper>
    </div>
  );
}
