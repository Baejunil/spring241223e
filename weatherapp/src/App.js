import { useEffect, useState } from 'react';
import './App.css'
;import axios from 'axios'; 

// 네이버 파파고 API 설정
const clientId = "i02e1xcqqe";  // 네이버에서 발급받은 클라이언트 ID
const clientSecret = "cRatF7nHMsGtKlIsSfrvJTaLDHRicl69FgL2QOnr";  // 네이버에서 발급받은 클라이언트 시크릿

// 파파고 API를 이용한 텍스트 번역 함수
const translate = async (text, sourceLang = 'ko', targetLang = 'en') => {
  const url = 'https://openapi.naver.com/v1/papago/n2mt';
  const headers = {
    'X-Naver-Client-Id': clientId,
    'X-Naver-Client-Secret': clientSecret,
    'Content-Type': 'application/x-www-form-urlencoded',
  };
  const params = new URLSearchParams();
  params.append('source', sourceLang);
  params.append('target', targetLang);
  params.append('text', text);

  try {
    const response = await axios.post(url, params, { headers });
    return response.data.message.result.translatedText; // 번역된 텍스트 반환
  } catch (error) {
    console.error('Error:', error);
    return 'Error occurred while translating.';
  }
};

// App 컴포넌트
function App() {
  const [inputText, setInputText] = useState(''); // 사용자 입력 텍스트
  const [translatedText, setTranslatedText] = useState(''); // 번역된 텍스트 상태
  const [temp, setTemp] = useState(''); // 온도 상태
  const [desc, setDesc] = useState(''); // 날씨 설명 상태
  const [icon, setIcon] = useState(''); // 날씨 아이콘 상태
  const [isReady, setReady] = useState(false); // 데이터 로딩 여부 상태

  // 입력값이 변경될 때마다 번역 함수 호출
  const handleInputChange = async (event) => {
    const text = event.target.value;
    setInputText(text); // 사용자 입력값 상태 업데이트

    if (text.trim() !== '') {
      const translation = await translate(text, 'ko', 'en'); // 한->영 번역
      setTranslatedText(translation); // 번역된 텍스트 상태 업데이트
    } else {
      setTranslatedText(''); // 입력값이 없으면 번역된 텍스트 비우기
    }
  };

  // 날씨 정보 가져오기
  useEffect(() => {
    fetch('https://api.openweathermap.org/data/2.5/weather?q=London&appid=cdd89109a5e5f94a16e4dd141051d7f1')
      .then((result) => result.json())
      .then((jsonResult) => {
        const kelvinTemp = jsonResult.main.temp;
        const celsiusTemp = kelvinTemp - 273.15; // 켈빈을 섭씨로 변환
        setTemp(celsiusTemp.toFixed(2)); // 소수점 2자리로 설정
        setDesc(jsonResult.weather[0].main);
        setIcon(jsonResult.weather[0].icon);
        setReady(true);
      })
      .catch((err) => console.log(err));
  }, []); // 컴포넌트가 처음 렌더링 될 때만 호출

  // 데이터가 준비되면 날씨 정보를 표시
  if (isReady) {
    return (
      <div className="App">
        <h1>Weather Information</h1>
        <p>Temperature: {temp}°C</p> {/* 섭씨 온도 표시 */}
        <p>Description: {desc}</p>
        <img src={`https://openweathermap.org/img/wn/${icon}@2x.png`} alt="Weather icon" />
        <hr />
        <h2>Translation</h2>
        <textarea
          value={inputText}
          onChange={handleInputChange}
          placeholder="번역할 텍스트를 입력하세요..."
          rows="5"
          cols="50"
        />
        <div className="result">
          <h3>Translated Text</h3>
          <p>{translatedText}</p>
        </div>
      </div>
    );
  } else {
    return <div>Loading...</div>; // 데이터 로딩 중
  }
}

export default App;
