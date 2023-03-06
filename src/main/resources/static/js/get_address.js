"use strict";
$(function () {
  // ［検索］ボタンクリックで検索開始
  $("#get_address_btn").on("click", function () {
    const zipcode = $("#zipcode").val();
    if (zipcode.toString().length < 7) {
      alert("該当住所がありません。");
      return;
    }

    $.ajax({
      url: "https://zipcoda.net/api",
      type: "GET",
      dataType: "json",
      data: {
        zipcode: $("#zipcode").val(),
      },

      async: true,
    })
      .done(function (data) {
        // レスポンスデータがdataに入る
        // 検索成功時にはページに結果を反映
        // コンソールに取得データを表示
        console.log(data);
        console.dir(JSON.stringify(data));
        if (zipcode.toString().length == 7) {
          let strZipcode = zipcode.toString();
          strZipcode = strZipcode.slice(0, 3) + "-" + zipcode.slice(3);
          $("#zipcode").val(strZipcode);
        }

        const address = data.items[0].state_name + data.items[0].address;
        $("#address").val(address);
        $("#address_label").addClass("active");
      })
      .fail(function (XMLHttpRequest, textStatus, errorThrown) {
        // 検索失敗時には、その旨をダイアログ表示
        alert("該当住所がありません。");
        console.log("XMLHttpRequest : " + XMLHttpRequest.status);
        console.log("textStatus     : " + textStatus);
        console.log("errorThrown    : " + errorThrown.message);
      });
  });
});
