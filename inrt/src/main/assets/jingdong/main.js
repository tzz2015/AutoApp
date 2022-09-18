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
          hour=d.getHours()
          minute=d.getMinutes()
          log("-----"+hour+"点"+minute+"分"+"-----")
          if(hour==17&&minute==22){
//            clickMaskDetail()
              width = device.width
              height = device.height
              log("宽："+width+"----高："+height)
              click(width/2,height/2)
              sleep(10)
              click(width/2,height/2)
              sleep(10)
              click(width/2,height/2)
              sleep(10)
              clickRushBuy()
              break
          }
       }



// 点击抢购
function clickRushBuy(){
       width = device.width
       height = device.height
       log("宽："+width+"----高："+height)
       while (true){
             try {
                  sleep(100);
                  log("点击立即抢购")
                  click(width-50,height-30)
                  log("点击完成立即抢购")
                  sleep(100)
                  click(width-50,height-30)
                  log("点击确定")
                  click(width/2,height-30)
                  var draw = desc("打白条").findOne(500);
                  if(draw != null){
                     break;
                  }else {
                      log("找不到了")
                  }
               }catch(error){
                 log("出错了，不好意思")
                 log(error)
              }
       }

    }


