console.log(getCookie("respondentId"))
function modUser() {

    var password = document.getElementById("PassWord");
    var rePassword = document.getElementById("rPassWord");
    if (password.val!==rePassword.val){
        alert("两次输入的密码不一致！");
        return;
    }

    var url = '/user/modify';
    var data = {
        "username": document.getElementById("UserName").value,
        "name": document.getElementById("Name").value,
        "password": password.value,
        //"identity": 1,
        "phone": document.getElementById("Phone").value,
        "question": document.getElementById("Question").value,
        "answer": document.getElementById("Answer").value,
        //"identity": iden,
        //"status" : 1,
        //"info":document.getElementById("Question"), //修改
        "userId": getCookie("respondentId")
    };
    commonAjaxPost(true, url, data, function (result) {
        if (result.code == "666") {
            //layer.msg("用户修改成功，权限已开启", {icon: 1});
            setTimeout(function () {
                window.location.href = 'manGroupRespondent.html';
            }, 1000)
        }else if(result.code == "50003"){
            //用户名已存在
            layer.msg(result.message, {icon: 2});
        } else if (result.code == "333") {
            layer.msg(result.message, {icon: 2});
            setTimeout(function () {
                window.location.href = 'manGroupRespondent.html';
            }, 1000)
        } else {
            layer.msg(result.message, {icon: 2});
        }
    })

    // window.location.href = "manGroupRespondent.html" //界面跳转
}

function returnToMain(){
    deleteCookie('respondentId')
    window.location.href = "manGroupRespondent.html" //界面跳转
}