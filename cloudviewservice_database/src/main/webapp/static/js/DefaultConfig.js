function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    var Hours = date.getHours();
    var Minutes = date.getMinutes();
    var Seconds = date.getSeconds();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    if (Hours >= 0 && Hours <= 9) {
        Hours = "0" + Hours;
    }
    if (Minutes >= 0 && Minutes <= 9) {
        Minutes = "0" + Minutes;
    }
    if (Seconds >= 0 && Seconds <= 9) {
        Seconds = "0" + Seconds;
    }
    var currentdate = year + seperator1 + month + seperator1 + strDate
        + " " + Hours + seperator2 + Minutes
        + seperator2 + Seconds;
    return Hours + seperator2 + Minutes+ seperator2 + Seconds;;
}
function getData(){

    var temp = (Math.ceil(Math.random()*20))*500;
    console.log(temp);
    return temp;
}