console.log("this is the script file");

const togglesideBar = () => {
    $(".sidebar").toggle();
    $(".content").css("margin-left", $(".sidebar").is(":visible") ? "20%" : "0%");
};
