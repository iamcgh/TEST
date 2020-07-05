<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理员管理页面</title>
<style type="text/css">
table.hovertable {
	font-family: verdana, arial, sans-serif;
	font-size: 11px;
	color: #333333;
	border-width: 1px;
	border-color: #999999;
	border-collapse: collapse;
	width: 70%;
	margin: auto;
}

table.hovertable th {
	background-color: #c3dde0;
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
}

table.hovertable tr {
	background-color: #d4e3e5;
	color: black;
	font-size: 15px;
}

table.hovertable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #a9c6c9;
	text-align: center;
}

table.hovertable tr:hover {
	background-color: blue;
	color: white;
}

table.hovertable tr td button {
	width: 80px;
	height: 30px;
	border-radius: 8px;
}

ul.pagination {
	display: inline-block;
	padding: 0;
	margin: 0;
}

ul.pagination li {
	display: inline;
}

ul.pagination li a {
	color: black;
	float: left;
	padding: 8px 16px;
	text-decoration: none;
}

ul.pagination li a.active {
	background-color: #4CAF50;
	color: white;
}

ul.pagination li a:hover:not(.active){
	background-color: #ddd;
}

.disabled{
	disabled:disabled;
}

.pageRow {
	margin:15px;
	display:flex;
	justify-content:center;
}

.pageRow #page_info_area{
	height:40px;
	line-height:40px;
	margin-right:30px;
	
}

