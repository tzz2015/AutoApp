console.show();
console.setPosition(0, 0);
auto.waitFor();
app.launchApp("爱南宁");
sleep(1000);
while (text("立即领取").untilFind()) {
   text("立即领取").untilFind().click();
   log("点击立即领取")
   sleep(100)
   text("确认").findOne().click()
   log("点击确认")
}
log("抢到了退出");
exit();