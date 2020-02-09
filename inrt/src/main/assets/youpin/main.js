console.show();
auto.waitFor();
app.launchApp('小米有品');
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
          if(hour==16){
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


//点击口罩详情
function clickMaskDetail() {
     while (true){
        sleep(100);
        try {
           log("开始点击")
           var maskImage = className("android.widget.Image").text("a7b5a6fcad58549d209684e3da6c85c5").findOne();
           if(maskImage){
              maskImage.click()
              log("成功点击")
              break
           }else{
              log("没有找到入口")
           }
          }catch(error){
            log("点击失败")
            log(error)
         }
       }
     sleep(100);
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
               }catch(error){
                 log("出错了，不好意思")
                 log(error)
              }
       }

    }