</style>
</head>
<body onload="init()">
	<div>
		<table class="hovertable">
			<caption>
				<h1>用户管理</h1>
			</caption>
			<thead>
				<tr>
					<th>用户编号</th>
					<th>用户账号</th>
					<th>用户邮箱</th>
					<th>用户状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody id="list">
			</tbody>
		</table>
		<!--<div class="pageRow">
			<div id="page_info_area">当前第1页，总共5页，共45条记录</div>
			<div id="page_nav_area">
				<ul class="pagination">
					<li><a href="#" class="disabled">首页</a>
					<li>
					<li><a href="#">&laquo;</a>
					<li>
					<li><a href="#" class="active">1</a>
					<li>
					<li><a href="#">2</a>
					<li>
					<li><a href="#">3</a>
					<li>
					<li><a href="#">4</a>
					<li>
					<li><a href="#">5</a>
					<li>
					<li><a href="#">&raquo;</a>
					<li>
					<li><a href="#">末页</a>
					<li>
				</ul>
			</div>
		</div>-->
		
		<div class="pageRow">
			<div id="page_info_area">
			</div>
			<div id="page_nav_area">
			</div>
		</div>
	</div>
	<script type="text/javascript">
		//全局变量
		var dataNameArr = [ "id", "userName", "email", "status" ];
		var page = 0;
		var limit = 10;
		var totalRecord = 0;
		var currentPage = 1;//当前页数
		
		
		var currentPage_last_init = 1;//构造页码的基准
		
		//var flag = 0;//标记
		
		function init(){
			//alert(currentPage);
			initData(currentPage - 1,limit,1);			
		}

		function initData(page, limit,currentPage_last_init) {
			//1.发送请求		
			var httpRequest = new XMLHttpRequest();//第一步：建立所需的对象
			var url = "http://localhost:8080/test200617/admin/UserListServlet?page="
					+ page + "&limit=" + limit;
			httpRequest.open('GET', url, true);//第二步：打开连接  将请求参数写在url中
			httpRequest.send();//第三步：发送请求  将请求参数写在URL中
			/**
			 * 获取数据后的处理程序
			 */
			httpRequest.onreadystatechange = function() {
				if (httpRequest.readyState == 4 && httpRequest.status == 200) {
					var json = httpRequest.responseText;//获取到json字符串，还需解析
					//console.log(json);
					// 获取操作的DOM元素
					var list = document.getElementById("list");
		    		while(list.hasChildNodes()){
		    			list.removeChild(list.firstChild);
		    		}
					//将json字符串转化为json对象
					//循环遍历
					var jsonObj = eval("(" + json + ")");
					var data = jsonObj.data;
					totalRecord = jsonObj.total;
					// alert(totalRecord);
					for ( var index in data) {
						//console.log(JSON.stringify(data))
						//新增tr结点
						var trElement = document.createElement('tr');
						//console.log(trElement);
						//新增td结点
						for (var j = 0; j < 5; j++) {
							var tdElement = document.createElement('td');

							var attrName = dataNameArr[j];
							if (j == 4) {
								//操作栏
								var btnEle = document.createElement('button');
								// btnEle.onclick = updateStatus(data[index].id);//为按钮绑定点击事件
								btnEle.innerHTML = data[index].status == "0" ? "封禁"
										: "启用";
								//btnEle.onclick = updateStatus1(data[index].id);
								btnEle.addEventListener("click", updateStatus,
										false);
								//将按钮添加到td元素上
								tdElement.appendChild(btnEle);
							} else if (j == 3) {
								//状态栏                			
								if (data[index][attrName] == "0") {
									//可用
									tdElement.innerHTML = "可用";
								} else {
									//禁用
									tdElement.innerHTML = "禁用";
								}
							} else {
								tdElement.innerHTML = typeof (data[index][attrName]) == "undefined" ? ""
										: data[index][attrName];
							}
							trElement.appendChild(tdElement);
						}
						list.appendChild(trElement);
					}
					
					//2.构建页数信息
					buildPageInfo();
					//3.构造分页组件
					buildPageNav(currentPage_last_init);
				}
			};
		}
		
		
		function buildPageInfo(){
			//this.totalRecord = totalRecord;
			//1.获取元素对象
			var pageInfo = document.getElementById("page_info_area");
    		while(pageInfo.hasChildNodes()){
    			pageInfo.removeChild(pageInfo.firstChild);
    		}
			//2.拼接内容
			var pages = Math.ceil(totalRecord/10);
			var content = "当前第" + currentPage + "页,总共" + pages + "页,共" + totalRecord + "条记录";
			pageInfo.innerHTML = content;			
		}
		
		function buildPageNav(currentPage_last_init){
			//this.totalRecord = totalRecord;
			//1.获取DOM元素
			var pageNav = document.getElementById("page_nav_area");
			//清空内容
		 	//当pageNav下还存在子节点时 循环继续	
    		while(pageNav.hasChildNodes()){
    			pageNav.removeChild(pageNav.firstChild);
    		}
			//2.拼接内容
			//2.1构造ul
			var ulElement = document.createElement('ul');
			ulElement.classList.add("pagination");
			//计算应该构建的页码组件数
			var pages = Math.ceil(totalRecord/10);
			var pageNavNum = pages > 5? 9:pages+4;
			for(var i = 0;i < pageNavNum;i++){
				//构造li
				var liElement = document.createElement('li');
				//构造a
				var aElement = document.createElement('a');
				//给a绑定链接
				aElement.href = "#";
				//绑定点击事件
				aElement.addEventListener("click",clickPageNav,false);
				//a内容
				if(i == 0) {
					aElement.innerHTML = "首页";
				} else if(i == 1) {
					aElement.innerHTML = "&laquo;";
				} else if(i == pageNavNum - 2) {
					aElement.innerHTML = "&raquo;";
				} else if(i == pageNavNum - 1) {
					aElement.innerHTML = "末页";
				} else {
					//alert("构造时："+currentPage +"i为" +i);
					//alert(typeof(Number(currentPage)));
					//判断是否已经需要更新页码
					if(currentPage > Math.ceil(totalRecord/limit)) {
						currentPage_last_init++;
					}
					aElement.innerHTML = parseInt(Number(currentPage_last_init)+i-2);//解决显示NAN问题
				}
				
				//添加激活特效
				if(aElement.innerHTML == currentPage) {
					// alert("点击："+currentPage_copy)
					aElement.classList.add("active");
				}
				
				//将a放到li里
				liElement.appendChild(aElement);
				//将li放到ul
				ulElement.appendChild(liElement)
			}
			
			pageNav.appendChild(ulElement);
			
		}
		
		//页码点击事件函数
		function clickPageNav(event) {
			//1.获取被点击的DOM元素
			var ele = event.target;
			//2.获取该元素的文本（页码）
			var num = ele.innerHTML;
			//alert(this.currentPage);
			if(checkNumber(num)) {
				//是数字
				//3.将当前的页码改变
				currentPage = num;
				//4.发送请求
				initData(currentPage - 1,limit,currentPage_last_init);
			} else if(num == "首页"){
				currentPage = 1;
				//跳到第一页
				initData(currentPage - 1,limit,currentPage_last_init);
			} else if(num == "末页"){
				currentPage = Math.ceil(totalRecord/limit);
				//跳到最后一页
				initData(currentPage - 1,limit,currentPage_last_init);
			} else if(num == "«") {
				if(currentPage == 1) {
					//alert(currentPage);
					currentPage = 1;
				} else {
					//alert(currentPage+"haha");
					currentPage = currentPage - 1;
				}
				//alert(currentPage);
				//this.currentPage = this.currentPage - 1 <= 0? 1:this.currentPage - 1;
				initData(currentPage - 1,limit,currentPage_last_init);
			} else if(num == "»") {
				if(currentPage == Math.ceil(totalRecord/limit)) {
					currentPage = Math.ceil(totalRecord/limit);
				} else {
					currentPage = currentPage + 1;
				}
				
				initData(currentPage - 1,limit,currentPage_last_init);
			}

			//alert(this.currentPage);
			//4.将当前页码激活,同时移除原来激活的页码的特效
			//querySelector
			//var lastNav = document.querySelector("a.active");
			//lastNav.classList.remove("active");
			//ele.classList.add("active");

		}

		//更新用户状态函数
		function updateStatus(event) {

			//console.log(event);
			//alert(data[index].id);
			//获取id
			var id = event.target.parentNode.parentNode.firstElementChild.innerHTML;
			//发送请求
			var httpRequest = new XMLHttpRequest();//第一步：创建需要的对象
			var url = "http://localhost:8080/test200617/admin/UpdateStatusServlet";
			httpRequest.open('POST', url, true); //第二步：打开连接
			httpRequest.setRequestHeader("Content-type",
					"application/x-www-form-urlencoded");//设置请求头 注：post方式必须设置请求头（在建立连接后设置请求头）
			httpRequest.send('id=' + id);//发送请求 将情头体写在send中
			/**
			 * 获取数据后的处理程序
			 */
			httpRequest.onreadystatechange = function() {//请求后的回调接口，可将请求成功后要执行的程序写在其中
				if (httpRequest.readyState == 4 && httpRequest.status == 200) {//验证请求是否发送成功
					var json = httpRequest.responseText;//获取到服务端返回的数据
					//console.log(json);
					//清除tbody下的所有子元素
					/*while (list.firstChild) {
						list.removeChild(list.firstChild);

					}
					initData(0, 10);*/
					//局部更新
					var listEle = document.getElementById("list");
					var childEles = listEle.children;
					for (var i = 0; i < childEles.length; i++) {
						if (childEles[i].firstElementChild.innerHTML == id) {
							//找到那一行数据了
							var sonEles = childEles[i].children;
							sonEles[3].innerHTML = sonEles[3].innerHTML == "可用" ? "禁用"
									: "可用";
							sonEles[4].firstElementChild.innerHTML = sonEles[4].firstElementChild.innerHTML == "封禁" ? "启用"
									: "封禁";
						}
					}
				}
			};

		}
		
		//验证字符串是否是数字
		function checkNumber(theObj) {
		    var reg = /^[0-9]+.?[0-9]*$/;
		    if (reg.test(theObj)) {
		        return true;
		    }
		    return false;
		}
	</script>
</body>

</html>


