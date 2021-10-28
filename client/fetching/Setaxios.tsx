import axios from "axios";
axios.defaults.withCredentials = true;
class Setaxios {
  constructor() {}
  async postAxios(endpoint: string, data: object) {
    await axios(`${process.env.NEXT_PUBLIC_SERVER_URL}/` + endpoint, {
      method: "POST",
      data: {},
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Headers": "Content-Type",
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "POST",
        "Access-Control-Allow-Credentials": "true",
      },
      withCredentials: true,
    });
  }
}

export default new Setaxios();