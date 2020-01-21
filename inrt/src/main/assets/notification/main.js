// 监听通知栏红包
function listenerNotification(){
     auto();
     log("启动红包监听")
     events.observeNotification();
     events.onNotification(function(notification){
         try{
          var packageName=notification.getPackageName()+""
          var tickerText=notification.tickerText+""
          log(packageName)
          log(tickerText)
          if(packageName=="com.tencent.mm"&&tickerText.indexOf("[微信红包]")!=-1){
               notification.click()
           }
          }catch(error){
            log(error)
          }
     });

}
listenerNotification()