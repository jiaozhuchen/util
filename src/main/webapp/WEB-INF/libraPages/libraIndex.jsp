<!DOCTYPE HTML>
<html>
	<head>
		<meta charset="UTF-8">
		<title>TestProxyLibra</title>
		<link rel="stylesheet" media="screen" href="./testProxyLibra?act=static&path=/libraStatics/css/index.css">
	</head>
	<body>
	   <div id="methodType">
	       <div class="methodContainer">
		       <ul>
		           <li class="on"><a href="javascript:void(0)" id="callDynamicMethod">Non-Static Method</a></li>
		           <li><a href="javascript:void(0)" id="callStaticMethod">Static Method</a></li>
		       </ul>
	       </div>
	       
	   </div>
		<div id="container">
			<div id="left">
				<div class="item" id="service">
					<label>Bean</label>
					<input class="basic-input" id="serviceValue" placeholder="please input bean id" value="testServiceLibra">
					<button class="btn" id="checkServiceValid">View Method</button>
				</div>
				<div class="item" id="method">
					<label>Method</label>
					<select id="methodValue">
						<option value="">Select Method...</option>
					</select>
					<input id="methodIdValue" class="none" data-method=""/>
				</div>
				<div id="parameter">
					<div class="p1"><textarea id="parameterValue"></textarea></div>
				</div>
				<div id="submit">
					<input class="btn"  type="submit" id="submit">
				</div>
			</div>
			<div id="right">
				<textarea id="invokeResult"></textarea>
			</div>
		</div>
	   <script type="text/javascript" src="./testProxyLibra?act=static&path=/libraStatics/js/jquery-1.11.3.js"></script>
		<script type="text/javascript" src="./testProxyLibra?act=static&path=/libraStatics/js/index.js"></script>
	</body>
</html>