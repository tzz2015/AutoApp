console.show();
auto.waitFor();
app.launchApp('淘宝');
sleep(3000);
//clickMaskDetail()
//clickRushBuy()


// 循环执行
while (true){
          sleep(99);
          var d = new Date();
          hour = d.getHours()
          minute = d.getMinutes()
          seconds = d.getSeconds()
          log("-----"+hour+"点"+minute+"分"+seconds+"秒")
          if(hour==20){
               var btnSnapUp = className("android.widget.LinearLayout").desc("立即购买").findOne()
               if(btnSnapUp != null){
                   log("找到立即购买")
                   btnSnapUp.click()
                   sleep(10)
                   btnSnapUp.click()
                   sleep(12)
                   btnSnapUp.click()
               }

               var btnConfirm = className("android.widget.TextView").text("提交订单").findOne()
               if(btnConfirm != null){
                   log("找到提交订单")
                   btnConfirm.click()
                   console.hide()
                   break
               }
          }



       }

