var content = document.getElementById('publish');
var test = window.location.search;
submit.onclick = function () {
    if (content.value != '' && content.value.length <= 1000) {
        $.ajax({
            type: 'POST',
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8',
            data: JSON.stringify({
                "content": content.value,    // 帖子内容
            }),
            url: '/publish' + test,
            success: function (data) {
                alert('发布成功')
                location.reload()
            },
            error: function (data) {
                alert('发布失败');
            }
        })
    } else {
        alert('不能为空');
    }
};

function next_page() {
    <!--截取url中的页码参数，用于跳转到下一页-->
    var page_num = window.location.search.split("=").pop();
    var num = Number(page_num)
    num += 1;
    window.location.href = 'http://localhost:8001/luntan?pageNum=' + num
}

function top_page() {
    var page_num = window.location.search.split("=").pop();
    var num = Number(page_num)
    num -= 1;
    window.location.href = 'http://localhost:8001/luntan?pageNum=' + num
}

//加载页面之前删除所有评论框
$(document).ready(function(){
    $(".reply_textarea").remove();
})
//评论框的显示和隐藏
function showAndhide(ID) {

    $(".reply").click(function(){
        $(".reply_textarea").remove();
        $(this).parent().append("<div class='reply_textarea'><textarea id='reply_content' cols='40' rows='5'></textarea><br/>" +
            "<input type='submit' value='发表' onclick='reply_submit(" +
            +ID+");'/></div>");
        console.log(ID);
    })
}

function reply_submit(ID) {
    var reply_content = document.getElementById("reply_content");
    $.ajax({
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        data: JSON.stringify({
            "ParentID": ID,
            "Text": reply_content.value,

        }),
        url: '/saveRemark',
        success: function (data) {
            if(data.msg == "Don't Login")
                alert("请先登录再评论");
                // location.href='http://localhost:8001/login'
            else {
                alert('评论成功');
                location.reload();
            }
        },
        error: function (data) {
            alert('评论存储失败');
        },
    })
    console.log()
}

