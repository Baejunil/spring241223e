import React, { useState } from "react";
import axios from "axios";  // axios를 ES6 방식으로 import
import "./App.css";

// 네이버 파파고 API 설정 (본인의 클라이언트 ID와 시크릿을 넣어야 합니다)
const clientId = "i02e1xcqqe";  // 네이버에서 발급받은 클라이언트 ID
const clientSecret = "cRatF7nHMsGtKlIsSfrvJTaLDHRicl69FgL2QOnr";  // 네이버에서 발급받은 클라이언트 시크릿

// 파파고 API를 이용한 텍스트 번역 함수
const translate = async (sourceText, sourceLanguage = "ko", targetLanguage = "en") => {
  const url = "https://naveropenapi.apigw.ntruss.com/nmt/v1/translation";
  
  const data = new URLSearchParams();
  data.append("source", sourceLanguage);
  data.append("target", targetLanguage);
  data.append("text", sourceText);

  try {
    const response = await axios.post(url, data, {
      headers: {
        "X-NCP-APIGW-API-KEY-ID": clientId,
        "X-NCP-APIGW-API-KEY": clientSecret,
        "Content-Type": "application/x-www-form-urlencoded",
      },
    });

    // 번역된 텍스트 반환
    return response.data.message.result.translatedText;
  } catch (error) {
    console.error("번역 오류:", error);
    return "번역 중 오류가 발생했습니다.";  // 오류 메시지
  }
};

function App1() {
  const [inputText, setInputText] = useState("");  // 사용자 입력 텍스트 상태
  const [translatedText, setTranslatedText] = useState("");  // 번역된 텍스트 상태

  // 입력값이 변경될 때마다 번역 함수 호출
  const handleInputChange = async (event) => {
    const text = event.target.value;
    setInputText(text);  // 입력값 상태 업데이트

    if (text.trim() !== "") {
      const translation = await translate(text, "ko", "en");  // 한->영 번역
      setTranslatedText(translation);  // 번역된 텍스트 상태 업데이트
    } else {
      setTranslatedText("");  // 입력값이 없으면 번역된 텍스트 비우기
    }
  };

  return (
    <div className="App">
      <h1>번역기</h1>
      <textarea
        value={inputText}
        onChange={handleInputChange}
        placeholder="번역할 텍스트를 입력하세요..."
        rows="5"
        cols="50"
      />
      <div className="result">
        <h2>번역 결과</h2>
        <p>{translatedText}</p>
      </div>
    </div>
  );
}

export default App1;
