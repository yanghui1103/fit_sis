	//选中表格中的一行记录，并把主键ＩＤ放在一个隐藏域中
	/*tr:当前行的tr
	  table:当前表格
	  id:主键隐藏域的名称
	  index:需要放到隐藏中的字段在表格中的序号,从0开始
	*/
	var trIdTemp = 0;
	function selectOne(tr,table,id,index){
		if(trIdTemp != 0 && trIdTemp != tr.id){
	        
			var listTable = document.getElementById(table);
			for(var i=0;i<listTable.rows.length;i++){
				if (listTable.rows[i].id==trIdTemp){
					var colorName = i % 2 == 0 ? "#DAE4FC" : "#EAEFFD";
					listTable.rows(trIdTemp).style.backgroundColor = colorName; //"#EAEFFD"; //"#ECECEC";
					//var styleName = i % 2 == 0 ? "list-deepblue" : "list-blue";
					//listTable.rows(trIdTemp).className = styleName;
				}
			}
			
			trIdTemp = tr.id;
			tr.style.backgroundColor = "blue"; //"yellow";
			}
		else if(trIdTemp != 0 && trIdTemp == tr.id){
			tr.style.backgroundColor = "blue"; //"yellow";
		}
		else{
			trIdTemp = tr.id;
			tr.style.backgroundColor = "blue"; //"yellow";
		}
		
		var tdId = tr.cells(index).innerText;
		document.getElementsByName(id).value = tdId;
	}
	