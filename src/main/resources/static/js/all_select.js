"user strict"
$(function(){
	$(".btn").on("click",function(){
		console.log("OK");
		$(".checkbox").prop('checked',true);
	});
});
