function screenToData(fldArray, postData) {//将页面中数组对应的id值填充
	for (var i = 0; i < fldArray.length; i++) {
		var obj = $('#' + fldArray[i]);
		var val = GetWindowText(obj,fldArray[i]);
		postData[fldArray[i]] = ChangeNoteToCode(val);
	}
}

function GetWindowText(ctlobj,jVal) {
	var val = "";
	var type = ctlobj.attr("type");
	if (type == "checkbox"||type == "radio") {
		$("input[id^=\""+jVal+"\"]").each(function (){
			if($(this).is(":checked")||$(this).parent().hasClass("on")){
				val = $(this).val();
			}
		});
	}else {
		val = ctlobj.val();
		if (val == undefined)
			val = ctlobj.text();
	}
	return val;
}

function dataToScreen(fldArray, postData) {//将数组中对应的值填充到页面
	if(!postData)return;
//	var reg = new RegExp("\"","g");//g,表示全部替换。
	for (var i = 0; i < fldArray.length; i++) {
		var ctlobj = $('#' + fldArray[i]);
//		postData = postData.replace(reg,"'");
		var pd = JSON.parse(postData);
		var val = ChangeNoteToCode(pd[fldArray[i]]);
		SetWindowText(ctlobj, val,fldArray[i]);
	}
}

function ChangeNoteToCode(note) {
	if (note == undefined)
		return '';
	
	if (typeof s != 'String')
		note = note.toString();
	
//	var idx = note.indexOf('|');
//	if (idx > 0)
//		return note.split('|')[0];
		
	return note;
}

function SetWindowText(ctlobj, val,jVal) {
		var type = ctlobj.attr("type");
		if (type == "checkbox"||type == "radio") {
			$("input[id^=\""+jVal+"\"]").each(function (){
				$(this).parent().removeClass("on");
				$(this).attr("checked", false);
				if($(this).val()==val){
					$(this).parent().addClass("on");
//					$(this).attr("checked", true);
				}
			});
		}else{
			ctlobj.val(val);
			ctlobj.change();//下拉列表选中时，出发一次本身的选中事件
		}
}

function gainData(fldArr,dataMap){
	for (var i = 0; i < fldArr.length; i++) {
		$("."+fldArr[i]+"").each(function(){
			var obj = $(this);
			var type = obj.attr("type");
			var val = "";
			if (type == "checkbox"||type == "radio") {
				if(obj.is(":checked")||obj.parent().hasClass("on")){
					val = obj.val();
					if(trimStr(val).length>0){
						dataMap[fldArr[i]] = (typeof(dataMap[fldArr[i]])=="undefined")?val+"":(dataMap[fldArr[i]]+","+val);
					}
				}
			}else if(type == "text"){
				var dv = obj.attr("class");
				if(dv.indexOf("d"+fldArr[i])>-1){
					var next=obj.parent().nextAll().eq(1).find("input").val();
					if(typeof(next)=="undefined"){
						next=obj.parents("li").find("input:eq(1)").val();
					}
					val = obj.val()+"~"+next;
					if(trimStr(val).length>1){
						dataMap[fldArr[i]] = (typeof(dataMap[fldArr[i]])=="undefined")?val+"":(dataMap[fldArr[i]]+","+val);
					}
				}else{
					val = obj.val();
					if(trimStr(val).length>0){
						dataMap[fldArr[i]] = (typeof(dataMap[fldArr[i]])=="undefined")?val+"":(dataMap[fldArr[i]]+","+val);
					}
				}
			}else{
				val = obj.val();
				if (typeof(val) == "undefined"){
					val = obj.text();
					if(trimStr(val).length>0){
						dataMap[fldArr[i]] = (typeof(dataMap[fldArr[i]])=="undefined")?val+"":(dataMap[fldArr[i]]+","+val);
					}
				}else{
					if(trimStr(val).length>0){
						dataMap[fldArr[i]] = (typeof(dataMap[fldArr[i]])=="undefined")?val+"":(dataMap[fldArr[i]]+","+val);
					}
				}
			}
		});
	}
}
function gainData2(fldArr){
	var dataMap="";
	for (var i = 0; i < fldArr.length; i++) {
		$("."+fldArr[i]+"").each(function(){
			var obj = $(this);
			var type = obj.attr("type");
			var val = "";
			if(type == "text"){
				var dv = obj.attr("class");
				if(dv.indexOf("d"+fldArr[i])>-1){
					var next=obj.parent().nextAll().eq(1).find("input").val();
					if(typeof(next)=="undefined"){
						next=obj.parents("li").find("input:eq(1)").val();
					}
					val = obj.val()+"~"+next;
					dataMap += fldArr[i]+","+val+";";
				}else{
					val = obj.val();
					dataMap += fldArr[i]+","+val+";";
				}
			}else{
				var clazz = obj.prop("class");
				if(clazz.indexOf("textarea")>-1){
					val = obj.val();
					dataMap += fldArr[i]+","+val+";";
				}

			}
		});
	}
	if(dataMap.length>0){
		dataMap = dataMap.substring(0,dataMap.length-1);
	}
	return dataMap;
}

