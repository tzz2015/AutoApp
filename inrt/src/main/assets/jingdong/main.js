console.show();
auto.waitFor();
app.launchApp('京东');
sleep(3000);
//clickMaskDetail()
//clickRushBuy()


// 循环执行
while (true){
          sleep(500);
          var d = new Date();
          hour = d.getHours()
          minute = d.getMinutes()
          seconds = d.getSeconds()
          log("-----"+hour+"点"+minute+"分"+seconds+"秒")
          if(hour==11&&minute==59&&seconds==58){
               var btnSnapUp = className("android.widget.TextView").text("立即抢购").findOne()
               if(btnSnapUp != null){
                  log("找到立即抢购")
                  btnSnapUp.click()
               }

               var btnConfirm = className("android.widget.Button").text("打白条").findOne()
               if(btnConfirm != null){
                    log("找到打白条按钮")
                    btnConfirm.click()
                    console.hide()
                    break
               }
          }



       }

