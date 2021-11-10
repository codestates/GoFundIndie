import axios from "axios";

axios.defaults.withCredentials = true;

class Setaxios {
  constructor() {}
  postAxios = (endpoint: string, data: object = {}) => {
    return axios(`${process.env.NEXT_PUBLIC_SERVER_URL}/` + endpoint, {
      method: "POST",
      data: data,
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Headers": "Content-Type",
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "POST",
        "Access-Control-Allow-Credentials": "true",
      },
      withCredentials: true,
    });

    // if (Object.keys(data).length === 0) {
    //   return axios.post(
    //     `${process.env.NEXT_PUBLIC_SERVER_URL}/` + endpoint,
    //     null,
    //     { withCredentials: true }
    //   );
    // }
    // return axios.post(
    //   `${process.env.NEXT_PUBLIC_SERVER_URL}/` + endpoint,
    //   data,
    //   { withCredentials: true }
    // );

    // return axios.request({
    //   method: "post",
    //   url: `${process.env.NEXT_PUBLIC_SERVER_URL}/` + endpoint,
    //   maxRedirects: 0,
    //   data: data,
    //   validateStatus: function (status) {
    //     return status >= 200 && status < 303;
    //   },
    // });
  };

  getAxios(endpoint: string) {
    return axios(`${process.env.NEXT_PUBLIC_SERVER_URL}/` + endpoint, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Headers": "Content-Type",
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "GET",
        "Access-Control-Allow-Credentials": "true",
      },
      withCredentials: true,
    });
  }
  // return axios.get(`${process.env.NEXT_PUBLIC_SERVER_URL}/` + endpoint, {
  //   withCredentials: true,
  // });
  postgraphql = (endpoint: string, data: any = {}, id: Number) => {
    return axios.post(
      `${process.env.NEXT_PUBLIC_SERVER_URL}/` + endpoint,
      {
        query: data,
        variables: {
          boardId: id,
        },
      },
      {
        headers: {
          "Content-Type": "application/json",
          "Access-Control-Allow-Headers": "Content-Type",
          "Access-Control-Allow-Origin": "*",
          "Access-Control-Allow-Methods": "POST",
          "Access-Control-Allow-Credentials": "true",
        },
        withCredentials: true,
      }
    );

    return axios(`${process.env.NEXT_PUBLIC_SERVER_URL}/` + endpoint, {
      method: "POST",
      data: `{
         ${data}}`,
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Headers": "Content-Type",
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "POST",
        "Access-Control-Allow-Credentials": "true",
      },
      withCredentials: true,
    });
  };
}

export default new Setaxios();