//postArr,rpData,mainfieldExtend
function rgainData(fldArr,dataMap,mainfieldExtend){
	if(fldArr){
		fldArr.push("country");
		fldArr.push("province");
		fldArr.push("city");
		for (var i = 0; i < fldArr.length; i++) {
			$(".r"+fldArr[i]+"").each(function(){
				var obj = $(this);
				var type = obj.attr("type");
				var val = "";
				if (type == "radio") {
					if(obj.is(":checked")||obj.parent().hasClass("on")){
						//if($(this).parent().parent().parent().next().eq(0).is("[id*='result']")){
						var undef=$(this).parent().parent().next().eq(0).find("[id*='result']").html();
						if(typeof(undef)!="undefined"){
							var p = obj.val().split("_")[0];
							var k = fldArr[i];
							if(p==8){
								k = "t#"+k;
							}else if(p==16){
								k = "y#"+k;
							}else if(p==17){
								k = "m#"+k;
							}else if(p==18){
								k = "d#"+k;
							}else if(p==19){
								k = "w#"+k;
							}
							k = typeof(mainfieldExtend[k])=="undefined"?k:mainfieldExtend[k].value;
							dataMap[k] = "";
							$(this).parent().parent().next().eq(0).find("input[type='checkbox']").each(function(){
								if($(this).parent().hasClass("on")){
									val = $(this).val();
									if(trimStr(val).length!=0){
										var parentVal = obj.val().split("_")[0];
										var key = fldArr[i];
										if(parentVal==8){
											key = "t#"+key;
										}else if(parentVal==16){
											key = "y#"+key;
										}else if(parentVal==17){
											key = "m#"+key;
										}else if(parentVal==18){
											key = "d#"+key;
										}else if(parentVal==19){
											key = "w#"+key;
										}
										key = typeof(mainfieldExtend[key])=="undefined"?key:mainfieldExtend[key].value;
										dataMap[key] = (dataMap[key]==="")?val+"":(dataMap[key]+","+val);
									}
								}
							});
						}
					}
				}
			});
		}
	}
}

function rfullGainData(fldArr,dataMap){
	if(fldArr){
		for (var i = 0; i < fldArr.length; i++) {
			var flag = true;
			$(".r"+fldArr[i]+"").each(function(){
	//			if($.isEmptyObject(dataMap)){
				if(flag){
					var obj = $(this);
					var type = obj.attr("type");
					var val = "";
					if (type == "radio") {
					if(obj.is(":checked")||obj.parent().hasClass("on")){
						if($(this).parent().parent().parent().next().eq(0).is("[id*='result']")){
							$(this).parent().parent().parent().next().eq(0).find("input[type='checkbox']").each(function(){
	//						if($(this).parent().hasClass("on")){
								val = $(this).val();
								if(trimStr(val).length!=0){
									var parentVal = obj.val().split("_")[0];
									var key = fldArr[i];
									if(parentVal==8){
										key = "t#"+key;
									}else if(parentVal==16){
										key = "y#"+key;
									}else if(parentVal==17){
										key = "m#"+key;
									}else if(parentVal==18){
										key = "d#"+key;
									}else if(parentVal==19){
										key = "w#"+key;
									}
									dataMap[key] = (typeof(dataMap[key])=="undefined")?val+"":(dataMap[key]+","+val);
								}
	//							}
							});
							flag = false;
						}
					}
					}
				}
	//			}
			});
		}
	}
}

function trimStr(str){//去除前后空格
	if(str==null){
		str = "";
	}else if($.isArray(str)){//是数组直接返回
		return str;
	}
	return str.replace(/(^\s*)|(\s*$)/g,"");
}
//判断是大写字母
function testUpperLetter(value){
	var Regx = /[A-Z]{1}/;
	if (Regx.test(value)){
		return true;
	}else{
		return false;
	} 
}
