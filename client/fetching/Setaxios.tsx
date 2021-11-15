import axios from "axios";

axios.defaults.withCredentials = true;

class Setaxios {
  constructor() {}
  postAxios = (
    endpoint: string,
    data: object = {},
    contentType: string = "application/json"
  ) => {
    return axios(`${process.env.NEXT_PUBLIC_SERVER_URL}/` + endpoint, {
      method: "POST",
      data: data,
      headers: {
        "Content-Type": contentType,
        "Access-Control-Allow-Headers": "Content-Type",
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "POST",
        "Access-Control-Allow-Credentials": "true",
      },
      withCredentials: true,
    });
  };

  postfileAxios = (endpoint: string, data: object = {}) => {
    return axios(`${process.env.NEXT_PUBLIC_SERVER_URL}/` + endpoint, {
      method: "POST",
      data: data,
      headers: {
        "Content-Type": "multipart/form-data",
        "Access-Control-Allow-Headers": "Content-Type",
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "POST",
        "Access-Control-Allow-Credentials": "true",
      },
      withCredentials: true,
    });
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

  putAxios = (endpoint: string, data: object = {}) => {
    // return axios.put(
    //   `${process.env.NEXT_PUBLIC_SERVER_URL}/` + endpoint,
    //   data,
    //   { withCredentials: true }
    // );
    return axios(`${process.env.NEXT_PUBLIC_SERVER_URL}/` + endpoint, {
      method: "put",
      data: data,
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Headers": "Content-Type",
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "put",
        "Access-Control-Allow-Credentials": "true",
      },
      withCredentials: true,
    });
  };
  deleteAxios = (endpoint: string) => {
    // return axios.put(
    //   `${process.env.NEXT_PUBLIC_SERVER_URL}/` + endpoint,
    //   data,
    //   { withCredentials: true }
    // );
    return axios(`${process.env.NEXT_PUBLIC_SERVER_URL}/` + endpoint, {
      method: "delete",
      headers: {
        "Content-Type": "application/json",
        "Access-Control-Allow-Headers": "Content-Type",
        "Access-Control-Allow-Origin": "*",
        "Access-Control-Allow-Methods": "delete",
        "Access-Control-Allow-Credentials": "true",
      },
      withCredentials: true,
    });
  };

  // return axios.get(`${process.env.NEXT_PUBLIC_SERVER_URL}/` + endpoint, {
  //   withCredentials: true,
  // });
  postGraphql = (data: any = {}, variable: any = {}) => {
    return axios.post(
      `${process.env.NEXT_PUBLIC_SERVER_URL}/graphql`,
      {
        query: data,
        variables: variable,
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
  };

  postFindboardGraphql = (data: any = {}, id: Number) => {
    return axios.post(
      `${process.env.NEXT_PUBLIC_SERVER_URL}/graphql`,
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
  };

  postSearchBoardGraphql = (data: any = {}, search: string) => {
    return axios.post(
      `${process.env.NEXT_PUBLIC_SERVER_URL}/graphql`,
      {
        query: data,
        variables: {
          what: search,
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
  };
}

export default new Setaxios();
