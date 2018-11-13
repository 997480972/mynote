<#include "/layout/head.ftl">
<script type="text/javascript" src="/plugins/ckeditor/ckeditor.js"></script> 
<script charset="utf-8" src="/plugins/kindeditor/kindeditor-all.js"></script>
<script charset="utf-8" src="/plugins/kindeditor/lang/zh-CN.js"></script>
<div id="note" style="margin-top:50px; height: 100%;margin-bottom:100px;">
	<form role="form" style="text-align:center;" action="#" method="post">
		分类：<select v-model="note.categoryName"  >
			<option v-for="category in headVue.menus">{{category.name}}</option>
		</select><br><br>
		<input type="text"  class="form-control" v-model:value="note.title"  style="text-align:center;" name="title" placeholder="请输入标题">
		<textarea name="content" id="content" v-model:value="note.content" style=" height:400px;width:100%; margin: 0 auto;"></textarea>  
		<br/>
		<button id="saveNote" type="submit" class="btn btn-success">保存</button>
	</form>
</div>
<script>
	var note = ${note!"''"}; //add default ''
 	new Vue({
    	el : '#note', 
    	data : {
    		note : note
    	},
    	created:function(){
    		headVue.activeName=note.categoryName;
    	}
    });
    KindEditor.ready(function(K) {
            window.editor = K.create('#content', {
                uploadJson : '/fileUpload',
                fileManagerJson : '/fileManager',
                allowFileManager : true,
                afterUpload : function(url) {
                    alert(url);
            	},
            	formatUploadUrl : false,
            	urlType : "",//改变站内本地URL，可设置”“、”relative”、”absolute”、”domain”。空为不修改URL，relative为相对路径，absolute为绝对路径，domain为带域名的绝对路径。默认值: “”
            	afterCreate:function(){
                      var doc = this.edit.doc; 
                      var cmd = this.edit.cmd;
                      $(doc.body).bind('paste',function(ev){
                          var $this = $(this);
                          var dataItem =  ev.originalEvent.clipboardData.items[0];
                          if(dataItem){
                              var file = dataItem.getAsFile();
                             if(file){
                                  var reader = new FileReader();
                                  reader.onload = function(evt)  {
                                  var imageDataBase64 = evt.target.result;
                                  $.post("/imgUpload/base64",{"imageDataBase64":imageDataBase64},function(resp){
                                           var respData = resp;
                                           if(respData.errCode == 0){
                                               var html = '<img src="' + respData.result + '" alt="" />';
                                               cmd.inserthtml(html);
                                           }
                                       });
                                  };
                                 reader.readAsDataURL(file);
                             } 
                          }
                      });
                  }
            });
    });
    /*
 	//实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('content');
    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
	UE.Editor.prototype.getActionUrl = function(action) {
	    if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
	        return 'http://192.168.220.33:8081/uploadimage';
	    } else if (action == 'uploadvideo') {
	        return 'http://a.b.com/video.php';
	    } else {
	        return this._bkGetActionUrl.call(this, action);
	    }
	}
	*/
	//保存
    $('button#saveNote').click(function() {  
    	//同步数据后可以直接取得textarea的value
		window.editor.sync();
    	note.content = $("#content").val();
    	if(note == ""){ //add
    		note = $('form').serialize();
    	}
	    $.ajax({  
            type: "POST",  
            url:"/note",  
            data: note, //$('form').serialize(),// 序列化表单值  
            error: function(request) {  
                alert("Connection error");  
            },  
            success: function(data) {  
                alert(data);
                location.href="/";
            }  
	     });
	     return false;
    });
</script>
<#include "/layout/foot.ftl">

