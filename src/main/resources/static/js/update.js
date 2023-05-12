// (1) 회원정보 수정
function update(userId,event) {
    //alert("update!!");
    event.preventDefault();// 폼테그 진행을 막는다.

    let data=$("#profileUpdate").serialize();
    console.log(data);
    console.log(userId);
    $.ajax({
        url:"/api/user/"+userId,
        type:"put",
        data:data,
        contentType:"application/x-www-form-urlencoded;charset=utf-8",
        dataType:"json"
    }).done(res=>{
        console.log("update 성공", res);
        location.href="/user/"+userId;
    }).fail(error=>{ //HttpStatus 상태코드 200번대가 아닐때
        //console.log("update 실패", error);
        console.log(error);
        if(error.data==null){
            alert(error.responseJSTON.message());
        }else{
            alert(JSON.stringify(error.responseJSON.data));
        }
    });
}