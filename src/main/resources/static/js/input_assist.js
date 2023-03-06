/**
 *
 */
"use strict";

$(function () {
  // ［検索］ボタンクリックで検索開始
  $("#input-assist").on("click", function () {
    const userId = $("#input-assist").val();
    const key = $("#key").val();
    console.log(userId);
    console.log(key);

    $.ajax({
      url: "http://localhost:8080/ec-202301b/input-assist",
      type: "GET",
      dataType: "JSON",
      data: {
        userId: userId,
        key: key,
      },

      async: true,
    })
      .done(function (data) {
        //成功
        console.log("ここここ");
        $("#destinationName").val(data.user.name);
        $("#destinationName_label").addClass("active");
        $("#destinationEmail").val(data.user.email);
        $("#destinationEmail_label").addClass("active");
        $("#zipcode").val(data.user.zipcode);
        $("#zipcode_label").addClass("active");
        $("#address").val(data.user.address);
        $("#address_label").addClass("active");
        $("#destinationTel").val(data.user.telephone);
        $("#destinationTel_label").addClass("active");
      })
      .fail(function (XMLHttpRequest, textStatus, errorThrown) {
        // 検索失敗時には、その旨をダイアログ表示

        console.log("XMLHttpRequest : " + XMLHttpRequest.status);
        console.log("textStatus     : " + textStatus);
        console.log("errorThrown    : " + errorThrown.message);
      });
  });
});
