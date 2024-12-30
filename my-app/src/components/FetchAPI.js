// FetchApI.js

function FetchApI(){
    // FetchAPI
    // fetch API를 이용하면 웹 요청을 쉽게 만들 수 있다
    // fetch API는 기존 XMLHttpRequet(AJX)왕 비슷한 개념이지만 프로미스를 지원하므로
    // fetch API는 호출하는 리소스의 경로를 지정하는 필수 인수 하나를 가진 fetch()메서드를 제공한다 웹요청의 경우 이인수는 서비스 의 URL이된다

    fetch("https://jsonplaceholder.typicode.com/todos/1")
    .then(response=>response.json())
    .then(data=>console.log(data))
    .catch(error=>console.log(error))

    //다른 HTTp방식을 이용하려면 fetch()메서드의 두 번째 인수에 정의해야한다.
    // 두 번째 인수는 여러 요청 설정을 정의할 수 있는 객체다
    fetch("https://jsonplaceholder.typicode.com/todos/1",{
        method:"POST",
        headers:{"Content-Type":"application/json"}
    })
    .then(response=>response.json())
    .then(data=>console.log(data))
    .catch(error=>console.log(error))
    // 요청본몬에 JSON으로 인코딩된 데이터를 넣는 구문을 넣을수 있다
    //JSON.stringify - 객체를 JSON으로 바꿔줍니다
    fetch("https://jsonplaceholder.typicode.com/todos/1",{
        method:"POST",
        headers:{"Content-Type":"application/json"},
        body:JSON.stringify(data)
    })
    .then(response=>response.json())
    .then(data=>console.log(data))
    .catch(error=>console.log(error))

    
    return <></>
}
export default FetchApI;