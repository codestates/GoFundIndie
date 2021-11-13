import styles from "../styles/components/notification.module.scss";
export default function Notification() {
  return (
    <div
      className={styles["notification-container"]}
      id="notification-container"
    >
      <p>You have already entered the letter</p>
    </div>
  );
}
