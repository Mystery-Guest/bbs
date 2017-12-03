<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div id="ft" class="qing_foot cl"> 
   <div class="wp cl"> 
    <div id="frt" class="y"> 
     <p>联系方式 <a href="http://www.hacg.cool/" target="_blank">一本道</a>&nbsp; <b>(株)</b>  &nbsp; &copy; 2016-2017 
     </p> 
    </div> 
   </div> 
  </div> 
  <div id="scrolltop" class="js_scrolltop"> 
   <a title="返回顶部" class="scrolltopa" id="scrolltopa" style="display: none;"> <b>返回顶部</b> </a> 
  </div> 
  <script type="text/javascript">
  function updateuseronlinetime(){
	  $.ajax({
		   type: "POST",
		   url: "${path}/common/updateuseronlinetime.do",
		   async: true,
		   success: function(data){
		   }
	 });
  }
  setInterval("updateuseronlinetime()",30000);
</script>
<%@ include file="/WEB-INF/inc/commonfoot.jsp"%>