console.show();
console.setPosition(100, device.height/2);
auto.waitFor();
app.launchApp("支付宝");
sleep(3000);
var screen_width = device.width;  //设置屏幕的宽度，像素值
var screen_height =  device.height; //设置屏幕的高度，像素值
//前置操作
setScreenMetrics(screen_width, screen_height);
//收取自己的能量
for (var row = screen_height * 0.256; row < screen_height * 0.376; row += 80)
    for (var col = screen_width * 0.185; col < screen_width * 0.815; col += 80) {
        click(col, row);
        sleep(50);
    }
//循环收取好友能量
while (text("找能量").findOne()) {
    sleep(100)

    log("开始收能量");
    text("找能量").findOne().click();
    sleep(1000);
     if(className("android.widget.Button").text("返回我的森林").exists()){
           log("返回我的森林");
           back();
           break;
    }
    for (var row = screen_height * 0.256; row < screen_height * 0.376; row += 80) {
        for (var col = screen_width * 0.185; col < screen_width * 0.815; col += 80) {
            click(col, row);
            sleep(50);
        }
    }


    back();
    log("一个好友能量收取结束，开始下一个");
}
log("循环结束准备退出");
exit();