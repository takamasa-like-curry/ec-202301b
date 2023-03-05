"user strict";
$(function () {
  calc_price();
  $(".size").on("change", function () {
    console.log("size");
    calc_price();
  });
  $(".topping").on("change", function () {
    console.log("topping");
    calc_price();
  });
  $("#currynum").on("change", function () {
    console.log("currynum");
    calc_price();
  });
    $(".select_btn").on("click", function () {
    console.log("topping");
     $(".checkbox").prop("checked", true);
    calc_price();
  });
    $(".delete_btn").on("click", function () {
		$(".checkbox").prop("checked", false);
    console.log("topping");
    calc_price();
  });
 
 
 
  function calc_price() {
    let size = $(".size:checked").val();
    console.log(size);
    let topping_count = $(
      '.topping input[name="toppingIdList"]:checked'
    ).length;
    console.log(topping_count);
    let curry_num = $("#currynum").val();
    console.log(curry_num);
    let size_price = 0;
    let topping_price = 0;
    if (size == "M") {
      size_price = $("#priceM").text();
      console.log(size_price);
      topping_price = 200 * topping_count;
    } else {
      size_price = $("#priceL").text();
      console.log(size_price);
      topping_price = 300 * topping_count;
    }

    let price = (Number(size_price) + topping_price) * curry_num;
    $("#tortalPrice").text(price.toLocaleString());
  }

});
