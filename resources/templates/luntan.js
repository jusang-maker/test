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
   		url: '/publish'+test,
   		success: function (data) {
   		    alert('发布成功')
   		    location.reload()
   		},
   		error: function(data) {
			alert('发布失败');
		}
	})
	}
	else {
		alert('不能为空');
	}
};
function next_page(){
    <!--截取url中的页码参数，用于跳转到下一页-->
    var page_num =window.location.search.split("=").pop();
    var num=Number(page_num)
    num+=1;
    window.location.href='http://localhost:8001/luntan?pageNum='+num
}
function top_page(){
    var page_num =window.location.search.split("=").pop();
    var num=Number(page_num)
    num-=1;
    window.location.href='http://localhost:8001/luntan?pageNum='+num
}